package ar.edu.um.cart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.cart.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchasedProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasedProduct.class);
        PurchasedProduct purchasedProduct1 = new PurchasedProduct();
        purchasedProduct1.setId(1L);
        PurchasedProduct purchasedProduct2 = new PurchasedProduct();
        purchasedProduct2.setId(purchasedProduct1.getId());
        assertThat(purchasedProduct1).isEqualTo(purchasedProduct2);
        purchasedProduct2.setId(2L);
        assertThat(purchasedProduct1).isNotEqualTo(purchasedProduct2);
        purchasedProduct1.setId(null);
        assertThat(purchasedProduct1).isNotEqualTo(purchasedProduct2);
    }
}
