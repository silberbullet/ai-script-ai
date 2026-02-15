package club.product.importing.usecase;

import club.product.importing.domain.ChinaImportCalc;

public interface ChinaImportCalcUpsertUseCase {
    ChinaImportCalc upsert(String userId, String productSourcingId, ChinaImportCalc calc);
}