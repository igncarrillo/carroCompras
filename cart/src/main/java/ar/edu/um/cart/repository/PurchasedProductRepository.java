package ar.edu.um.cart.repository;

import ar.edu.um.cart.domain.PurchasedProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PurchasedProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Long> {}
