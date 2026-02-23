package club.product.planning.usecase;

import club.product.planning.detailpage.domain.DetailPagePlan;

public interface DetailPagePlanUpdateUseCase {
    
    DetailPagePlan update(String userId, String id, DetailPagePlan plan);
}
