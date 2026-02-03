package club.product.sourcing.usecase;

import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingCursorSummary;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.usecase.query.ProductSourcingSearchCondition;

public interface ProductSourcingReadUseCase {
    
    ProductSourcingCursorSummary getProductSourcingList(String userId, ProductSourcingSearchCondition searchCondition);
    
    ProductSourcingDetail getProductSourcing(String userId, String id);
}
