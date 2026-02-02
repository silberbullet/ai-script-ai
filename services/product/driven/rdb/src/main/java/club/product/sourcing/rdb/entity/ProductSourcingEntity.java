package club.product.sourcing.rdb.entity;

import club.jpa.support.SnowflakeBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(schema = "product", name = "product_sourcing")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSourcingEntity extends SnowflakeBaseTimeEntity {
    
    @Column(name = "user_id", nullable = false)
    public Long userId;
    
    /**
     * 엑셀: 키워드 (B열)
     */
    @Column(name = "keyword")
    public String keyword;
    
    /**
     * 엑셀: 참고 상품 (C열)
     */
    @Column(name = "reference_product")
    public String referenceProduct;
    
    /**
     * 엑셀: 1688 URL (D열)
     */
    @Column(name = "source_url")
    public String sourceUrl;
    
    /**
     * 엑셀: 이미지 (E열)
     */
    @Column(name = "image_url")
    public String imageUrl;
    
    /**
     * 엑셀: 상품명 (Q열)
     */
    @Column(nullable = false)
    public String name;
    
    /**
     * 엑셀: 원가(위안) (F열)
     */
    @Column(name = "cost_cny")
    public Long costCny;
    
    /**
     * 엑셀: 원가(원) (G열)
     */
    @Column(name = "cost_krw")
    public Long costKrw;
    
    /**
     * 엑셀: 쿠팡 카테고리 (H열)
     */
    @Column(name = "coupang_category")
    public String coupangCategory;
    
    /**
     * 엑셀: 입출고 및 배송 카테고리 (I열)
     */
    @Column(name = "wing_logistics_category")
    public String wingLogisticsCategory;
    
    /**
     * 엑셀: 판매가 (J열)
     */
    @Column(name = "sale_price_krw")
    public Long salePriceKrw;
    
    /**
     * 엑셀: 수수료(%) (K열)
     */
    @Column(name = "fee_rate_percent", precision = 10, scale = 4)
    public BigDecimal feeRatePercent;
    
    /**
     * 엑셀: 판매 수수료(원) (L열)
     */
    @Column(name = "fee_amount_krw")
    public Long feeAmountKrw;
    
    /**
     * 엑셀: 부가세 (M열)
     */
    @Column(name = "vat_krw")
    public Long vatKrw;
    
    /**
     * 엑셀: 그로스 마진 (N열)
     */
    @Column(name = "gross_margin_krw")
    public Long grossMarginKrw;
    
    /**
     * 엑셀: 그로스 마진율(%) (O열)
     */
    @Column(name = "gross_margin_rate_percent", precision = 10, scale = 4)
    public BigDecimal grossMarginRatePercent;
    
    /**
     * 엑셀: 최소 광고 수익율(%) (P열)
     */
    @Column(name = "min_ad_roi_percent", precision = 10, scale = 4)
    public BigDecimal minAdRoiPercent;
    
    @Builder(builderClassName = "UpdateProductSourcingEntityBuilder", builderMethodName = "prepareUpdate", buildMethodName = "update")
    public void update(String keyword, String referenceProduct, String sourceUrl, String imageUrl, String name, Long costCny, Long costKrw, String coupangCategory, String wingLogisticsCategory, Long salePriceKrw, BigDecimal feeRatePercent, Long feeAmountKrw, Long vatKrw, Long grossMarginKrw, BigDecimal grossMarginRatePercent, BigDecimal minAdRoiPercent) {
        this.keyword = keyword;
        this.referenceProduct = referenceProduct;
        this.sourceUrl = sourceUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.costCny = costCny;
        this.costKrw = costKrw;
        this.coupangCategory = coupangCategory;
        this.wingLogisticsCategory = wingLogisticsCategory;
        this.salePriceKrw = salePriceKrw;
        this.feeRatePercent = feeRatePercent;
        this.feeAmountKrw = feeAmountKrw;
        this.vatKrw = vatKrw;
        this.grossMarginKrw = grossMarginKrw;
        this.grossMarginRatePercent = grossMarginRatePercent;
        this.minAdRoiPercent = minAdRoiPercent;
    }
}
