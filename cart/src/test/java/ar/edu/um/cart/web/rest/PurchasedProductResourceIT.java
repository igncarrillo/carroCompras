package ar.edu.um.cart.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.cart.IntegrationTest;
import ar.edu.um.cart.domain.Cart;
import ar.edu.um.cart.domain.PurchasedProduct;
import ar.edu.um.cart.repository.PurchasedProductRepository;
import ar.edu.um.cart.service.dto.PurchasedProductDTO;
import ar.edu.um.cart.service.mapper.PurchasedProductMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PurchasedProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchasedProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Long DEFAULT_ID_PRODUCT = 1L;
    private static final Long UPDATED_ID_PRODUCT = 2L;

    private static final String ENTITY_API_URL = "/api/purchased-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchasedProductRepository purchasedProductRepository;

    @Autowired
    private PurchasedProductMapper purchasedProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchasedProductMockMvc;

    private PurchasedProduct purchasedProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasedProduct createEntity(EntityManager em) {
        PurchasedProduct purchasedProduct = new PurchasedProduct()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .idProduct(DEFAULT_ID_PRODUCT);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        purchasedProduct.setCart(cart);
        return purchasedProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasedProduct createUpdatedEntity(EntityManager em) {
        PurchasedProduct purchasedProduct = new PurchasedProduct()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .idProduct(UPDATED_ID_PRODUCT);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        purchasedProduct.setCart(cart);
        return purchasedProduct;
    }

    @BeforeEach
    public void initTest() {
        purchasedProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchasedProduct() throws Exception {
        int databaseSizeBeforeCreate = purchasedProductRepository.findAll().size();
        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);
        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasedProduct testPurchasedProduct = purchasedProductList.get(purchasedProductList.size() - 1);
        assertThat(testPurchasedProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPurchasedProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPurchasedProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPurchasedProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchasedProduct.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
    }

    @Test
    @Transactional
    void createPurchasedProductWithExistingId() throws Exception {
        // Create the PurchasedProduct with an existing ID
        purchasedProduct.setId(1L);
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        int databaseSizeBeforeCreate = purchasedProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasedProductRepository.findAll().size();
        // set the field null
        purchasedProduct.setName(null);

        // Create the PurchasedProduct, which fails.
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasedProductRepository.findAll().size();
        // set the field null
        purchasedProduct.setPrice(null);

        // Create the PurchasedProduct, which fails.
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasedProductRepository.findAll().size();
        // set the field null
        purchasedProduct.setQuantity(null);

        // Create the PurchasedProduct, which fails.
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdProductIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasedProductRepository.findAll().size();
        // set the field null
        purchasedProduct.setIdProduct(null);

        // Create the PurchasedProduct, which fails.
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        restPurchasedProductMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPurchasedProducts() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        // Get all the purchasedProductList
        restPurchasedProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasedProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT.intValue())));
    }

    @Test
    @Transactional
    void getPurchasedProduct() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        // Get the purchasedProduct
        restPurchasedProductMockMvc
            .perform(get(ENTITY_API_URL_ID, purchasedProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchasedProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPurchasedProduct() throws Exception {
        // Get the purchasedProduct
        restPurchasedProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPurchasedProduct() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();

        // Update the purchasedProduct
        PurchasedProduct updatedPurchasedProduct = purchasedProductRepository.findById(purchasedProduct.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasedProduct are not directly saved in db
        em.detach(updatedPurchasedProduct);
        updatedPurchasedProduct
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .idProduct(UPDATED_ID_PRODUCT);
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(updatedPurchasedProduct);

        restPurchasedProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchasedProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
        PurchasedProduct testPurchasedProduct = purchasedProductList.get(purchasedProductList.size() - 1);
        assertThat(testPurchasedProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPurchasedProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchasedProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPurchasedProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchasedProduct.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
    }

    @Test
    @Transactional
    void putNonExistingPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchasedProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchasedProductWithPatch() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();

        // Update the purchasedProduct using partial update
        PurchasedProduct partialUpdatedPurchasedProduct = new PurchasedProduct();
        partialUpdatedPurchasedProduct.setId(purchasedProduct.getId());

        partialUpdatedPurchasedProduct.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).idProduct(UPDATED_ID_PRODUCT);

        restPurchasedProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchasedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchasedProduct))
            )
            .andExpect(status().isOk());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
        PurchasedProduct testPurchasedProduct = purchasedProductList.get(purchasedProductList.size() - 1);
        assertThat(testPurchasedProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPurchasedProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchasedProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPurchasedProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchasedProduct.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
    }

    @Test
    @Transactional
    void fullUpdatePurchasedProductWithPatch() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();

        // Update the purchasedProduct using partial update
        PurchasedProduct partialUpdatedPurchasedProduct = new PurchasedProduct();
        partialUpdatedPurchasedProduct.setId(purchasedProduct.getId());

        partialUpdatedPurchasedProduct
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .idProduct(UPDATED_ID_PRODUCT);

        restPurchasedProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchasedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchasedProduct))
            )
            .andExpect(status().isOk());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
        PurchasedProduct testPurchasedProduct = purchasedProductList.get(purchasedProductList.size() - 1);
        assertThat(testPurchasedProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPurchasedProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchasedProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPurchasedProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchasedProduct.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
    }

    @Test
    @Transactional
    void patchNonExistingPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchasedProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchasedProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchasedProductRepository.findAll().size();
        purchasedProduct.setId(count.incrementAndGet());

        // Create the PurchasedProduct
        PurchasedProductDTO purchasedProductDTO = purchasedProductMapper.toDto(purchasedProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchasedProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchasedProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchasedProduct in the database
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchasedProduct() throws Exception {
        // Initialize the database
        purchasedProductRepository.saveAndFlush(purchasedProduct);

        int databaseSizeBeforeDelete = purchasedProductRepository.findAll().size();

        // Delete the purchasedProduct
        restPurchasedProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchasedProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchasedProduct> purchasedProductList = purchasedProductRepository.findAll();
        assertThat(purchasedProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
