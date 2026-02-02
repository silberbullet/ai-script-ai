package club.product.sourcing.usecase;

import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import club.product.sourcing.usecase.query.ProductSourcingSearchCondition;

import java.util.List;

public interface ProductSourcingReadUseCase {
    
    List<ProductSourcingSummary> getProductSourcingList(String userId, ProductSourcingSearchCondition searchCondition);
    
    ProductSourcingDetail getProductSourcing(String userId, String id);
}
