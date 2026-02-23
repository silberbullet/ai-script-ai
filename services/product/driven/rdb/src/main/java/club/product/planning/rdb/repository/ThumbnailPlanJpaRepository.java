package club.product.planning.rdb.repository;

import club.product.planning.rdb.entity.ThumbnailPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailPlanJpaRepository extends JpaRepository<ThumbnailPlanEntity, Long> {
   
    boolean existsByIdAndUserId(Long id, Long userId);
}
