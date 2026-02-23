package club.product.planning.rdb.adapter;

import club.product.planning.detailpage.domain.DetailPagePlan;
import club.product.planning.port.DetailPagePlanCommandRepositoryPort;
import club.product.planning.rdb.mapper.PlanningEntityMapper;
import club.product.planning.rdb.repository.DetailPageFileJpaRepository;
import club.product.planning.rdb.repository.DetailPagePlanJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

import static club.product.sourcing.exception.ProductErrorCode.DETAIL_PAGE_PLAN_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class DetailPagePlanCommandAdapter implements DetailPagePlanCommandRepositoryPort {
    
    private final PlanningEntityMapper mapper;
    private final DetailPagePlanJpaRepository planRepo;
    private final DetailPageFileJpaRepository fileRepo;
    
    @Override
    public <T> T transaction(Supplier<T> supplier) {
        return supplier.get();
    }
    
    @Override
    public DetailPagePlan save(DetailPagePlan plan) {
        var entity = mapper.toEntity(plan);
        // started/finished는 CRUD 단계에서는 입력이 문자열이므로 저장하지 않음(배치 단계에서 Instant로 갱신 권장)
        var saved = planRepo.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public DetailPagePlan update(String id, DetailPagePlan plan) {
        var exists = planRepo.findById(Long.valueOf(id)).orElseThrow(DETAIL_PAGE_PLAN_NOT_FOUND::exception);
        
        exists.name = plan.getName();
        exists.productSourcingId = (plan.getProductSourcingId() == null || plan.getProductSourcingId().isBlank()) ? null : Long.valueOf(plan.getProductSourcingId());
        exists.status = plan.getStatus().name();
        exists.progress = plan.getProgress();
        exists.currentStep = plan.getCurrentStep();
        exists.payloadJson = plan.getPayloadJson();
        exists.resultSummaryJson = plan.getResultSummaryJson();
        exists.errorCode = plan.getErrorCode();
        exists.errorMessage = plan.getErrorMessage();
        
        // startedAt/finishedAt 문자열은 저장 생략(배치에서 Instant 세팅 권장)
        var saved = planRepo.save(exists);
        
        return mapper.toDomain(saved);
    }
    
    @Override
    public void delete(String id) {
        planRepo.deleteById(Long.valueOf(id));
    }
    
    @Override
    public boolean existsByIdAndUserId(String id, String userId) {
        return planRepo.existsByIdAndUserId(Long.valueOf(id), Long.valueOf(userId));
    }
    
    @Override
    public void deleteFilesByPlanId(String planId) {
        fileRepo.deleteByProductPlanId(Long.valueOf(planId));
    }
}
