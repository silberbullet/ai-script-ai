package club.product.importing.service;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.port.ChinaImportCalcQueryRepositoryPort;
import club.product.importing.usecase.ChinaImportCalcReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static club.product.sourcing.exception.ProductErrorCode.CHINA_IMPORT_CALC_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChinaImportCalcQueryService implements ChinaImportCalcReadUseCase {
    
    private final ChinaImportCalcQueryRepositoryPort repository;
    
    @Override
    public ChinaImportCalc getChinaImportCalc(String userId, String productSourcingId) {
        // 조회 조건 검증
        if (userId == null && productSourcingId == null) {
            throw CHINA_IMPORT_CALC_NOT_FOUND.exception();
        }
        
        return repository.findByUserIdAndProductSourcingId(userId, productSourcingId)
                .orElseThrow(CHINA_IMPORT_CALC_NOT_FOUND::exception);
    }
}
