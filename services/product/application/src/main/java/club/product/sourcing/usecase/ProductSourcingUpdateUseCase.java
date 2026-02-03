package club.product.sourcing.usecase;

import club.product.sourcing.domain.ProductSourcing;

public interface ProductSourcingUpdateUseCase {
    ProductSourcing update(String userId, String id, ProductSourcing sourcing);
}
