package club.product.planning.readmodel;

import club.product.planning.type.PlanStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public final class PlanningReadModel {
    
    private PlanningReadModel() {}
    
    // ----- Detail Page -----
    @Builder
    public record DetailPagePlanSummary(
            String id,
            String userId,
            String sourcingId,
            String name,
            PlanStatus status,
            int progress,
            Instant createAt,
            Instant updateAt
    ) {}
    
    @Builder
    public record DetailPagePlanDetail(
            String id,
            String userId,
            String sourcingId,
            String name,
            PlanStatus status,
            int progress,
            String currentStep,
            String payloadJson,
            String resultSummaryJson,
            String errorCode,
            String errorMessage,
            Instant startedAt,
            Instant finishedAt,
            Instant createAt,
            Instant updateAt,
            List<DetailPageFileItem> files
    ) {}
    
    @Builder
    public record DetailPageFileItem(
            String id,
            String type,
            String fileUrl,
            int sortOrder,
            String metaJson
    ) {}
    
    // ----- Thumbnail -----
    @Builder
    public record ThumbnailPlanSummary(
            String id,
            String userId,
            String sourcingId,
            String name,
            PlanStatus status,
            int progress,
            Instant createAt,
            Instant updateAt
    ) {}
    
    @Builder
    public record ThumbnailPlanDetail(
            String id,
            String userId,
            String sourcingId,
            String name,
            PlanStatus status,
            int progress,
            String currentStep,
            String payloadJson,
            String errorCode,
            String errorMessage,
            Instant startedAt,
            Instant finishedAt,
            Instant createAt,
            Instant updateAt,
            List<ThumbnailFileItem> files
    ) {}
    
    @Builder
    public record ThumbnailFileItem(
            String id,
            String type,
            String fileUrl,
            int sortOrder,
            String metaJson
    ) {}
}
