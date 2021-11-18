package ar.edu.um.cart.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchasedProductMapperTest {

    private PurchasedProductMapper purchasedProductMapper;

    @BeforeEach
    public void setUp() {
        purchasedProductMapper = new PurchasedProductMapperImpl();
    }
}
