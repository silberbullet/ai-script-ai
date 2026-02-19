package club.product.planning.rdb.repository;

import club.product.planning.rdb.entity.DetailPagePlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailPagePlanJpaRepository extends JpaRepository<DetailPagePlanEntity, Long> {
    
    boolean existsByIdAndUserId(Long id, Long userId);
}
