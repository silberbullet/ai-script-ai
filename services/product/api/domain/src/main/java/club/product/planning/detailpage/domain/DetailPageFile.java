package club.product.planning.detailpage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailPageFile {
    
    private String id;
    
    private String userId;
    
    private String productPlanId;
    
    /** SECTION_IMAGE / ZIP / OTHER */
    private String type;
    
    private String fileUrl;
    
    private int sortOrder;
    
    /** 섹션 template, title 등 메타(JSON) */
    private String metaJson;
}