package ar.edu.um.products.service.mapper;

import ar.edu.um.products.domain.Product;
import ar.edu.um.products.service.dto.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-03T01:41:41-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private DistributorMapper distributorMapper;

    @Override
    public Product toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( dto.getId() );
        product.setName( dto.getName() );
        product.setDescription( dto.getDescription() );
        product.setPrice( dto.getPrice() );
        product.setSoldQuantity( dto.getSoldQuantity() );
        product.setIsEnabled( dto.getIsEnabled() );
        product.distributor( distributorMapper.toEntity( dto.getDistributor() ) );

        return product;
    }

    @Override
    public List<Product> toEntity(List<ProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtoList.size() );
        for ( ProductDTO productDTO : dtoList ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public List<ProductDTO> toDto(List<Product> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entityList.size() );
        for ( Product product : entityList ) {
            list.add( toDto( product ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Product entity, ProductDTO dto) {
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
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getSoldQuantity() != null ) {
            entity.setSoldQuantity( dto.getSoldQuantity() );
        }
        if ( dto.getIsEnabled() != null ) {
            entity.setIsEnabled( dto.getIsEnabled() );
        }
        if ( dto.getDistributor() != null ) {
            entity.distributor( distributorMapper.toEntity( dto.getDistributor() ) );
        }
    }

    @Override
    public ProductDTO toDto(Product s) {
        if ( s == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setDistributor( distributorMapper.toDtoId( s.getDistributor() ) );
        productDTO.setId( s.getId() );
        productDTO.setName( s.getName() );
        productDTO.setDescription( s.getDescription() );
        productDTO.setPrice( s.getPrice() );
        productDTO.setSoldQuantity( s.getSoldQuantity() );
        productDTO.setIsEnabled( s.getIsEnabled() );

        return productDTO;
    }
}
