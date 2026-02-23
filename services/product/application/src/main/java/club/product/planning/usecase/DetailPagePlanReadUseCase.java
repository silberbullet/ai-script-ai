package club.product.planning.usecase;

import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanDetail;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanSummary;
import club.product.planning.usecase.query.PlanningSearchCondition;

import java.util.List;
import java.util.Optional;

public interface DetailPagePlanReadUseCase {
    
    Optional<DetailPagePlanDetail> getDetailPagePlan(String userId, String id);
    
    List<DetailPagePlanSummary> getDetailPagePlanList(String userId, PlanningSearchCondition condition);
}
