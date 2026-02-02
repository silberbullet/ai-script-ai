package club.product.plan.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPlan {
    
    private String id;
    
    private String userId;
    
    /** 연결된 소싱 상품 */
    private String sourcingId;
    
    /** 내부 기획명 */
    private String name;
    
    /** 썸네일 파일 */
    private String thumbnailFileUrl;
    
    /** 상세페이지 파일 */
    private List<String> detailPageFileUrl;
}
