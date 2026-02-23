package club.product.importing.web.mapper;

import club.product.importing.domain.ChinaImportCalc;
import club.product.importing.web.dto.ChinaImportCalcCommandDto.UpsertCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChinaImportCalcDtoMapper {
    
    // Command DTO + (AuthUser, PathVariable) → Domain
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "productSourcingId", source = "productSourcingId")
    ChinaImportCalc toDomain(String userId, String productSourcingId, UpsertCommand command);
}
