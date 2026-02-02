package club.product.sourcing.service;

import club.product.sourcing.exception.ProductErrorCode;
import club.product.sourcing.port.ProductSourcingQueryRepositoryPort;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import club.product.sourcing.usecase.ProductSourcingReadUseCase;
import club.product.sourcing.usecase.query.ProductSourcingSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static club.product.sourcing.exception.ProductErrorCode.SOURCING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductSourcingQueryService implements ProductSourcingReadUseCase {
    
    private final ProductSourcingQueryRepositoryPort repository;
    
    @Override
    public List<ProductSourcingSummary> getProductSourcingList(
            String userId,
            ProductSourcingSearchCondition searchCondition
    ) {
        Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(searchCondition, "searchCondition is required");
        
        Instant fromInclusive = Objects.requireNonNull(searchCondition.from(), "fromInclusive is required");
        Instant toExclusive = Objects.requireNonNull(searchCondition.to(), "toExclusive is required");
        
        if (!fromInclusive.isBefore(toExclusive)) {
            throw ProductErrorCode.INVALID_DATE_RANGE.exception();
        }
        
        return repository.findProductSourcingSummaries(userId, fromInclusive, toExclusive, searchCondition.keyword());
    }
    
    @Override
    public ProductSourcingDetail getProductSourcing(String userId, String id) {
        Objects.requireNonNull(userId, "userId is required");
        Objects.requireNonNull(id, "id is required");
        
        return repository.findProductSourcingDetail(userId, id)
                .orElseThrow(SOURCING_NOT_FOUND::exception);
    }
}
