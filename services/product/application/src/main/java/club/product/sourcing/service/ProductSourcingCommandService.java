package club.product.sourcing.service;

import club.product.sourcing.domain.ProductSourcing;
import club.product.sourcing.port.ProductSourcingCommandRepositoryPort;
import club.product.sourcing.usecase.ProductSourcingBulkSaveCase;
import club.product.sourcing.usecase.ProductSourcingCreateUseCase;
import club.product.sourcing.usecase.ProductSourcingDeleteUseCase;
import club.product.sourcing.usecase.ProductSourcingUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static club.product.sourcing.exception.ProductErrorCode.INVALID_BULK_REQUEST;
import static club.product.sourcing.exception.ProductErrorCode.SOURCING_FORBIDDEN;
import static club.product.sourcing.exception.ProductErrorCode.SOURCING_NOT_FOUND;
import static club.product.sourcing.exception.ProductErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductSourcingCommandService implements ProductSourcingBulkSaveCase, ProductSourcingCreateUseCase, ProductSourcingUpdateUseCase, ProductSourcingDeleteUseCase {
    
    private final ProductSourcingCommandRepositoryPort repository;
    
    @Override
    public List<ProductSourcing> bulkSave(String userId, List<ProductSourcing> upserts, List<String> deleteIds) {
        if (userId == null || userId.isBlank()) throw USER_NOT_FOUND.exception();
        
        List<ProductSourcing> safeUpserts = (upserts == null) ? List.of() : upserts;
        List<String> safeDeleteIds = (deleteIds == null) ? List.of() : deleteIds;
        
        if (safeUpserts.isEmpty() && safeDeleteIds.isEmpty()) {
            throw SOURCING_NOT_FOUND.exception(); // 또는 INVALID_REQUEST 성격 코드가 더 적절
        }
        
        // upserts와 deletes가 충돌하면 즉시 실패
        var upsertIds = safeUpserts.stream()
                .map(ProductSourcing::getId)
                .filter(java.util.Objects::nonNull)
                .collect(java.util.stream.Collectors.toSet());
        
        boolean hasConflict = safeDeleteIds.stream().anyMatch(upsertIds::contains);
        
        if (hasConflict) {
            throw INVALID_BULK_REQUEST.exception(); // 여기엔 새 코드가 더 맞음: INVALID_BULK_REQUEST 등
        }
        
        return repository.transaction(() -> {
            // 1) 삭제 (소유자 검증 필수)
            for (String id : safeDeleteIds) {
                delete(userId, id);
            }
            
            // 2-1) 생성
            var bulkProductSourcingList = create(userId, safeUpserts.stream().filter(s -> s.getId() == null).toList());
            
            // 2-2) 수정 (소유자 검증 필수)
            safeUpserts.stream().filter(s -> s.getId() != null) .forEach((s) -> bulkProductSourcingList.add(update(userId, s.getId(), s)));
            
            return bulkProductSourcingList;
        });
    }

    
    @Override
    public List<ProductSourcing> create(String userId, List<ProductSourcing> sourcingList) {
        if (userId.isEmpty()) throw USER_NOT_FOUND.exception();
        if (sourcingList.isEmpty()) throw SOURCING_NOT_FOUND.exception();
        
        sourcingList.forEach(s -> {
            if (s == null) throw SOURCING_NOT_FOUND.exception();
            s.setUserId(userId);
            s.setId(null);
        });
        
        return repository.saveAll(sourcingList);
    }
    
    @Override
    public ProductSourcing update(String userId, String id, ProductSourcing sourcing) {
        if (userId.isEmpty()) throw USER_NOT_FOUND.exception();
        
        ProductSourcing existing = repository.findById(id).orElseThrow(SOURCING_NOT_FOUND::exception);
        
        // 소유자 검증
        if (!existing.getUserId().equals(userId)) {
            throw SOURCING_FORBIDDEN.exception();
        }
        
        return repository.update(id, sourcing);
    }
    
    @Override
    public void delete(String userId, String id) {
        if (userId.isEmpty()) throw USER_NOT_FOUND.exception();
        if (id.isEmpty()) throw SOURCING_NOT_FOUND.exception();
        
        ProductSourcing existing = repository.findById(id).orElseThrow(SOURCING_NOT_FOUND::exception);
        
        if (!existing.getUserId().equals(userId)) {
            throw SOURCING_FORBIDDEN.exception();
        }
        
        repository.deleteById(id);
    }
}
