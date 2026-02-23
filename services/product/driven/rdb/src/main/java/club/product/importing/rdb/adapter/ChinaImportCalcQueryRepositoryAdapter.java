package club.product.importing.rdb.adapter;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.port.ChinaImportCalcQueryRepositoryPort;
import club.product.importing.rdb.mapper.ChinaImportCalcEntityMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static club.product.importing.rdb.entity.QChinaImportCalcEntity.chinaImportCalcEntity;

@Repository
@RequiredArgsConstructor
public class ChinaImportCalcQueryRepositoryAdapter implements ChinaImportCalcQueryRepositoryPort {
    
    private final ChinaImportCalcEntityMapper mapper;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public Optional<ChinaImportCalc> findByUserIdAndProductSourcingId(String userId, String productSourcingId) {
        return mapper.toOptionalDomain(
                queryFactory.select(chinaImportCalcEntity)
                        .from(chinaImportCalcEntity)
                        .where(
                                eqUserId(userId),
                                eqProductSourcingId(productSourcingId)
                        )
                        .fetchOne()
        
        );
    }
    
    private static BooleanExpression eqUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            return null;
        }
        
        return chinaImportCalcEntity.userId.eq((Long.valueOf(userId)));
    }
    
    private static BooleanExpression eqProductSourcingId(String productSourcingId) {
        if (productSourcingId == null || productSourcingId.isBlank()) {
            return null;
        }
        
        return chinaImportCalcEntity.productSourcingId.eq((Long.valueOf(productSourcingId)));
    }
}