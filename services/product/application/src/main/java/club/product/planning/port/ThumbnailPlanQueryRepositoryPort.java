package club.product.planning.port;

import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ThumbnailPlanQueryRepositoryPort {
    
    Optional<ThumbnailPlanDetail> findThumbnailPlanDetail(String userId, String id);
    
    List<ThumbnailPlanSummary> finThumbnailPlanSummary(
            String userId,
            String sourcingId,
            Instant fromInclusive,
            Instant toExclusive,
            Instant cursorCreatedAt,
            String cursorId,
            int sizePlusOne
    );
}
