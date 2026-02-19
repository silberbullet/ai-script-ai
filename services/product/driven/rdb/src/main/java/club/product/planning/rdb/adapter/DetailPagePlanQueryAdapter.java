package club.product.planning.rdb.adapter;

import club.product.planning.port.DetailPagePlanQueryRepositoryPort;
import club.product.planning.rdb.mapper.PlanningEntityMapper;
import club.product.planning.rdb.projection.QPlanningProjections_DetailPagePlanDetailProjection;
import club.product.planning.rdb.projection.QPlanningProjections_DetailPagePlanSummaryProjection;
import club.product.planning.rdb.repository.DetailPageFileJpaRepository;
import club.product.planning.readmodel.PlanningReadModel.DetailPageFileItem;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanDetail;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanSummary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static club.product.planning.rdb.entity.QDetailPagePlanEntity.detailPagePlanEntity;

@Repository
@RequiredArgsConstructor
public class DetailPagePlanQueryAdapter implements DetailPagePlanQueryRepositoryPort {
    
    private final JPAQueryFactory queryFactory;
    private final PlanningEntityMapper mapper;
    private final DetailPageFileJpaRepository fileRepo;
    
    @Override
    public Optional<DetailPagePlanDetail> findDetailPagePlan(String userId, String id) {
        var detailPagePlan = queryFactory.select(new QPlanningProjections_DetailPagePlanDetailProjection(
                        detailPagePlanEntity.id,
                        detailPagePlanEntity.userId,
                        detailPagePlanEntity.productSourcingId,
                        detailPagePlanEntity.name,
                        detailPagePlanEntity.status,
                        detailPagePlanEntity.progress,
                        detailPagePlanEntity.currentStep,
                        detailPagePlanEntity.payloadJson,
                        detailPagePlanEntity.resultSummaryJson,
                        detailPagePlanEntity.errorCode,
                        detailPagePlanEntity.errorMessage,
                        detailPagePlanEntity.startedAt,
                        detailPagePlanEntity.finishedAt,
                        detailPagePlanEntity.createdAt,
                        detailPagePlanEntity.updatedAt
                ))
                .from(detailPagePlanEntity)
                .where(
                        detailPagePlanEntity.userId.eq(Long.valueOf(userId)),
                        detailPagePlanEntity.id.eq(Long.valueOf(id))
                )
                .fetchOne();
        
        if (detailPagePlan == null) return Optional.empty();
        
        var detail = mapper.toDetailModel(detailPagePlan);
        
        var files = fileRepo.findByProductPlanIdOrderBySortOrderAsc(Long.valueOf(id)).stream()
                .map(file -> DetailPageFileItem.builder()
                        .id(String.valueOf(file.getId()))
                        .type(file.type)
                        .fileUrl(file.fileUrl)
                        .sortOrder(file.sortOrder == null ? 0 : file.sortOrder)
                        .metaJson(file.metaJson)
                        .build())
                .toList();
        
        var rebuilt = DetailPagePlanDetail.builder()
                .id(detail.id())
                .userId(detail.userId())
                .sourcingId(detail.sourcingId())
                .name(detail.name())
                .status(detail.status())
                .progress(detail.progress())
                .currentStep(detail.currentStep())
                .payloadJson(detail.payloadJson())
                .resultSummaryJson(detail.resultSummaryJson())
                .errorCode(detail.errorCode())
                .errorMessage(detail.errorMessage())
                .startedAt(detail.startedAt())
                .finishedAt(detail.finishedAt())
                .createAt(detail.createAt())
                .updateAt(detail.updateAt())
                .files(files)
                .build();
        
        return Optional.of(rebuilt);
    }
    
    @Override
    public List<DetailPagePlanSummary> findDetailPagePlanSummaries(String userId, String productSourcingId, Instant fromInclusive, Instant toExclusive, Instant cursorCreatedAt, String cursorId, int sizePlusOne) {
        return queryFactory.select(new QPlanningProjections_DetailPagePlanSummaryProjection(
                        detailPagePlanEntity.id,
                        detailPagePlanEntity.userId,
                        detailPagePlanEntity.productSourcingId,
                        detailPagePlanEntity.name,
                        detailPagePlanEntity.status,
                        detailPagePlanEntity.progress,
                        detailPagePlanEntity.createdAt,
                        detailPagePlanEntity.updatedAt
                ))
                .from(detailPagePlanEntity)
                .where(
                        detailPagePlanEntity.userId.eq(Long.valueOf(userId)),
                        eqSourcingId(productSourcingId),
                        betweenCreatedAt(fromInclusive, toExclusive),
                        cursor(cursorCreatedAt, cursorId)
                )
                .orderBy(detailPagePlanEntity.createdAt.desc(), detailPagePlanEntity.id.desc())
                .limit(sizePlusOne)
                .fetch()
                .stream()
                .map(mapper::toSummaryModel)
                .toList();
    }
    
    private BooleanExpression eqSourcingId(String productSourcingId) {
        if (productSourcingId == null || productSourcingId.isBlank()) return null;
        
        return detailPagePlanEntity.productSourcingId.eq(Long.valueOf(productSourcingId));
    }
    
    private BooleanExpression betweenCreatedAt(Instant from, Instant to) {
        if (from == null && to == null) return null;
        
        if (from != null && to == null) return detailPagePlanEntity.createdAt.goe(from);
        
        if (from == null) return detailPagePlanEntity.createdAt.lt(to);
        
        return detailPagePlanEntity.createdAt.goe(from).and(detailPagePlanEntity.createdAt.lt(to));
    }
    
    private BooleanExpression cursor(Instant cursorCreatedAt, String cursorId) {
        if (cursorCreatedAt == null || cursorId == null || cursorId.isBlank()) return null;
        
        return detailPagePlanEntity.createdAt.lt(cursorCreatedAt)
                .or(detailPagePlanEntity.createdAt.eq(cursorCreatedAt).and(detailPagePlanEntity.id.lt(Long.valueOf(cursorId))));
    }
}
