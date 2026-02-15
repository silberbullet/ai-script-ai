package club.product.importing.usecase;

import club.product.importing.domain.ChinaImportCalc;

public interface ChinaImportCalcReadUseCase {
    ChinaImportCalc getChinaImportCalc(String userId, String productSourcingId);
}
