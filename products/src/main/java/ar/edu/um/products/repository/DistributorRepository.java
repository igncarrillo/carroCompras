package ar.edu.um.products.repository;

import ar.edu.um.products.domain.Distributor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Distributor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {}
