package club.product.planning.rdb.adapter;

import club.product.planning.port.ThumbnailPlanQueryRepositoryPort;
import club.product.planning.rdb.mapper.PlanningEntityMapper;
import club.product.planning.rdb.projection.QPlanningProjections_ThumbnailPlanDetailProjection;
import club.product.planning.rdb.projection.QPlanningProjections_ThumbnailPlanSummaryProjection;
import club.product.planning.rdb.repository.ThumbnailFileJpaRepository;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailFileItem;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static club.product.planning.rdb.entity.QThumbnailPlanEntity.thumbnailPlanEntity;

@Repository
@RequiredArgsConstructor
public class ThumbnailPlanQueryAdapter implements ThumbnailPlanQueryRepositoryPort {
    
    private final JPAQueryFactory queryFactory;
    private final PlanningEntityMapper mapper;
    private final ThumbnailFileJpaRepository fileRepo;
    
    @Override
    public Optional<ThumbnailPlanDetail> findThumbnailPlanDetail(String userId, String id) {
        var thumbnailPlan = queryFactory.select(new QPlanningProjections_ThumbnailPlanDetailProjection(
                        thumbnailPlanEntity.id,
                        thumbnailPlanEntity.userId,
                        thumbnailPlanEntity.productSourcingId,
                        thumbnailPlanEntity.name,
                        thumbnailPlanEntity.status,
                        thumbnailPlanEntity.progress,
                        thumbnailPlanEntity.currentStep,
                        thumbnailPlanEntity.payloadJson,
                        thumbnailPlanEntity.errorCode,
                        thumbnailPlanEntity.errorMessage,
                        thumbnailPlanEntity.startedAt,
                        thumbnailPlanEntity.finishedAt,
                        thumbnailPlanEntity.createdAt,
                        thumbnailPlanEntity.updatedAt
                ))
                .from(thumbnailPlanEntity)
                .where(
                        thumbnailPlanEntity.userId.eq(Long.valueOf(userId)),
                        thumbnailPlanEntity.id.eq(Long.valueOf(id))
                )
                .fetchOne();
        
        if (thumbnailPlan == null) return Optional.empty();
        
        var detail = mapper.toDetailModel(thumbnailPlan);
        
        var files = fileRepo.findByProductPlanIdOrderBySortOrderAsc(Long.valueOf(id)).stream()
                .map(file -> ThumbnailFileItem.builder()
                        .id(String.valueOf(file.getId()))
                        .type(file.type)
                        .fileUrl(file.fileUrl)
                        .sortOrder(file.sortOrder == null ? 0 : file.sortOrder)
                        .metaJson(file.metaJson)
                        .build())
                .toList();
        
        var rebuilt = ThumbnailPlanDetail.builder()
                .id(detail.id())
                .userId(detail.userId())
                .sourcingId(detail.sourcingId())
                .name(detail.name())
                .status(detail.status())
                .progress(detail.progress())
                .currentStep(detail.currentStep())
                .payloadJson(detail.payloadJson())
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
    public List<ThumbnailPlanSummary> finThumbnailPlanSummary(
            String userId,
            String sourcingId,
            Instant fromInclusive,
            Instant toExclusive,
            Instant cursorCreatedAt,
            String cursorId,
            int sizePlusOne
    ) {
        return queryFactory.select(new QPlanningProjections_ThumbnailPlanSummaryProjection(
                        thumbnailPlanEntity.id,
                        thumbnailPlanEntity.userId,
                        thumbnailPlanEntity.productSourcingId,
                        thumbnailPlanEntity.name,
                        thumbnailPlanEntity.status,
                        thumbnailPlanEntity.progress,
                        thumbnailPlanEntity.createdAt,
                        thumbnailPlanEntity.updatedAt
                ))
                .from(thumbnailPlanEntity)
                .where(
                        thumbnailPlanEntity.userId.eq(Long.valueOf(userId)),
                        eqSourcingId(sourcingId),
                        betweenCreatedAt(fromInclusive, toExclusive),
                        cursor(cursorCreatedAt, cursorId)
                )
                .orderBy(thumbnailPlanEntity.createdAt.desc(), thumbnailPlanEntity.id.desc())
                .limit(sizePlusOne)
                .fetch()
                .stream()
                .map(mapper::toSummaryModel)
                .toList();
    }
    
    private BooleanExpression eqSourcingId(String sourcingId) {
        if (sourcingId == null || sourcingId.isBlank()) return null;
        
        return thumbnailPlanEntity.productSourcingId.eq(Long.valueOf(sourcingId));
    }
    
    private BooleanExpression betweenCreatedAt(Instant from, Instant to) {
        if (from == null && to == null) return null;
        if (from != null && to == null) return thumbnailPlanEntity.createdAt.goe(from);
        if (from == null) return thumbnailPlanEntity.createdAt.lt(to);
        
        return thumbnailPlanEntity.createdAt.goe(from).and(thumbnailPlanEntity.createdAt.lt(to));
    }
    
    private BooleanExpression cursor(Instant cursorCreatedAt, String cursorId) {
        if (cursorCreatedAt == null || cursorId == null || cursorId.isBlank()) return null;
        
        return thumbnailPlanEntity.createdAt.lt(cursorCreatedAt)
                .or(thumbnailPlanEntity.createdAt.eq(cursorCreatedAt).and(thumbnailPlanEntity.id.lt(Long.valueOf(cursorId))));
    }
}
