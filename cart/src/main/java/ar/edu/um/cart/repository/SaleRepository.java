package ar.edu.um.cart.repository;

import ar.edu.um.cart.domain.Sale;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select sale from Sale sale where sale.user.login = ?#{principal.username}")
    List<Sale> findByUserIsCurrentUser();
}
