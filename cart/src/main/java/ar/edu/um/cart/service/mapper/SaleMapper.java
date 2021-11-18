package ar.edu.um.cart.service.mapper;

import ar.edu.um.cart.domain.Sale;
import ar.edu.um.cart.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    SaleDTO toDto(Sale s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaleDTO toDtoId(Sale sale);
}
