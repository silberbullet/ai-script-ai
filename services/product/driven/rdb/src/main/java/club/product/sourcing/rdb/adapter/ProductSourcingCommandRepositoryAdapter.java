package club.product.sourcing.rdb.adapter;

import club.product.sourcing.domain.ProductSourcing;
import club.product.sourcing.port.ProductSourcingCommandRepositoryPort;
import club.product.sourcing.rdb.mapper.ProductSourcingEntityMapper;
import club.product.sourcing.rdb.repository.ProductSourcingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static club.product.sourcing.exception.ProductErrorCode.SOURCING_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ProductSourcingCommandRepositoryAdapter implements ProductSourcingCommandRepositoryPort {
    
    private final ProductSourcingEntityMapper mapper;
    private final ProductSourcingJpaRepository productSourcingJpaRepository;
    
    @Override
    public <T> T transaction(Supplier<T> supplier) {
        return supplier.get();
    }
    
    @Override
    public List<ProductSourcing> saveAll(List<ProductSourcing> sourcingList) {
        var newEntityList = sourcingList.stream().map(mapper::toEntity).toList();
        
        return productSourcingJpaRepository.saveAll(newEntityList).stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public ProductSourcing update(String id, ProductSourcing productSourcing) {
        var existsSourcing = productSourcingJpaRepository.findById(Long.valueOf(id))
                .orElseThrow(SOURCING_NOT_FOUND::exception);
        
        existsSourcing.prepareUpdate()
                .keyword(productSourcing.getKeyword())
                .referenceProduct(productSourcing.getReferenceProduct())
                .sourceUrl(productSourcing.getSourceUrl())
                .imageUrl(productSourcing.getImageUrl())
                .name(productSourcing.getName())
                .costCny(productSourcing.getCostCny())
                .costKrw(productSourcing.getCostKrw())
                .coupangCategory(productSourcing.getCoupangCategory())
                .wingLogisticsCategory(productSourcing.getWingLogisticsCategory())
                .salePriceKrw(productSourcing.getSalePriceKrw())
                .feeRatePercent(productSourcing.getFeeRatePercent())
                .feeAmountKrw(productSourcing.getFeeAmountKrw())
                .vatKrw(productSourcing.getVatKrw())
                .grossMarginKrw(productSourcing.getGrossMarginKrw())
                .grossMarginRatePercent(productSourcing.getGrossMarginRatePercent())
                .minAdRoiPercent(productSourcing.getMinAdRoiPercent())
                .update();
        
        var saved = productSourcingJpaRepository.save(existsSourcing);
        
        return mapper.toDomain(saved);
    }
    
    @Override
    public void deleteById(String id) {
        productSourcingJpaRepository.deleteById(Long.valueOf(id));
    }
    
    @Override
    public Optional<ProductSourcing> findById(String id) {
        return productSourcingJpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain);
    }
}
