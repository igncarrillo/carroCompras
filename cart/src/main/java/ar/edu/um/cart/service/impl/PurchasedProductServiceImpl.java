package ar.edu.um.cart.service.impl;

import ar.edu.um.cart.domain.PurchasedProduct;
import ar.edu.um.cart.repository.PurchasedProductRepository;
import ar.edu.um.cart.service.PurchasedProductService;
import ar.edu.um.cart.service.dto.PurchasedProductDTO;
import ar.edu.um.cart.service.mapper.PurchasedProductMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PurchasedProduct}.
 */
@Service
@Transactional
public class PurchasedProductServiceImpl implements PurchasedProductService {

    private final Logger log = LoggerFactory.getLogger(PurchasedProductServiceImpl.class);

    private final PurchasedProductRepository purchasedProductRepository;

    private final PurchasedProductMapper purchasedProductMapper;

    public PurchasedProductServiceImpl(
        PurchasedProductRepository purchasedProductRepository,
        PurchasedProductMapper purchasedProductMapper
    ) {
        this.purchasedProductRepository = purchasedProductRepository;
        this.purchasedProductMapper = purchasedProductMapper;
    }

    @Override
    public PurchasedProductDTO save(PurchasedProductDTO purchasedProductDTO) {
        log.debug("Request to save PurchasedProduct : {}", purchasedProductDTO);
        PurchasedProduct purchasedProduct = purchasedProductMapper.toEntity(purchasedProductDTO);
        purchasedProduct = purchasedProductRepository.save(purchasedProduct);
        return purchasedProductMapper.toDto(purchasedProduct);
    }

    @Override
    public Optional<PurchasedProductDTO> partialUpdate(PurchasedProductDTO purchasedProductDTO) {
        log.debug("Request to partially update PurchasedProduct : {}", purchasedProductDTO);

        return purchasedProductRepository
            .findById(purchasedProductDTO.getId())
            .map(existingPurchasedProduct -> {
                purchasedProductMapper.partialUpdate(existingPurchasedProduct, purchasedProductDTO);

                return existingPurchasedProduct;
            })
            .map(purchasedProductRepository::save)
            .map(purchasedProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchasedProductDTO> findAll() {
        log.debug("Request to get all PurchasedProducts");
        return purchasedProductRepository
            .findAll()
            .stream()
            .map(purchasedProductMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchasedProductDTO> findOne(Long id) {
        log.debug("Request to get PurchasedProduct : {}", id);
        return purchasedProductRepository.findById(id).map(purchasedProductMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchasedProduct : {}", id);
        purchasedProductRepository.deleteById(id);
    }
}
