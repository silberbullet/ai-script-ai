package club.product.importing.rdb.adapter;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.port.ChinaImportCalcCommandRepositoryPort;
import club.product.importing.rdb.mapper.ChinaImportCalcEntityMapper;
import club.product.importing.rdb.repository.ChinaImportCalcJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static club.product.sourcing.exception.ProductErrorCode.CHINA_IMPORT_CALC_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ChinaImportCalcCommandRepositoryAdapter implements ChinaImportCalcCommandRepositoryPort {
    
    private final ChinaImportCalcJpaRepository jpaRepository;
    private final ChinaImportCalcEntityMapper mapper;
    
    @Override
    public ChinaImportCalc saveChinaImportCalc(String userId, String productSourcingId, ChinaImportCalc calc) {
        calc.setProductSourcingId(productSourcingId);
        calc.setUserId(userId);
        
        var newChinaImportCalc = jpaRepository.save(mapper.toEntity(calc));
        
        return mapper.toDomain(newChinaImportCalc);
    }
    
    @Override
    public ChinaImportCalc updateChinaImportCalc(ChinaImportCalc calc) {
        var chinaImportCalcEntity = jpaRepository.findByUserIdAndProductSourcingId(Long.valueOf(calc.getUserId())
                        , Long.valueOf(calc.getProductSourcingId()))
                .orElseThrow(CHINA_IMPORT_CALC_NOT_FOUND::exception);
        
        // 수정
        chinaImportCalcEntity.prepareUpdate()
                // inputs
                .fxRateKrwPerCny(calc.getFxRateKrwPerCny())
                .forwardingFeeRate(calc.getForwardingFeeRate())
                .unitCostCny(calc.getUnitCostCny())
                .qty(calc.getQty())
                
                .sizeWCm(calc.getSizeWCm())
                .sizeDCm(calc.getSizeDCm())
                .sizeHCm(calc.getSizeHCm())
                
                .inspectionPackPerUnitKrw(calc.getInspectionPackPerUnitKrw())
                .barcodePerUnitKrw(calc.getBarcodePerUnitKrw())
                .boxPalletTotalKrw(calc.getBoxPalletTotalKrw())
                .cbmRateKrw(calc.getCbmRateKrw())
                
                .customsBrokerKrw(calc.getCustomsBrokerKrw())
                .containerWorkKrw(calc.getContainerWorkKrw())
                .blIssueKrw(calc.getBlIssueKrw())
                .ftaIssueKrw(calc.getFtaIssueKrw())
                
                .domesticShippingKrw(calc.getDomesticShippingKrw())
                .milkRunKrw(calc.getMilkRunKrw())
                
                // computed snapshot
                .inlandShippingCny(calc.getInlandShippingCny())
                .inspectionPackTotalKrw(calc.getInspectionPackTotalKrw())
                .barcodeTotalKrw(calc.getBarcodeTotalKrw())
                .productPurchaseKrw(calc.getProductPurchaseKrw())
                .cbmFeeKrw(calc.getCbmFeeKrw())
                .customsDoneTotalKrw(calc.getCustomsDoneTotalKrw())
                .vatKrw(calc.getVatKrw())
                .totalKrw(calc.getTotalKrw())
                .unitCostKrw(calc.getUnitCostKrw())
                .multiple(calc.getMultiple())
                .estimatedBoxCount(calc.getEstimatedBoxCount())
                .update();
        
        var saved = jpaRepository.save(chinaImportCalcEntity);
        
        return mapper.toDomain(saved);
    }
}
