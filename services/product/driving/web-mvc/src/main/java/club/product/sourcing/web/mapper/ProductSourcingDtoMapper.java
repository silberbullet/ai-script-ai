package club.product.sourcing.web.mapper;

import club.product.sourcing.domain.ProductSourcing;
import club.product.sourcing.usecase.query.ProductSourcingSearchCondition;
import club.product.sourcing.web.dto.ProductSourcingCommandDto.ProductSourcingUpsertCommand;
import club.product.sourcing.web.dto.ProductSourcingQueryDto.ProductSourcingSummaryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSourcingDtoMapper {
    
    ProductSourcing toDomain(ProductSourcingUpsertCommand dto);
    ProductSourcingSearchCondition toSearchCondition(ProductSourcingSummaryDto dto);
}
