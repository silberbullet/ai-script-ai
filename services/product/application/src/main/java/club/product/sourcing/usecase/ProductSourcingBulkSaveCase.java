package club.product.sourcing.usecase;

import club.product.sourcing.domain.ProductSourcing;

import java.util.List;

public interface ProductSourcingBulkSaveCase {
    
    /**
     * 하나의 저장 동작으로 생성/수정/삭제를 처리한다.
     *
     * @param userId    소유자
     * @param upserts   생성 또는 수정 대상 (id null = create, id not null = update)
     * @param deleteIds 삭제 대상 id 목록
     */
    List<ProductSourcing> bulkSave(String userId, List<ProductSourcing> upserts, List<String> deleteIds);
}
