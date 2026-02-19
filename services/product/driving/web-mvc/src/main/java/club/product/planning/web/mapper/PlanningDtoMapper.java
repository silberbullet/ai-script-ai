package club.product.planning.web.mapper;

import club.product.planning.detailpage.domain.DetailPagePlan;
import club.product.planning.thumbnail.domain.ThumbnailPlan;
import club.product.planning.usecase.query.PlanningSearchCondition;
import club.product.planning.web.dto.DetailPagePlanDto;
import club.product.planning.web.dto.PlanningQueryDto;
import club.product.planning.web.dto.ThumbnailPlanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanningDtoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    DetailPagePlan toDomain(DetailPagePlanDto.UpsertCommand dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    ThumbnailPlan toDomain(ThumbnailPlanDto.UpsertCommand dto);
    
    PlanningSearchCondition toSearchCondition(PlanningQueryDto.SearchConditionDto dto);
}
