package club.product.planning.usecase.query;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PlanningSearchCondition(
        String productSourcingId,
        Instant from,
        Instant to,
        Instant cursorCreatedAt,
        String cursorId,
        int size
) {}