package club.product.sourcing.port;

import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ProductSourcingQueryRepositoryPort {
    
    List<ProductSourcingSummary> findProductSourcingSummaries(String userId, Instant fromInclusive, Instant toExclusive, String keyword, Instant cursorCreatedAt, String cursorId, int size);
    
    Optional<ProductSourcingDetail> findProductSourcingDetail(String userId, String id);
}
