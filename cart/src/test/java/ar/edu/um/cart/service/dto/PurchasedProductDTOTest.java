package ar.edu.um.cart.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.cart.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchasedProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasedProductDTO.class);
        PurchasedProductDTO purchasedProductDTO1 = new PurchasedProductDTO();
        purchasedProductDTO1.setId(1L);
        PurchasedProductDTO purchasedProductDTO2 = new PurchasedProductDTO();
        assertThat(purchasedProductDTO1).isNotEqualTo(purchasedProductDTO2);
        purchasedProductDTO2.setId(purchasedProductDTO1.getId());
        assertThat(purchasedProductDTO1).isEqualTo(purchasedProductDTO2);
        purchasedProductDTO2.setId(2L);
        assertThat(purchasedProductDTO1).isNotEqualTo(purchasedProductDTO2);
        purchasedProductDTO1.setId(null);
        assertThat(purchasedProductDTO1).isNotEqualTo(purchasedProductDTO2);
    }
}
