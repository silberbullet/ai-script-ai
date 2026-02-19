package club.product.planning.rdb.entity;

import club.jpa.support.LongBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = "product", name = "thumbnail_file")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailFileEntity extends LongBaseTimeEntity {
    
    @Column(name = "user_id", nullable = false)
    public Long userId;
    
    @Column(name = "product_plan_id")
    public Long productPlanId;
    
    @Column(name = "type")
    public String type;
    
    @Column(name = "file_url", nullable = false)
    public String fileUrl;
    
    @Column(name = "sort_order", nullable = false)
    public Integer sortOrder;
    
    @Column(name = "meta_json", columnDefinition = "jsonb")
    public String metaJson;
}
