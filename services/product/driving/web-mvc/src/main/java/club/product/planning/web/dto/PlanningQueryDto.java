package club.product.planning.web.dto;

import lombok.Builder;

import java.time.Instant;

public final class PlanningQueryDto {
    
    private PlanningQueryDto() {}
    
    @Builder
    public record SearchConditionDto(
            String sourcingId,
            Instant from,
            Instant to,
            Instant cursorCreatedAt,
            String cursorId,
            Integer size
    ) {}
}
