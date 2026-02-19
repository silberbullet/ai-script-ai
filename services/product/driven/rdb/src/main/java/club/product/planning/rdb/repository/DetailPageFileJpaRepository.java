package club.product.planning.rdb.repository;

import club.product.planning.rdb.entity.DetailPageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailPageFileJpaRepository extends JpaRepository<DetailPageFileEntity, Long> {
    List<DetailPageFileEntity> findByProductPlanIdOrderBySortOrderAsc(Long productPlanId);
    
    void deleteByProductPlanId(Long planId);
}
