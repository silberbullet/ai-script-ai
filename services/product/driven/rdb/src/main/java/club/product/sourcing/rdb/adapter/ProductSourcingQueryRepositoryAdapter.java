package club.product.sourcing.rdb.adapter;

import club.product.sourcing.port.ProductSourcingQueryRepositoryPort;
import club.product.sourcing.rdb.mapper.ProductSourcingEntityMapper;
import club.product.sourcing.rdb.projection.QProductSourcingProjections_ProductSourcingDetailProjection;
import club.product.sourcing.rdb.projection.QProductSourcingProjections_ProductSourcingSummaryProjection;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static club.product.sourcing.rdb.entity.QProductSourcingEntity.productSourcingEntity;

@Repository
@RequiredArgsConstructor
public class ProductSourcingQueryRepositoryAdapter implements ProductSourcingQueryRepositoryPort {
    
    private final ProductSourcingEntityMapper mapper;
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<ProductSourcingSummary> findProductSourcingSummaries(
            String userId,
            Instant fromInclusive,
            Instant toExclusive,
            String keyword,
            Instant cursorCreatedAt,
            String cursorId,
            int size
    ) {
        var sourcingList = queryFactory
                .select(new QProductSourcingProjections_ProductSourcingSummaryProjection(
                        productSourcingEntity.id,
                        productSourcingEntity.userId,
                        productSourcingEntity.keyword,
                        productSourcingEntity.referenceProduct,
                        productSourcingEntity.sourceUrl,
                        productSourcingEntity.imageUrl,
                        productSourcingEntity.name,
                        productSourcingEntity.costCny,
                        productSourcingEntity.costKrw,
                        productSourcingEntity.coupangCategory,
                        productSourcingEntity.wingLogisticsCategory,
                        productSourcingEntity.salePriceKrw,
                        productSourcingEntity.feeRatePercent,
                        productSourcingEntity.feeAmountKrw,
                        productSourcingEntity.vatKrw,
                        productSourcingEntity.grossMarginKrw,
                        productSourcingEntity.grossMarginRatePercent,
                        productSourcingEntity.minAdRoiPercent,
                        productSourcingEntity.createdAt,
                        productSourcingEntity.updatedAt
                ))
                .from(productSourcingEntity)
                .where(
                        productSourcingEntity.userId.eq((Long.valueOf(userId))),
                        betweenCreatedAt(fromInclusive, toExclusive),
                        keywordContains(keyword),
                        cursorCondition(cursorCreatedAt, cursorId)
                )
                .orderBy(
                        productSourcingEntity.createdAt.desc(),
                        productSourcingEntity.id.desc()
                )
                .limit(size)
                .fetch();
        
        return sourcingList.stream()
                .map(mapper::toDomainSummary)
                .toList();
    }
    
    @Override
    public Optional<ProductSourcingDetail> findProductSourcingDetail(String userId, String id) {
        return mapper.toOptionalProductSourcingDetail(
                queryFactory.select(new QProductSourcingProjections_ProductSourcingDetailProjection(
                                productSourcingEntity.id,
                                productSourcingEntity.userId,
                                productSourcingEntity.keyword,
                                productSourcingEntity.referenceProduct,
                                productSourcingEntity.sourceUrl,
                                productSourcingEntity.imageUrl,
                                productSourcingEntity.name,
                                productSourcingEntity.costCny,
                                productSourcingEntity.costKrw,
                                productSourcingEntity.coupangCategory,
                                productSourcingEntity.wingLogisticsCategory,
                                productSourcingEntity.salePriceKrw,
                                productSourcingEntity.feeRatePercent,
                                productSourcingEntity.feeAmountKrw,
                                productSourcingEntity.vatKrw,
                                productSourcingEntity.grossMarginKrw,
                                productSourcingEntity.grossMarginRatePercent,
                                productSourcingEntity.minAdRoiPercent,
                                productSourcingEntity.createdAt,
                                productSourcingEntity.updatedAt
                        ))
                        .from(productSourcingEntity)
                        .where(
                                productSourcingEntity.userId.eq((Long.valueOf(userId))),
                                productSourcingEntity.id.eq(Long.valueOf(id))
                        )
                        .fetchOne()
        );
    }
    
    private BooleanExpression betweenCreatedAt(Instant from, Instant to) {
        if (from == null || to == null) return null;
        return productSourcingEntity.createdAt.between(from, to);
    }
    
    private BooleanExpression cursorCondition(
            Instant cursorCreatedAt,
            String cursorId
    ) {
        if (cursorCreatedAt == null || cursorId == null) {
            return null;
        }
        
        return productSourcingEntity.createdAt.lt(cursorCreatedAt)
                .or(
                        productSourcingEntity.createdAt.eq(cursorCreatedAt)
                                .and(productSourcingEntity.id.lt(Long.valueOf(cursorId)))
                );
    }
    
    private static BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return productSourcingEntity.keyword.containsIgnoreCase(keyword.trim());
    }
}
