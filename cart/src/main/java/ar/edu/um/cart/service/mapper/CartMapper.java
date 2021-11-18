package ar.edu.um.cart.service.mapper;

import ar.edu.um.cart.domain.Cart;
import ar.edu.um.cart.service.dto.CartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    CartDTO toDto(Cart s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoId(Cart cart);
}
