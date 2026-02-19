package club.product.planning.rdb.projection;

import com.querydsl.core.annotations.QueryProjection;

import java.time.Instant;

public final class PlanningProjections {
    
    private PlanningProjections() {}
    
    public record DetailPagePlanSummaryProjection(
            Long id,
            Long userId,
            Long sourcingId,
            String name,
            String status,
            Integer progress,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public DetailPagePlanSummaryProjection { }
    }
    
    public record DetailPagePlanDetailProjection(
            Long id,
            Long userId,
            Long sourcingId,
            String name,
            String status,
            Integer progress,
            String currentStep,
            String payloadJson,
            String resultSummaryJson,
            String errorCode,
            String errorMessage,
            Instant startedAt,
            Instant finishedAt,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public DetailPagePlanDetailProjection { }
    }
    
    public record ThumbnailPlanSummaryProjection(
            Long id,
            Long userId,
            Long sourcingId,
            String name,
            String status,
            Integer progress,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public ThumbnailPlanSummaryProjection { }
    }
    
    public record ThumbnailPlanDetailProjection(
            Long id,
            Long userId,
            Long sourcingId,
            String name,
            String status,
            Integer progress,
            String currentStep,
            String payloadJson,
            String errorCode,
            String errorMessage,
            Instant startedAt,
            Instant finishedAt,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public ThumbnailPlanDetailProjection { }
    }
}
