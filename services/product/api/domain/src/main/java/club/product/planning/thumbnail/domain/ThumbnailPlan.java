package club.product.planning.thumbnail.domain;

import club.product.planning.type.PlanStatus;
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
public class ThumbnailPlan {
    
    private String id;
    private String userId;
    
    /** 연결 소싱 ID (선택) */
    private String productSourcingId;
    
    /** 내부 기획명 */
    private String name;
    
    /** 상태/진행 */
    private PlanStatus status;
    private int progress;
    private String currentStep;
    
    /** 입력 스냅샷(JSON) */
    private String payloadJson;
    
    /** 오류 */
    private String errorCode;
    private String errorMessage;
    
    /** 실행 시간 */
    private String startedAt;
    private String finishedAt;
}