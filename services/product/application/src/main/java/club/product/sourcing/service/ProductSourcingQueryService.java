package club.product.sourcing.service;

import club.product.sourcing.exception.ProductErrorCode;
import club.product.sourcing.port.ProductSourcingQueryRepositoryPort;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingCursorSummary;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.usecase.ProductSourcingReadUseCase;
import club.product.sourcing.usecase.query.ProductSourcingSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

import static club.product.sourcing.exception.ProductErrorCode.SOURCING_NOT_FOUND;
import static club.product.sourcing.exception.ProductErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductSourcingQueryService implements ProductSourcingReadUseCase {
    
    private final ProductSourcingQueryRepositoryPort repository;
    
    @Override
    public ProductSourcingCursorSummary getProductSourcingList(
            String userId,
            ProductSourcingSearchCondition searchCondition
    ) {
        if (userId.isEmpty()) throw USER_NOT_FOUND.exception();
        
        Instant fromInclusive = searchCondition.from();
        Instant toExclusive = searchCondition.to();
        
        if ((fromInclusive != null && toExclusive != null) && !fromInclusive.isBefore(toExclusive)) {
            throw ProductErrorCode.INVALID_DATE_RANGE.exception();
        }
        
        var summaryList = repository.findProductSourcingSummaries(userId,
                fromInclusive,
                toExclusive,
                searchCondition.keyword(),
                searchCondition.cursorCreatedAt(),
                searchCondition.cursorId(),
                searchCondition.size() + 1);
        
        boolean hasNext = searchCondition.size() < summaryList.size();
        
        // size 갯수만 반환
        summaryList = hasNext ? summaryList.subList(0, searchCondition.size()) : summaryList;
        
        // 마지막 날짜, ID
        Instant nextCursorCreatedAt = null;
        String nextCursorId = null;
        
        if (!summaryList.isEmpty()) {
            var last = summaryList.getLast();
            nextCursorCreatedAt = last.createAt();
            nextCursorId = last.id();
        }
        
        return new ProductSourcingCursorSummary(summaryList, nextCursorCreatedAt, nextCursorId, hasNext);
    }
    
    @Override
    public ProductSourcingDetail getProductSourcing(String userId, String id) {
        Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(id, "id is required");
        
        return repository.findProductSourcingDetail(userId, id)
                .orElseThrow(SOURCING_NOT_FOUND::exception);
    }
}
