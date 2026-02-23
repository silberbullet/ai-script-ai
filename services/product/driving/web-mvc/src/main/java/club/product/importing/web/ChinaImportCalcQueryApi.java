package club.product.importing.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.importing.usecase.ChinaImportCalcReadUseCase;
import club.product.importing.web.dto.ChinaImportCalcQueryDto.ChinaImportCalcResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ChinaImportCalcApi", description = "중국 사입 계산기 API")
@RestController
@RequestMapping("/product/importing")
@RequiredArgsConstructor
public class ChinaImportCalcQueryApi {
    
    private final ChinaImportCalcReadUseCase readUseCase;
    
    @GetMapping("/{productSourcingId}")
    public ChinaImportCalcResponse get(
            @AuthUser AuthorizedUser user,
            @PathVariable String productSourcingId
    ) {
        return ChinaImportCalcResponse.builder()
                .chinaImportCalc(readUseCase.getChinaImportCalc(user.userId(), productSourcingId))
                .build();
    }
}
