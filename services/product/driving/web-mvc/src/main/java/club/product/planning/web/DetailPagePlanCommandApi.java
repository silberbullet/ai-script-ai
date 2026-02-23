package club.product.planning.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.planning.usecase.DetailPagePlanCreateUseCase;
import club.product.planning.usecase.DetailPagePlanDeleteUseCase;
import club.product.planning.usecase.DetailPagePlanUpdateUseCase;
import club.product.planning.web.dto.DetailPagePlanDto.IdResponse;
import club.product.planning.web.dto.DetailPagePlanDto.UpsertCommand;
import club.product.planning.web.mapper.PlanningDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Planning Api", description = "기획 API - 상세페이지")
@RestController
@RequestMapping("/planning/detail-page")
@RequiredArgsConstructor
public class DetailPagePlanCommandApi {
    
    private final PlanningDtoMapper mapper;
    private final DetailPagePlanCreateUseCase createUseCase;
    private final DetailPagePlanUpdateUseCase updateUseCase;
    private final DetailPagePlanDeleteUseCase deleteUseCase;
    
    @PostMapping
    public IdResponse create(
            @AuthUser AuthorizedUser user,
            @RequestBody UpsertCommand req
    ) {
        String userId = String.valueOf(user.userId());
        var plan = mapper.toDomain(req);
        var saved = createUseCase.create(userId, plan);
        
        return IdResponse.builder().id(saved.getId()).build();
    }
    
    @PutMapping("/{id}")
    public IdResponse update(
            @AuthUser AuthorizedUser user,
            @PathVariable String id,
            @RequestBody UpsertCommand req
    ) {
        String userId = String.valueOf(user.userId());
        var plan = mapper.toDomain(req);
        var updated = updateUseCase.update(userId, id, plan);
        
        return IdResponse.builder().id(updated.getId()).build();
    }
    
    @DeleteMapping("/{id}")
    public void delete(@AuthUser AuthorizedUser user, @PathVariable String id) {
        String userId = String.valueOf(user.userId());
        
        deleteUseCase.delete(userId, id);
    }
}
