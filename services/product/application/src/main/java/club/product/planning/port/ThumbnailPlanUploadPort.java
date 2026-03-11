package club.product.planning.port;

import club.product.planning.thumbnail.domain.ThumbnailPlan;

public interface ThumbnailPlanUploadPort {
    
    void uploadThumbnailImage(ThumbnailPlan thumbnailPlan);
}
