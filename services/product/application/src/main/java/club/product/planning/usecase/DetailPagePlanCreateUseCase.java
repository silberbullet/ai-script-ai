package club.product.planning.usecase;

import club.product.planning.detailpage.domain.DetailPagePlan;

public interface DetailPagePlanCreateUseCase {
    
    DetailPagePlan create(String userId, DetailPagePlan plan);
}
