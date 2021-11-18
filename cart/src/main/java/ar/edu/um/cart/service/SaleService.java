package ar.edu.um.cart.service;

import ar.edu.um.cart.service.dto.SaleDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.edu.um.cart.domain.Sale}.
 */
public interface SaleService {
    /**
     * Save a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    SaleDTO save(SaleDTO saleDTO);

    /**
     * Partially updates a sale.
     *
     * @param saleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaleDTO> partialUpdate(SaleDTO saleDTO);

    /**
     * Get all the sales.
     *
     * @return the list of entities.
     */
    List<SaleDTO> findAll();

    /**
     * Get the "id" sale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaleDTO> findOne(Long id);

    /**
     * Delete the "id" sale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
