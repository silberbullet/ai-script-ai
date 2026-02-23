package club.product.planning.port;

import club.product.planning.detailpage.domain.DetailPagePlan;

import java.util.function.Supplier;

public interface DetailPagePlanCommandRepositoryPort {
    <T> T transaction(Supplier<T> supplier);
    
    DetailPagePlan save(DetailPagePlan plan);
    
    DetailPagePlan update(String id, DetailPagePlan plan);
    
    void delete(String id);
    
    boolean existsByIdAndUserId(String id, String userId);
    
    void deleteFilesByPlanId(String planId);
}
