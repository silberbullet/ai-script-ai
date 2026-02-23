package club.product.planning.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.planning.usecase.ThumbnailPlanCreateUseCase;
import club.product.planning.usecase.ThumbnailPlanDeleteUseCase;
import club.product.planning.usecase.ThumbnailPlanUpdateUseCase;
import club.product.planning.web.dto.ThumbnailPlanDto.IdResponse;
import club.product.planning.web.dto.ThumbnailPlanDto.UpsertCommand;
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

@Tag(name = "Planning API", description = "기획 API - 썸네일")
@RestController
@RequestMapping("/planning/thumbnail")
@RequiredArgsConstructor
public class ThumbnailPlanCommandApi {
    
    private final PlanningDtoMapper mapper;
    private final ThumbnailPlanCreateUseCase createUseCase;
    private final ThumbnailPlanUpdateUseCase updateUseCase;
    private final ThumbnailPlanDeleteUseCase deleteUseCase;
    
    @PostMapping
    public IdResponse create(
            @AuthUser AuthorizedUser user,
            @RequestBody UpsertCommand req
    ) {
        var plan = mapper.toDomain(req);
        var saved = createUseCase.create(user.userId(), plan);
        
        return IdResponse.builder()
                .id(saved.getId())
                .build();
    }
    
    @PutMapping("/{id}")
    public IdResponse update(
            @AuthUser AuthorizedUser user,
            @PathVariable String id,
            @RequestBody UpsertCommand req
    ) {
        var plan = mapper.toDomain(req);
        var updated = updateUseCase.update(user.userId(), id, plan);
        
        return IdResponse.builder().id(updated.getId()).build();
    }
    
    @DeleteMapping("/{id}")
    public void delete(@AuthUser AuthorizedUser user, @PathVariable String id) {
        deleteUseCase.delete(user.userId(), id);
    }
}
