package club.product.importing.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.importing.usecase.ChinaImportCalcUpsertUseCase;
import club.product.importing.web.dto.ChinaImportCalcCommandDto.UpsertCommand;
import club.product.importing.web.dto.ChinaImportCalcQueryDto.ChinaImportCalcResponse;
import club.product.importing.web.mapper.ChinaImportCalcDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ChinaImportCalcApi", description = "중국 사입 계산기 API")
@RestController
@RequestMapping("/product/importing")
@RequiredArgsConstructor
public class ChinaImportCalcCommandApi {
    
    private final ChinaImportCalcUpsertUseCase upsertUseCase;
    private final ChinaImportCalcDtoMapper mapper;
    
    @PostMapping("/{productSourcingId}")
    public ChinaImportCalcResponse upsert(
            @AuthUser AuthorizedUser user,
            @PathVariable String productSourcingId,
            @RequestBody UpsertCommand command
    ) {
        var domain = mapper.toDomain(user.userId(), productSourcingId, command);
        
        return ChinaImportCalcResponse.builder()
                .chinaImportCalc(upsertUseCase.upsert(user.userId(), productSourcingId, domain))
                .build();
    }
}
