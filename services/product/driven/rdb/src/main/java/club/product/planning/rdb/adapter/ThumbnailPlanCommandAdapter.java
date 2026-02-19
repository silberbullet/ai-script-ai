package club.product.planning.rdb.adapter;

import club.product.planning.port.ThumbnailPlanCommandRepositoryPort;
import club.product.planning.rdb.mapper.PlanningEntityMapper;
import club.product.planning.rdb.repository.ThumbnailFileJpaRepository;
import club.product.planning.rdb.repository.ThumbnailPlanJpaRepository;
import club.product.planning.thumbnail.domain.ThumbnailPlan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

import static club.product.sourcing.exception.ProductErrorCode.THUMBNAIL_PLAN_NOT_FOUND;


@Repository
@RequiredArgsConstructor
public class ThumbnailPlanCommandAdapter implements ThumbnailPlanCommandRepositoryPort {
    
    private final PlanningEntityMapper mapper;
    private final ThumbnailPlanJpaRepository planRepo;
    private final ThumbnailFileJpaRepository fileRepo;
    
    @Override
    public <T> T transaction(Supplier<T> supplier) {
        return supplier.get();
    }
    
    @Override
    public ThumbnailPlan save(ThumbnailPlan plan) {
        var saved = planRepo.save(mapper.toEntity(plan));
        
        return mapper.toDomain(saved);
    }
    
    @Override
    public ThumbnailPlan update(String id, ThumbnailPlan plan) {
        var exists = planRepo.findById(Long.valueOf(id)).orElseThrow(THUMBNAIL_PLAN_NOT_FOUND::exception);
        
        exists.name = plan.getName();
        exists.productSourcingId = (plan.getProductSourcingId() == null || plan.getProductSourcingId().isBlank()) ? null : Long.valueOf(plan.getProductSourcingId());
        exists.status = plan.getStatus().name();
        exists.progress = plan.getProgress();
        exists.currentStep = plan.getCurrentStep();
        exists.payloadJson = plan.getPayloadJson();
        exists.errorCode = plan.getErrorCode();
        exists.errorMessage = plan.getErrorMessage();
        
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
