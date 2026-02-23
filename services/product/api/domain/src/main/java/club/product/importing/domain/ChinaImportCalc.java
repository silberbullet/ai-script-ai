package club.product.importing.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChinaImportCalc {
    // key
    private String id;
    
    // link
    private String productSourcingId;
    private String userId;
    
    // inputs
    private BigDecimal fxRateKrwPerCny;
    private BigDecimal forwardingFeeRate;   // 0~1
    private BigDecimal unitCostCny;
    private Integer qty;
    
    private BigDecimal sizeWCm;
    private BigDecimal sizeDCm;
    private BigDecimal sizeHCm;
    
    private Long inspectionPackPerUnitKrw;
    private Long barcodePerUnitKrw;
    private Long boxPalletTotalKrw;
    private Long cbmRateKrw;
    
    private Long customsBrokerKrw;
    private Long containerWorkKrw;
    private Long blIssueKrw;
    private Long ftaIssueKrw;
    
    private Long domesticShippingKrw;
    private Long milkRunKrw;
    
    // computed snapshot
    private BigDecimal inlandShippingCny;
    private Long inspectionPackTotalKrw;
    private Long barcodeTotalKrw;
    private Long productPurchaseKrw;
    private Long cbmFeeKrw;
    private Long customsDoneTotalKrw;
    private Long vatKrw;
    private Long totalKrw;
    private Long unitCostKrw;
    private BigDecimal multiple;
    private Integer estimatedBoxCount;
}
