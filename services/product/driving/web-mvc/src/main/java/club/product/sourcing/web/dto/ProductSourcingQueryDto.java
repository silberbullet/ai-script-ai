package club.product.sourcing.web.dto;

import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingCursorSummary;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public final class ProductSourcingQueryDto {
    
    public record ProductSourcingSummaryDto(
            @Schema(description = "상품 소싱 목록 조회조건")
            String from,
            String to,
            String keyword,
            String lasCursorCreatAt,
            String lastId,
            int size
    ) {
    }
    
    @Builder
    public record ProductSourcingSummaryResponse(
            @Schema(description = "상품 소싱 목록")
            ProductSourcingCursorSummary productSourcingList
    ) {
    }
    
    @Builder
    public record ProductSourcingDetailResponse(
            @Schema(description = "상품 소싱")
            ProductSourcingDetail productSourcing
    ) {
    }
}
