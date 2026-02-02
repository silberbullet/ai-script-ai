package club.product.sourcing.rdb.projection;

import com.querydsl.core.annotations.QueryProjection;

import java.math.BigDecimal;
import java.time.Instant;

public final class ProductSourcingProjections {
    
    public record ProductSourcingSummaryProjection(
            Long id,
            Long userId,
            String keyword,
            String referenceProduct,
            String sourceUrl,
            String imageUrl,
            String name,
            Long costCny,
            Long costKrw,
            String coupangCategory,
            String wingLogisticsCategory,
            Long salePriceKrw,
            BigDecimal feeRatePercent,
            Long feeAmountKrw,
            Long vatKrw,
            Long grossMarginKrw,
            BigDecimal grossMarginRatePercent,
            BigDecimal minAdRoiPercent,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public ProductSourcingSummaryProjection {
        }
    }
    
    public record ProductSourcingDetailProjection(
            Long id,
            Long userId,
            String keyword,
            String referenceProduct,
            String sourceUrl,
            String imageUrl,
            String name,
            Long costCny,
            Long costKrw,
            String coupangCategory,
            String wingLogisticsCategory,
            Long salePriceKrw,
            BigDecimal feeRatePercent,
            Long feeAmountKrw,
            Long vatKrw,
            Long grossMarginKrw,
            BigDecimal grossMarginRatePercent,
            BigDecimal minAdRoiPercent,
            Instant createAt,
            Instant updateAt
    ) {
        @QueryProjection
        public ProductSourcingDetailProjection {
        }
    }
}
