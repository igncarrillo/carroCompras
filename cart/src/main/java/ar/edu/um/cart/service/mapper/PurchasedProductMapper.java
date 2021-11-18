package ar.edu.um.cart.service.mapper;

import ar.edu.um.cart.domain.PurchasedProduct;
import ar.edu.um.cart.service.dto.PurchasedProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchasedProduct} and its DTO {@link PurchasedProductDTO}.
 */
@Mapper(componentModel = "spring", uses = { CartMapper.class, SaleMapper.class })
public interface PurchasedProductMapper extends EntityMapper<PurchasedProductDTO, PurchasedProduct> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "id")
    @Mapping(target = "sale", source = "sale", qualifiedByName = "id")
    PurchasedProductDTO toDto(PurchasedProduct s);
}
