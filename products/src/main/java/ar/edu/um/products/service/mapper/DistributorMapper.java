package ar.edu.um.products.service.mapper;

import ar.edu.um.products.domain.Distributor;
import ar.edu.um.products.service.dto.DistributorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Distributor} and its DTO {@link DistributorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistributorMapper extends EntityMapper<DistributorDTO, Distributor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistributorDTO toDtoId(Distributor distributor);
}
