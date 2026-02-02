package club.product.sourcing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSourcing {
    
    private String id;
    private String userId;

    /* =====================
       엑셀: 소싱 메타
    ====================== */
    
    /** 엑셀: 키워드 (B열) */
    private String keyword;
    
    /** 엑셀: 참고 상품 (C열) */
    private String referenceProduct;
    
    /** 엑셀: 1688 URL (D열) */
    private String sourceUrl;
    
    /** 엑셀: 상품 이미지 (E열) */
    private String imageUrl;
    
    /** 엑셀: 상품명 (Q열) */
    private String name;

    /* =====================
       원가
    ====================== */
    
    /** 엑셀: 원가(위안) (F열) */
    private Long costCny;
    
    /** 엑셀: 원가(원) (G열) */
    private Long costKrw;

    /* =====================
       카테고리
    ====================== */
    
    /** 엑셀: 쿠팡 카테고리 (H열) */
    private String coupangCategory;
    
    /** 엑셀: 입출고 및 배송 카테고리 (I열) */
    private String wingLogisticsCategory;

    /* =====================
       판매 / 수익
    ====================== */
    
    /** 엑셀: 판매가 (J열) */
    private Long salePriceKrw;
    
    /** 엑셀: 수수료(%) (K열) */
    private BigDecimal feeRatePercent;
    
    /** 엑셀: 판매 수수료(원) (L열) */
    private Long feeAmountKrw;
    
    /** 엑셀: 부가세 (M열) */
    private Long vatKrw;
    
    /** 엑셀: 그로스 마진 (N열) */
    private Long grossMarginKrw;
    
    /** 엑셀: 그로스 마진율(%) (O열) */
    private BigDecimal grossMarginRatePercent;
    
    /** 엑셀: 최소 광고 수익율(%) (P열) */
    private BigDecimal minAdRoiPercent;
    
    private Instant createAt;
    private Instant updateAt;
}

