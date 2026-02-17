package club.product.planning.thumbnail.domain;

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
public class ThumbnailFile {
    
    private String id;
    private String userId;
    private String productPlanId;
    
    /** THUMBNAIL / VARIANT */
    private String type;
    
    private String fileUrl;
    private int sortOrder;
    
    /** 메타(JSON) */
    private String metaJson;
}