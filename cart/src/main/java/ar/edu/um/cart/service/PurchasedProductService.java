package ar.edu.um.cart.service;

import ar.edu.um.cart.service.dto.PurchasedProductDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.edu.um.cart.domain.PurchasedProduct}.
 */
public interface PurchasedProductService {
    /**
     * Save a purchasedProduct.
     *
     * @param purchasedProductDTO the entity to save.
     * @return the persisted entity.
     */
    PurchasedProductDTO save(PurchasedProductDTO purchasedProductDTO);

    /**
     * Partially updates a purchasedProduct.
     *
     * @param purchasedProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurchasedProductDTO> partialUpdate(PurchasedProductDTO purchasedProductDTO);

    /**
     * Get all the purchasedProducts.
     *
     * @return the list of entities.
     */
    List<PurchasedProductDTO> findAll();

    /**
     * Get the "id" purchasedProduct.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchasedProductDTO> findOne(Long id);

    /**
     * Delete the "id" purchasedProduct.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
