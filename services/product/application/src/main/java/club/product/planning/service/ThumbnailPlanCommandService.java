package club.product.planning.service;

import club.product.planning.port.ThumbnailPlanCommandRepositoryPort;
import club.product.planning.thumbnail.domain.ThumbnailPlan;
import club.product.planning.type.PlanStatus;
import club.product.planning.usecase.ThumbnailPlanCreateUseCase;
import club.product.planning.usecase.ThumbnailPlanDeleteUseCase;
import club.product.planning.usecase.ThumbnailPlanUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static club.product.sourcing.exception.ProductErrorCode.PLAN_NAME_REQUIRED;
import static club.product.sourcing.exception.ProductErrorCode.THUMBNAIL_PLAN_FORBIDDEN;
import static club.product.sourcing.exception.ProductErrorCode.THUMBNAIL_PLAN_NOT_FOUND;
import static club.product.sourcing.exception.ProductErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ThumbnailPlanCommandService implements ThumbnailPlanCreateUseCase, ThumbnailPlanUpdateUseCase, ThumbnailPlanDeleteUseCase {
    
    private final ThumbnailPlanCommandRepositoryPort repository;
    
    @Override
    public ThumbnailPlan create(String userId, ThumbnailPlan plan) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (plan == null) throw THUMBNAIL_PLAN_NOT_FOUND.exception();
        if (plan.getName() == null || plan.getName().isBlank()) throw PLAN_NAME_REQUIRED.exception();
        
        if (plan.getStatus() == null) plan.setStatus(PlanStatus.DRAFT);
        
        plan.setUserId(userId);
        
        return repository.transaction(() -> repository.save(plan));
    }
    
    @Override
    public ThumbnailPlan update(String userId, String id, ThumbnailPlan plan) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (id == null || id.isBlank()) throw THUMBNAIL_PLAN_NOT_FOUND.exception();
        if (!repository.existsByIdAndUserId(id, userId)) throw THUMBNAIL_PLAN_FORBIDDEN.exception();
        
        if (plan == null) throw THUMBNAIL_PLAN_NOT_FOUND.exception();
        if (plan.getName() == null || plan.getName().isBlank()) throw PLAN_NAME_REQUIRED.exception();
     
        plan.setUserId(userId);
        
        return repository.transaction(() -> repository.update(id, plan));
    }
    
    @Override
    public void delete(String userId, String id) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        if (id == null || id.isBlank()) throw THUMBNAIL_PLAN_NOT_FOUND.exception();
        if (!repository.existsByIdAndUserId(id, userId)) throw THUMBNAIL_PLAN_FORBIDDEN.exception();
        
        repository.transaction(() -> {
            repository.deleteFilesByPlanId(id);
            repository.delete(id);
       
            return null;
        });
    }

}
