package club.product.planning.port;

import club.product.planning.thumbnail.domain.ThumbnailPlan;

import java.util.function.Supplier;

public interface ThumbnailPlanCommandRepositoryPort {
    <T> T transaction(Supplier<T> supplier);
    
    ThumbnailPlan save(ThumbnailPlan plan);
    
    ThumbnailPlan update(String id, ThumbnailPlan plan);
    
    void delete(String id);
    
    boolean existsByIdAndUserId(String id, String userId);
    
    void deleteFilesByPlanId(String planId);
}
