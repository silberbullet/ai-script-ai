package club.product.sourcing.usecase;

import club.product.sourcing.domain.ProductSourcing;

import java.util.List;

public interface ProductSourcingCreateUseCase {
    
    List<ProductSourcing> create(String userId, List<ProductSourcing> sourcingList);
}
