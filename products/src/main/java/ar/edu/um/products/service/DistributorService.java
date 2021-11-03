package ar.edu.um.products.service;

import ar.edu.um.products.service.dto.DistributorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ar.edu.um.products.domain.Distributor}.
 */
public interface DistributorService {
    /**
     * Save a distributor.
     *
     * @param distributorDTO the entity to save.
     * @return the persisted entity.
     */
    DistributorDTO save(DistributorDTO distributorDTO);

    /**
     * Partially updates a distributor.
     *
     * @param distributorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistributorDTO> partialUpdate(DistributorDTO distributorDTO);

    /**
     * Get all the distributors.
     *
     * @return the list of entities.
     */
    List<DistributorDTO> findAll();

    /**
     * Get the "id" distributor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistributorDTO> findOne(Long id);

    /**
     * Delete the "id" distributor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
