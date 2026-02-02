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
            String keyword
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
                        createdFrom(fromInclusive),
                        createdTo(toExclusive),
                        keywordContains(keyword)
                )
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
    
    private static BooleanExpression createdFrom(Instant fromInclusive) {
        return fromInclusive == null
                ? null
                : productSourcingEntity.createdAt.goe(fromInclusive);
    }
    
    private static BooleanExpression createdTo(Instant toExclusive) {
        return toExclusive == null
                ? null
                : productSourcingEntity.createdAt.lt(toExclusive);
    }
    
    private static BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return productSourcingEntity.keyword.containsIgnoreCase(keyword.trim());
    }
}
