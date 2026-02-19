package club.product.planning.usecase;

import club.product.planning.thumbnail.domain.ThumbnailPlan;

public interface ThumbnailPlanUpdateUseCase {
    
    ThumbnailPlan update(String userId, String id, ThumbnailPlan plan);
}
