package club.product.planning.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.planning.usecase.DetailPagePlanReadUseCase;
import club.product.planning.usecase.query.PlanningSearchCondition;
import club.product.planning.web.dto.DetailPagePlanDto.SummaryResponseDto;
import club.product.planning.web.dto.DetailPagePlanDto.DetailResponseDto;
import club.product.planning.web.dto.PlanningQueryDto.SearchConditionDto;
import club.product.planning.web.mapper.PlanningDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Planning Api", description = "기획 API - 상세페이지")
@RestController
@RequestMapping("/planning/detail-page")
@RequiredArgsConstructor
public class DetailPagePlanQueryApi {
    
    private final DetailPagePlanReadUseCase readUseCase;
    private final PlanningDtoMapper mapper;
    
    @GetMapping("/{id}")
    public DetailResponseDto getDetailPagePlan(@AuthUser AuthorizedUser user, @PathVariable String id) {
        return DetailResponseDto.builder()
                .detailPagePlan(readUseCase.getDetailPagePlan(user.userId(), id).orElse(null))
                .build();
    }
    
    @GetMapping
    public SummaryResponseDto getDetailPagePlanList(@AuthUser AuthorizedUser user, SearchConditionDto dto) {
        var c = mapper.toSearchCondition(dto);
        int size = (dto.size() == null || dto.size() <= 0) ? 50 : dto.size();
        
        PlanningSearchCondition condition = PlanningSearchCondition.builder()
                .productSourcingId(c.productSourcingId())
                .from(c.from())
                .to(c.to())
                .cursorCreatedAt(c.cursorCreatedAt())
                .cursorId(c.cursorId())
                .size(size)
                .build();
        
        return SummaryResponseDto.builder()
                .detailPagePlanSummaries(readUseCase.getDetailPagePlanList(user.userId(), condition))
                .build();
    }
}
