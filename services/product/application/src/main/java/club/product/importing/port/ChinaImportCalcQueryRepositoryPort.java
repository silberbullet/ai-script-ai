package club.product.importing.port;

import club.product.importing.domain.ChinaImportCalc;

import java.util.Optional;

public interface ChinaImportCalcQueryRepositoryPort {
    Optional<ChinaImportCalc> findByUserIdAndProductSourcingId(String userId, String productSourcingId);
}