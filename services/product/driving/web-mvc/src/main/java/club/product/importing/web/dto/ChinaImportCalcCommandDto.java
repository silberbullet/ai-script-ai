package club.product.importing.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

public final class ChinaImportCalcCommandDto {
    
    private ChinaImportCalcCommandDto() {}
    
    @Builder
    public record UpsertCommand(
            @Schema(description = "환율 (원/위안)")
            BigDecimal fxRateKrwPerCny,
            
            @Schema(description = "포워딩 수수료율 (0~1)")
            BigDecimal forwardingFeeRate,
            
            @Schema(description = "상품 단가 (위안)")
            BigDecimal unitCostCny,
            
            @Schema(description = "수량")
            Integer qty,
            
            @Schema(description = "박스 가로(cm)")
            BigDecimal sizeWCm,
            
            @Schema(description = "박스 세로/깊이(cm)")
            BigDecimal sizeDCm,
            
            @Schema(description = "박스 높이(cm)")
            BigDecimal sizeHCm,
            
            @Schema(description = "검수/포장 (개당, 원)")
            Long inspectionPackPerUnitKrw,
            
            @Schema(description = "바코드 (개당, 원)")
            Long barcodePerUnitKrw,
            
            @Schema(description = "박스/파렛트 (총액, 원)")
            Long boxPalletTotalKrw,
            
            @Schema(description = "CBM 단가 (원)")
            Long cbmRateKrw,
            
            @Schema(description = "관세사 비용 (원)")
            Long customsBrokerKrw,
            
            @Schema(description = "컨테이너 작업비 (원)")
            Long containerWorkKrw,
            
            @Schema(description = "BL 발급비 (원)")
            Long blIssueKrw,
            
            @Schema(description = "FTA 발급비 (원)")
            Long ftaIssueKrw,
            
            @Schema(description = "국내 운송비 (원)")
            Long domesticShippingKrw,
            
            @Schema(description = "밀크런 비용 (원)")
            Long milkRunKrw,
            
            // =========================
            // computed snapshot
            // =========================
            @Schema(description = "중국 내륙 운송비 (위안)")
            BigDecimal inlandShippingCny,
            
            @Schema(description = "검수/포장 총액 (원)")
            Long inspectionPackTotalKrw,
            
            @Schema(description = "바코드 총액 (원)")
            Long barcodeTotalKrw,
            
            @Schema(description = "상품 매입금 (원)")
            Long productPurchaseKrw,
            
            @Schema(description = "CBM 비용 (원)")
            Long cbmFeeKrw,
            
            @Schema(description = "통관 완료 총액 (원)")
            Long customsDoneTotalKrw,
            
            @Schema(description = "부가세 (원)")
            Long vatKrw,
            
            @Schema(description = "총 비용 (원)")
            Long totalKrw,
            
            @Schema(description = "개당 원가 (원)")
            Long unitCostKrw,
            
            @Schema(description = "배수")
            BigDecimal multiple,
            
            @Schema(description = "예상 박스 수")
            Integer estimatedBoxCount
    ) {}
}
