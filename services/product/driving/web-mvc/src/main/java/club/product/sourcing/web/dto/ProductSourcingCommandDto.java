package club.product.sourcing.web.dto;

import club.product.sourcing.domain.ProductSourcing;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public final class ProductSourcingCommandDto {
    
    private ProductSourcingCommandDto() {
    }
    
    @Builder
    public record ProductSourcingUpsertCommand(
            @Schema(description = "소싱 ID (신규 생성 시 null)", example = "12345")
            String id,
            
            @Schema(description = "키워드", example = "이어폰")
            String keyword,
            
            @Schema(description = "참고 상품", example = "도매꾹")
            String referenceProduct,
            
            @Schema(description = "상품명", example = "무선 블루투스 이어폰")
            String name,
            
            @Schema(description = "1688URL", example = "1688URL")
            String sourceUrl,
            
            @Schema(description = "상품 이미지 URL")
            String imageUrl,
            
            @Schema(description = "원가(위안)")
            Long costCny,
            
            @Schema(description = "원가(원)")
            Long costKrw,
            
            @Schema(description = "쿠팡 카테고리")
            String coupangCategory,
            
            @Schema(description = "입출고 및 배송 카테고리(윙)")
            String wingLogisticsCategory,
            
            @Schema(description = "판매가(원)")
            Long salePriceKrw,
            
            @Schema(description = "수수료율(%)")
            BigDecimal feeRatePercent,
            
            @Schema(description = "판매 수수료(원)")
            Long feeAmountKrw,
            
            @Schema(description = "부가세(원)")
            Long vatKrw,
            
            @Schema(description = "그로스 마진(원)")
            Long grossMarginKrw,
            
            @Schema(description = "그로스 마진율(%)")
            BigDecimal grossMarginRatePercent,
            
            @Schema(description = "최소 광고 수익률(%)")
            BigDecimal minAdRoiPercent
    ) {
    }
    
    @Builder
    public record ProductSourcingBulkSaveCommand(
            @NotEmpty(message = "저장할 소싱 목록이 비어있습니다.")
            @Valid
            @Schema(description = "생성/수정 대상 목록 (id=null → 생성, id!=null → 수정)")
            List<ProductSourcingUpsertCommand> upserts,
            
            @Schema(description = "삭제 대상 소싱 ID 목록")
            List<String> deleteIds
    ) {
    }
    
    @Builder
    public record ProductSourcingCommandResponse(
            @Schema(description = "처리된 상품 소싱 목록")
            List<ProductSourcing> productSourcingList
    ) {
    }
}
