package club.product.sourcing.rdb.mapper;

import club.product.sourcing.rdb.entity.ProductSourcingEntity;
import club.product.sourcing.domain.ProductSourcing;
import club.product.sourcing.rdb.projection.ProductSourcingProjections.ProductSourcingSummaryProjection;
import club.product.sourcing.rdb.projection.ProductSourcingProjections.ProductSourcingDetailProjection;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingSummary;
import club.product.sourcing.readmodel.ProductSourcingReadModel.ProductSourcingDetail;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProductSourcingEntityMapper {
    
    ProductSourcingEntity toEntity(ProductSourcing domain);
    
    ProductSourcing toDomain(ProductSourcingEntity entity);
    
    ProductSourcingDetail toDomainDetail(ProductSourcingDetailProjection entity);
    
    ProductSourcingSummary toDomainSummary(ProductSourcingSummaryProjection entity);
    
    default Optional<ProductSourcingDetail> toOptionalProductSourcingDetail(ProductSourcingDetailProjection entity) {
        return Optional.ofNullable(toDomainDetail(entity));
    }
}
