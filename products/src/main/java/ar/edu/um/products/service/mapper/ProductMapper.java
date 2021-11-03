package ar.edu.um.products.service.mapper;

import ar.edu.um.products.domain.Product;
import ar.edu.um.products.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = { DistributorMapper.class })
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "distributor", source = "distributor", qualifiedByName = "id")
    ProductDTO toDto(Product s);
}
