package club.product.planning.usecase;

import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;
import club.product.planning.usecase.query.PlanningSearchCondition;

import java.util.List;
import java.util.Optional;

public interface ThumbnailPlanReadUseCase {
    
    Optional<ThumbnailPlanDetail> getThumbnailPlan(String userId, String id);
    
    List<ThumbnailPlanSummary> getThumbnailPlanList(String userId, PlanningSearchCondition condition);
}
