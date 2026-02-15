package club.product.importing.web.dto;

import club.product.importing.domain.ChinaImportCalc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public final class ChinaImportCalcQueryDto {
    
    @Builder
    public record ChinaImportCalcResponse(
            @Schema(description = "중국 사입 계산서")
            ChinaImportCalc chinaImportCalc
    ) {
    }
}
