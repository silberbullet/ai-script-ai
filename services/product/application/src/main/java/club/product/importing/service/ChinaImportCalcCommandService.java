package club.product.importing.service;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.port.ChinaImportCalcCommandRepositoryPort;
import club.product.importing.usecase.ChinaImportCalcUpsertUseCase;
import club.product.sourcing.port.ProductSourcingCommandRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static club.product.sourcing.exception.ProductErrorCode.SOURCING_FORBIDDEN;
import static club.product.sourcing.exception.ProductErrorCode.SOURCING_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ChinaImportCalcCommandService implements ChinaImportCalcUpsertUseCase {
    
    private final ProductSourcingCommandRepositoryPort sourcingCommandRepository;
    private final ChinaImportCalcCommandRepositoryPort importCalcCommandRepository;
    
    @Override
    public ChinaImportCalc upsert(String userId, String productSourcingId, ChinaImportCalc calc) {
        // 저장 처리
        if (calc.getId() == null || calc.getId().isEmpty()) {
            return importCalcCommandRepository.saveChinaImportCalc(userId, productSourcingId, calc);
        }
        // 수정 처리
        else {
            // 소싱 존재 + 소유권 검증
            var sourcing = sourcingCommandRepository.findById(productSourcingId)
                    .orElseThrow(SOURCING_NOT_FOUND::exception);
            
            if (!sourcing.getUserId().equals(userId)) {
                throw SOURCING_FORBIDDEN.exception();
            }
            
            return importCalcCommandRepository.updateChinaImportCalc(calc);
        }
    }
}
