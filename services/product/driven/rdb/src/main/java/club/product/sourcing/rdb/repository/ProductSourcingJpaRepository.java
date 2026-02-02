package club.product.sourcing.rdb.repository;

import club.product.sourcing.rdb.entity.ProductSourcingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSourcingJpaRepository extends JpaRepository<ProductSourcingEntity, Long> {
}
