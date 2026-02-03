package club.product.sourcing.readmodel;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class ProductSourcingReadModel {
    
    private ProductSourcingReadModel() {
    }
    
    @Builder
    public record ProductSourcingSummary(
            String id,
            String userId,

            /* =====================
               엑셀: 소싱 메타
            ====================== */
            
            String keyword,
            String referenceProduct,
            String sourceUrl,
            
            String imageUrl,
            String name,

            /* =====================
               원가
            ====================== */
            
            Long costCny,
            Long costKrw,

            /* =====================
               카테고리
            ====================== */
            
            String coupangCategory,
            String wingLogisticsCategory,

            /* =====================
               판매 / 수익
            ====================== */
            
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
    }
    
    @Builder
    public record ProductSourcingDetail(
            String id,
            String userId,

            /* =====================
               엑셀: 소싱 메타
            ====================== */
            
            String keyword,
            String referenceProduct,
            String sourceUrl,
            
            String imageUrl,
            String name,

            /* =====================
               원가
            ====================== */
            
            Long costCny,
            Long costKrw,

            /* =====================
               카테고리
            ====================== */
            
            String coupangCategory,
            String wingLogisticsCategory,

            /* =====================
               판매 / 수익
            ====================== */
            
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
    }
    
    public record ProductSourcingCursorSummary(
            List<ProductSourcingSummary> summaryList,
            Instant nextCursorCreatedAt,
            String nextCursorId,
            boolean hasNext
    ) {
    }
}
