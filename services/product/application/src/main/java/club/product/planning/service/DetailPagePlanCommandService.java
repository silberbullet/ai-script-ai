package club.product.planning.service;

import club.product.planning.detailpage.domain.DetailPagePlan;
import club.product.planning.port.DetailPagePlanCommandRepositoryPort;
import club.product.planning.type.PlanStatus;
import club.product.planning.usecase.DetailPagePlanCreateUseCase;
import club.product.planning.usecase.DetailPagePlanDeleteUseCase;
import club.product.planning.usecase.DetailPagePlanUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static club.product.sourcing.exception.ProductErrorCode.DETAIL_PAGE_PLAN_NOT_FOUND;
import static club.product.sourcing.exception.ProductErrorCode.DETAIL_PAGE_PLAN_FORBIDDEN;
import static club.product.sourcing.exception.ProductErrorCode.PLAN_NAME_REQUIRED;
import static club.product.sourcing.exception.ProductErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DetailPagePlanCommandService implements DetailPagePlanCreateUseCase, DetailPagePlanUpdateUseCase, DetailPagePlanDeleteUseCase {
    
    private final DetailPagePlanCommandRepositoryPort repositoryPort;

    @Override
    public DetailPagePlan create(String userId, DetailPagePlan plan) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (plan == null) throw DETAIL_PAGE_PLAN_NOT_FOUND.exception();
        if (plan.getName() == null || plan.getName().isBlank()) throw PLAN_NAME_REQUIRED.exception();
        
        if (plan.getStatus() == null) plan.setStatus(PlanStatus.DRAFT);
        
        plan.setUserId(userId);
        return repositoryPort.transaction(() -> repositoryPort.save(plan));
    }
    
    @Override
    public DetailPagePlan update(String userId, String id, DetailPagePlan plan) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (id == null || id.isBlank()) throw DETAIL_PAGE_PLAN_NOT_FOUND.exception();
        if (!repositoryPort.existsByIdAndUserId(id, userId)) throw DETAIL_PAGE_PLAN_FORBIDDEN.exception();
        
        if (plan == null) throw DETAIL_PAGE_PLAN_NOT_FOUND.exception();
        if (plan.getName() == null || plan.getName().isBlank()) throw PLAN_NAME_REQUIRED.exception();
        
        plan.setUserId(userId);
        return repositoryPort.transaction(() -> repositoryPort.update(id, plan));
    }
    
    @Override
    public void delete(String userId, String id) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (id == null || id.isBlank()) throw DETAIL_PAGE_PLAN_NOT_FOUND.exception();
        if (!repositoryPort.existsByIdAndUserId(id, userId)) throw DETAIL_PAGE_PLAN_FORBIDDEN.exception();
        
        repositoryPort.transaction(() -> {
            repositoryPort.deleteFilesByPlanId(id);
            repositoryPort.delete(id);
            return null;
        });
    }
}
