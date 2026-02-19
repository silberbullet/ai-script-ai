package club.product.planning.rdb.repository;

import club.product.planning.rdb.entity.ThumbnailFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbnailFileJpaRepository extends JpaRepository<ThumbnailFileEntity, Long> {
    
    List<ThumbnailFileEntity> findByProductPlanIdOrderBySortOrderAsc(Long productPlanId);
    
    void deleteByProductPlanId(Long productPlanId);
}
