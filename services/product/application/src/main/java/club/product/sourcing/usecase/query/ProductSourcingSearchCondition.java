package club.product.sourcing.usecase.query;

import java.time.Instant;

public record ProductSourcingSearchCondition(
        Instant from,
        Instant to,
        String keyword
) {}