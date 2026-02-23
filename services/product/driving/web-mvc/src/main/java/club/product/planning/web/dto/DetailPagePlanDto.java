package club.product.planning.web.dto;


import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanDetail;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanSummary;
import club.product.planning.type.PlanStatus;
import lombok.Builder;

import java.util.List;

public final class DetailPagePlanDto {
    
    private DetailPagePlanDto() {
    }
    
    @Builder
    public record UpsertCommand(
            String sourcingId,
            String name,
            PlanStatus status,
            Integer progress,
            String currentStep,
            String payloadJson,
            String resultSummaryJson,
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
            DetailPagePlanDetail detailPagePlan
    ) {
    }
    
    @Builder
    public record SummaryResponseDto(
            List<DetailPagePlanSummary> detailPagePlanSummaries
    ) {
    }
}
