package club.product.importing.rdb.mapper;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.rdb.entity.ChinaImportCalcEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ChinaImportCalcEntityMapper {
    
    @Mapping(target = "productSourcingId", expression = "java(String.valueOf(entity.productSourcingId))")
    @Mapping(target = "userId", expression = "java(String.valueOf(entity.userId))")
    ChinaImportCalc toDomain(ChinaImportCalcEntity entity);
    
    @Mapping(target = "productSourcingId", expression = "java(Long.parseLong(domain.getProductSourcingId()))")
    @Mapping(target = "userId", expression = "java(Long.parseLong(domain.getUserId()))")
    ChinaImportCalcEntity toEntity(ChinaImportCalc domain);
    
    default Optional<ChinaImportCalc> toOptionalDomain(ChinaImportCalcEntity entity){
        return Optional.ofNullable(toDomain(entity));
    }
}