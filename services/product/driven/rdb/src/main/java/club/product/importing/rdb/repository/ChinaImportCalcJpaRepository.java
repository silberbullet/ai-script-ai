package club.product.importing.rdb.repository;

import club.product.importing.rdb.entity.ChinaImportCalcEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChinaImportCalcJpaRepository extends JpaRepository<ChinaImportCalcEntity, Long> {
   
    Optional<ChinaImportCalcEntity> findByUserIdAndProductSourcingId(Long userId, Long productSourcingId);
   
    Optional<ChinaImportCalcEntity> findByProductSourcingId(Long productSourcingId);
}