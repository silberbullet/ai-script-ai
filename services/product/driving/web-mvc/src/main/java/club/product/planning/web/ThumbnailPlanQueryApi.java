package club.product.planning.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.planning.usecase.ThumbnailPlanReadUseCase;
import club.product.planning.usecase.query.PlanningSearchCondition;
import club.product.planning.web.dto.PlanningQueryDto.SearchConditionDto;
import club.product.planning.web.dto.ThumbnailPlanDto.DetailResponseDto;
import club.product.planning.web.dto.ThumbnailPlanDto.SummaryResponseDto;
import club.product.planning.web.mapper.PlanningDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Planning API", description = "기획 API - 썸네일")
@RestController
@RequestMapping("/planning/thumbnail")
@RequiredArgsConstructor
public class ThumbnailPlanQueryApi {
    
    private final PlanningDtoMapper mapper;
    private final ThumbnailPlanReadUseCase readUseCase;
    
    @GetMapping("/{id}")
    public DetailResponseDto getThumbnailPlan(@AuthUser AuthorizedUser user, @PathVariable String id) {
        return DetailResponseDto.builder()
                .thumbnailPlan(readUseCase.getThumbnailPlan(user.userId(), id).orElse(null))
                .build();
    }
    
    @GetMapping
    public SummaryResponseDto getThumbnailPlanList(@AuthUser AuthorizedUser user, SearchConditionDto dto) {
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
                .thumbnailPlanSummaries(readUseCase.getThumbnailPlanList(user.userId(), condition))
                .build();
    }
}
