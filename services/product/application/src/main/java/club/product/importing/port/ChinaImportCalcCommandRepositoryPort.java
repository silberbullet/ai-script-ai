package club.product.importing.port;

import club.product.importing.domain.ChinaImportCalc;

public interface ChinaImportCalcCommandRepositoryPort {
    
    ChinaImportCalc saveChinaImportCalc(String userId, String productSourcingId, ChinaImportCalc calc);
    
    ChinaImportCalc updateChinaImportCalc(ChinaImportCalc calc);
}