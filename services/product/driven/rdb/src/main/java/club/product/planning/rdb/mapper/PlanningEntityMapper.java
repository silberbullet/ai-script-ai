package club.product.planning.rdb.mapper;

import club.product.planning.detailpage.domain.DetailPagePlan;
import club.product.planning.rdb.entity.DetailPagePlanEntity;
import club.product.planning.rdb.entity.ThumbnailPlanEntity;
import club.product.planning.rdb.projection.PlanningProjections.DetailPagePlanDetailProjection;
import club.product.planning.rdb.projection.PlanningProjections.DetailPagePlanSummaryProjection;
import club.product.planning.rdb.projection.PlanningProjections.ThumbnailPlanDetailProjection;
import club.product.planning.rdb.projection.PlanningProjections.ThumbnailPlanSummaryProjection;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanDetail;
import club.product.planning.readmodel.PlanningReadModel.DetailPagePlanSummary;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanDetail;
import club.product.planning.readmodel.PlanningReadModel.ThumbnailPlanSummary;
import club.product.planning.thumbnail.domain.ThumbnailPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanningEntityMapper {
    
    // ----- Domain <-> Entity -----
    @Mapping(target = "status", expression = "java(plan.getStatus().name())")
    @Mapping(target = "progress", source = "progress")
    DetailPagePlanEntity toEntity(DetailPagePlan plan);
    
    DetailPagePlan toDomain(DetailPagePlanEntity entity);
    
    @Mapping(target = "status", expression = "java(plan.getStatus().name())")
    @Mapping(target = "progress", source = "progress")
    ThumbnailPlanEntity toEntity(ThumbnailPlan plan);
    
    ThumbnailPlan toDomain(ThumbnailPlanEntity entity);
    
    // ----- Projection -> ReadModel -----
    DetailPagePlanSummary toSummaryModel(DetailPagePlanSummaryProjection p);
    
    DetailPagePlanDetail toDetailModel(DetailPagePlanDetailProjection projection);
    
    ThumbnailPlanSummary toSummaryModel(ThumbnailPlanSummaryProjection projection);
    
    ThumbnailPlanDetail toDetailModel(ThumbnailPlanDetailProjection projection);
}
