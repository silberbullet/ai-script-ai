package club.product.planning.rdb.entity;

import club.jpa.support.SnowflakeBaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = "product", name = "thumbnail_plan")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailPlanEntity extends SnowflakeBaseTimeEntity {
    
    @Column(name = "user_id", nullable = false)
    public Long userId;
    
    @Column(name = "product_sourcing_id")
    public Long productSourcingId;
    
    @Column(name = "name")
    public String name;
    
    @Column(name = "status")
    public String status;
    
    @Column(name = "progress")
    public Integer progress;
    
    @Column(name = "current_step")
    public String currentStep;
    
    @Column(name = "payload_json", nullable = false, columnDefinition = "jsonb")
    public String payloadJson;
    
    @Column(name = "error_code")
    public String errorCode;
    
    @Column(name = "error_message")
    public String errorMessage;
    
    @Column(name = "started_at")
    public java.time.Instant startedAt;
    
    @Column(name = "finished_at")
    public java.time.Instant finishedAt;
}
