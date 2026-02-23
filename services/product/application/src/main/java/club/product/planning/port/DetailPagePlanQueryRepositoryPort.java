package club.product.planning.port;

import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanDetail;

import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanSummary;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DetailPagePlanQueryRepositoryPort {
    
    Optional<DetailPagePlanDetail> findDetailPagePlan(String userId, String id);
    
    List<DetailPagePlanSummary> findDetailPagePlanSummaries(
            String userId,
            String sourcingId,
            Instant fromInclusive,
            Instant toExclusive,
            Instant cursorCreatedAt,
            String cursorId,
            int sizePlusOne
    );
}
