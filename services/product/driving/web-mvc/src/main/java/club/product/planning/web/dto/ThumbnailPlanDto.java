package club.product.planning.web.dto;

import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.type.PlanStatus;
import lombok.Builder;

import java.util.List;

public final class ThumbnailPlanDto {
    
    private ThumbnailPlanDto() {
    }
    
    @Builder
    public record UpsertCommand(
            String sourcingId,
            String name,
            PlanStatus status,
            Integer progress,
            String currentStep,
            String payloadJson,
            String errorCode,
            String errorMessage
    ) {
    }
    
    @Builder
    public record IdResponse(
            String id
    ) {
    }
    
    @Builder
    public record DetailResponseDto(
            ThumbnailPlanDetail thumbnailPlan
    ) {
    }
    
    @Builder
    public record SummaryResponseDto(
            List<ThumbnailPlanSummary> thumbnailPlanSummaries
    ) {
    }
}
