package club.product.sourcing.web;

import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import club.product.sourcing.usecase.ProductSourcingBulkSaveCase;
import club.product.sourcing.web.dto.ProductSourcingCommandDto.ProductSourcingBulkSaveCommand;
import club.product.sourcing.web.dto.ProductSourcingCommandDto.ProductSourcingCommandResponse;
import club.product.sourcing.web.mapper.ProductSourcingDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ProductSourcing", description = "상품 소싱 API")
@RestController
@RequestMapping("/product/sourcing")
@RequiredArgsConstructor
public class ProductSourcingCommandApi {
    
    private final ProductSourcingDtoMapper mapper;
    private final ProductSourcingBulkSaveCase productSourcingBulkSaveCase;
    
    @PostMapping("/bulk")
    public ProductSourcingCommandResponse bulkSave(
            @AuthUser AuthorizedUser user,
            @RequestBody ProductSourcingBulkSaveCommand req) {
        List<String> deleteIds = (req.deleteIds() == null) ? List.of() : req.deleteIds();
        
        var upserts = req.upserts().stream()
                .map(mapper::toDomain)
                .toList();
        
        String userId = String.valueOf(user.userId());
        
        var result = productSourcingBulkSaveCase.bulkSave(userId, upserts, deleteIds);
        
        return ProductSourcingCommandResponse.builder()
                .productSourcingList(result)
                .build();
    }
}
