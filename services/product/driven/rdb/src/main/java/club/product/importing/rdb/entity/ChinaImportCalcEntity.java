package club.product.importing.rdb.entity;

import club.jpa.support.SnowflakeBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(schema = "product", name = "china_import_calc")
@NoArgsConstructor
@AllArgsConstructor
public class ChinaImportCalcEntity extends SnowflakeBaseTimeEntity {
    
    @Column(name = "user_id")
    public Long userId;
    
    @Column(name = "product_sourcing_id")
    public Long productSourcingId;
    
    // inputs
    @Column(name = "fx_rate_krw_per_cny", precision = 12, scale = 4)
    public BigDecimal fxRateKrwPerCny;
    
    @Column(name = "forwarding_fee_rate", precision = 8, scale = 6)
    public BigDecimal forwardingFeeRate;
    
    @Column(name = "unit_cost_cny", precision = 14, scale = 6)
    public BigDecimal unitCostCny;
    
    @Column(name = "qty")
    public Integer qty;
    
    @Column(name = "size_w_cm", precision = 10, scale = 3)
    public BigDecimal sizeWCm;
    
    @Column(name = "size_d_cm", precision = 10, scale = 3)
    public BigDecimal sizeDCm;
    
    @Column(name = "size_h_cm", precision = 10, scale = 3)
    public BigDecimal sizeHCm;
    
    @Column(name = "inspection_pack_per_unit_krw")
    public Long inspectionPackPerUnitKrw;
    
    @Column(name = "barcode_per_unit_krw")
    public Long barcodePerUnitKrw;
    
    @Column(name = "box_pallet_total_krw")
    public Long boxPalletTotalKrw;
    
    @Column(name = "cbm_rate_krw")
    public Long cbmRateKrw;
    
    @Column(name = "customs_broker_krw")
    public Long customsBrokerKrw;
    
    @Column(name = "container_work_krw")
    public Long containerWorkKrw;
    
    @Column(name = "bl_issue_krw")
    public Long blIssueKrw;
    
    @Column(name = "fta_issue_krw")
    public Long ftaIssueKrw;
    
    @Column(name = "domestic_shipping_krw")
    public Long domesticShippingKrw;
    
    @Column(name = "milk_run_krw")
    public Long milkRunKrw;
    
    // computed snapshot
    @Column(name = "inland_shipping_cny", precision = 14, scale = 6)
    public BigDecimal inlandShippingCny;
    
    @Column(name = "inspection_pack_total_krw")
    public Long inspectionPackTotalKrw;
    
    @Column(name = "barcode_total_krw")
    public Long barcodeTotalKrw;
    
    @Column(name = "product_purchase_krw")
    public Long productPurchaseKrw;
    
    @Column(name = "cbm_fee_krw")
    public Long cbmFeeKrw;
    
    @Column(name = "customs_done_total_krw")
    public Long customsDoneTotalKrw;
    
    @Column(name = "vat_krw")
    public Long vatKrw;
    
    @Column(name = "total_krw")
    public Long totalKrw;
    
    @Column(name = "unit_cost_krw")
    public Long unitCostKrw;
    
    @Column(name = "multiple", precision = 14, scale = 6)
    public BigDecimal multiple;
    
    @Column(name = "estimated_box_count")
    public Integer estimatedBoxCount;
    
    @Builder(
            builderClassName = "UpdateChinaImportCalcEntityBuilder",
            builderMethodName = "prepareUpdate",
            buildMethodName = "update"
    )
    public void update(
            // inputs
            BigDecimal fxRateKrwPerCny,
            BigDecimal forwardingFeeRate,
            BigDecimal unitCostCny,
            Integer qty,
            
            BigDecimal sizeWCm,
            BigDecimal sizeDCm,
            BigDecimal sizeHCm,
            
            Long inspectionPackPerUnitKrw,
            Long barcodePerUnitKrw,
            Long boxPalletTotalKrw,
            Long cbmRateKrw,
            
            Long customsBrokerKrw,
            Long containerWorkKrw,
            Long blIssueKrw,
            Long ftaIssueKrw,
            
            Long domesticShippingKrw,
            Long milkRunKrw,
            
            // computed snapshot
            BigDecimal inlandShippingCny,
            Long inspectionPackTotalKrw,
            Long barcodeTotalKrw,
            Long productPurchaseKrw,
            Long cbmFeeKrw,
            Long customsDoneTotalKrw,
            Long vatKrw,
            Long totalKrw,
            Long unitCostKrw,
            BigDecimal multiple,
            Integer estimatedBoxCount
    ) {
        
        // inputs
        this.fxRateKrwPerCny = fxRateKrwPerCny;
        this.forwardingFeeRate = forwardingFeeRate;
        this.unitCostCny = unitCostCny;
        this.qty = qty;
        
        this.sizeWCm = sizeWCm;
        this.sizeDCm = sizeDCm;
        this.sizeHCm = sizeHCm;
        
        this.inspectionPackPerUnitKrw = inspectionPackPerUnitKrw;
        this.barcodePerUnitKrw = barcodePerUnitKrw;
        this.boxPalletTotalKrw = boxPalletTotalKrw;
        this.cbmRateKrw = cbmRateKrw;
        
        this.customsBrokerKrw = customsBrokerKrw;
        this.containerWorkKrw = containerWorkKrw;
        this.blIssueKrw = blIssueKrw;
        this.ftaIssueKrw = ftaIssueKrw;
        
        this.domesticShippingKrw = domesticShippingKrw;
        this.milkRunKrw = milkRunKrw;
        
        // computed snapshot
        this.inlandShippingCny = inlandShippingCny;
        this.inspectionPackTotalKrw = inspectionPackTotalKrw;
        this.barcodeTotalKrw = barcodeTotalKrw;
        this.productPurchaseKrw = productPurchaseKrw;
        this.cbmFeeKrw = cbmFeeKrw;
        this.customsDoneTotalKrw = customsDoneTotalKrw;
        this.vatKrw = vatKrw;
        this.totalKrw = totalKrw;
        this.unitCostKrw = unitCostKrw;
        this.multiple = multiple;
        this.estimatedBoxCount = estimatedBoxCount;
    }
}
