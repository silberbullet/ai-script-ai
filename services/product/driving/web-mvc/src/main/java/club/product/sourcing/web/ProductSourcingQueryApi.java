package club.product.sourcing.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.sourcing.usecase.ProductSourcingReadUseCase;
import club.product.sourcing.web.dto.ProductSourcingQueryDto.ProductSourcingSummaryDto;
import club.product.sourcing.web.dto.ProductSourcingQueryDto.ProductSourcingDetailResponse;
import club.product.sourcing.web.dto.ProductSourcingQueryDto.ProductSourcingSummaryResponse;
import club.product.sourcing.web.mapper.ProductSourcingDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ProductSourcing", description = "상품 소싱 API")
@RestController
@RequestMapping("/product/sourcing")
@RequiredArgsConstructor
public class ProductSourcingQueryApi {
    
    private final ProductSourcingDtoMapper mapper;
    private final ProductSourcingReadUseCase readUseCase;
    
    @GetMapping
    public ProductSourcingSummaryResponse getProductSourcingSummary(@AuthUser AuthorizedUser user, ProductSourcingSummaryDto searchCondition) {
        var result = readUseCase.getProductSourcingList(user.userId(), mapper.toSearchCondition(searchCondition));
        
        return ProductSourcingSummaryResponse.builder()
                .productSourcingList(result)
                .build();
    }
    
    @GetMapping("/{id}")
    public ProductSourcingDetailResponse getProductSourcingDetail(@AuthUser AuthorizedUser user, @PathVariable String id) {
        var result = readUseCase.getProductSourcing(user.userId(), id);
        
        return ProductSourcingDetailResponse.builder()
                .productSourcing(result)
                .build();
    }
}
