package ar.edu.um.products.service.mapper;

import ar.edu.um.products.domain.Distributor;
import ar.edu.um.products.service.dto.DistributorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-03T01:41:41-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class DistributorMapperImpl implements DistributorMapper {

    @Override
    public Distributor toEntity(DistributorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Distributor distributor = new Distributor();

        distributor.setId( dto.getId() );
        distributor.setName( dto.getName() );
        distributor.setDescription( dto.getDescription() );
        distributor.setIsEnabled( dto.getIsEnabled() );

        return distributor;
    }

    @Override
    public DistributorDTO toDto(Distributor entity) {
        if ( entity == null ) {
            return null;
        }

        DistributorDTO distributorDTO = new DistributorDTO();

        distributorDTO.setId( entity.getId() );
        distributorDTO.setName( entity.getName() );
        distributorDTO.setDescription( entity.getDescription() );
        distributorDTO.setIsEnabled( entity.getIsEnabled() );

        return distributorDTO;
    }

    @Override
    public List<Distributor> toEntity(List<DistributorDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Distributor> list = new ArrayList<Distributor>( dtoList.size() );
        for ( DistributorDTO distributorDTO : dtoList ) {
            list.add( toEntity( distributorDTO ) );
        }

        return list;
    }

    @Override
    public List<DistributorDTO> toDto(List<Distributor> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DistributorDTO> list = new ArrayList<DistributorDTO>( entityList.size() );
        for ( Distributor distributor : entityList ) {
            list.add( toDto( distributor ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Distributor entity, DistributorDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getIsEnabled() != null ) {
            entity.setIsEnabled( dto.getIsEnabled() );
        }
    }

    @Override
    public DistributorDTO toDtoId(Distributor distributor) {
        if ( distributor == null ) {
            return null;
        }

        DistributorDTO distributorDTO = new DistributorDTO();

        distributorDTO.setId( distributor.getId() );

        return distributorDTO;
    }
}
