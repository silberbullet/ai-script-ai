package club.product.planning.detailpage.domain;

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
public class DetailPagePlan {
    
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
    
    /** 결과 요약(JSON, 선택) */
    private String resultSummaryJson;
    
    /** 오류 */
    private String errorCode;
    private String errorMessage;
    
    /** 실행 시간 */
    private String startedAt;   // ISO-8601 문자열로 유지(초기 단순화)
    private String finishedAt;
}
