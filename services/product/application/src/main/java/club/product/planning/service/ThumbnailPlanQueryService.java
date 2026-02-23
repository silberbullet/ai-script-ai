package club.product.planning.service;

import club.product.planning.port.ThumbnailPlanQueryRepositoryPort;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;
import club.product.planning.usecase.ThumbnailPlanReadUseCase;
import club.product.planning.usecase.query.PlanningSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static club.product.sourcing.exception.ProductErrorCode.INVALID_DATE_RANGE;
import static club.product.sourcing.exception.ProductErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ThumbnailPlanQueryService implements ThumbnailPlanReadUseCase {
    
    private final ThumbnailPlanQueryRepositoryPort repository;
    
    @Override
    public Optional<ThumbnailPlanDetail> getThumbnailPlan(String userId, String id) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        
        return repository.findThumbnailPlanDetail(userId, id);
    }
    
    @Override
    public List<ThumbnailPlanSummary> getThumbnailPlanList(String userId, PlanningSearchCondition condition) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        
        Instant fromInclusive = condition.from();
        Instant toExclusive = condition.to();
        
        if ((fromInclusive != null && toExclusive != null) && !fromInclusive.isBefore(toExclusive)) {
            throw INVALID_DATE_RANGE.exception();
        }
        
        int size = (condition.size() <= 0) ? 50 : condition.size();
        
        return repository.finThumbnailPlanSummary(
                userId,
                condition.productSourcingId(),
                fromInclusive,
                toExclusive,
                condition.cursorCreatedAt(),
                condition.cursorId(),
                size + 1
        );
    }
}
