package club.product.planning.usecase;

import club.product.planning.thumbnail.domain.ThumbnailPlan;

public interface ThumbnailPlanCreateUseCase {
    
    ThumbnailPlan create(String userId, ThumbnailPlan plan);
}
