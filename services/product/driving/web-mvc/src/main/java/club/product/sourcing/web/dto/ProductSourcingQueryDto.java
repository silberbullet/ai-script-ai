package club.product.sourcing.web.dto;

import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public final class ProductSourcingQueryDto {
    
    public record ProductSourcingSummaryDto(
            @Schema(description = "상품 소싱 목록 조회조건")
            String from,
            String to,
            String keyword
    ) {
    }
    
    @Builder
    public record ProductSourcingSummaryResponse(
            @Schema(description = "상품 소싱 목록")
            List<ProductSourcingSummary> productSourcingList
    ) {
    }
    
    @Builder
    public record ProductSourcingDetailResponse(
            @Schema(description = "상품 소싱")
            ProductSourcingDetail productSourcing
    ) {
    }
}
