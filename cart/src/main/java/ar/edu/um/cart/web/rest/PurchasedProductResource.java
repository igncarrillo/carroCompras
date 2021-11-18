package ar.edu.um.cart.web.rest;

import ar.edu.um.cart.repository.PurchasedProductRepository;
import ar.edu.um.cart.service.PurchasedProductService;
import ar.edu.um.cart.service.dto.PurchasedProductDTO;
import ar.edu.um.cart.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.edu.um.cart.domain.PurchasedProduct}.
 */
@RestController
@RequestMapping("/api")
public class PurchasedProductResource {

    private final Logger log = LoggerFactory.getLogger(PurchasedProductResource.class);

    private static final String ENTITY_NAME = "purchasedProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchasedProductService purchasedProductService;

    private final PurchasedProductRepository purchasedProductRepository;

    public PurchasedProductResource(
        PurchasedProductService purchasedProductService,
        PurchasedProductRepository purchasedProductRepository
    ) {
        this.purchasedProductService = purchasedProductService;
        this.purchasedProductRepository = purchasedProductRepository;
    }

    /**
     * {@code POST  /purchased-products} : Create a new purchasedProduct.
     *
     * @param purchasedProductDTO the purchasedProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchasedProductDTO, or with status {@code 400 (Bad Request)} if the purchasedProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchased-products")
    public ResponseEntity<PurchasedProductDTO> createPurchasedProduct(@Valid @RequestBody PurchasedProductDTO purchasedProductDTO)
        throws URISyntaxException {
        log.debug("REST request to save PurchasedProduct : {}", purchasedProductDTO);
        if (purchasedProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchasedProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasedProductDTO result = purchasedProductService.save(purchasedProductDTO);
        return ResponseEntity
            .created(new URI("/api/purchased-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchased-products/:id} : Updates an existing purchasedProduct.
     *
     * @param id the id of the purchasedProductDTO to save.
     * @param purchasedProductDTO the purchasedProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasedProductDTO,
     * or with status {@code 400 (Bad Request)} if the purchasedProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchasedProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchased-products/{id}")
    public ResponseEntity<PurchasedProductDTO> updatePurchasedProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PurchasedProductDTO purchasedProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PurchasedProduct : {}, {}", id, purchasedProductDTO);
        if (purchasedProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchasedProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchasedProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurchasedProductDTO result = purchasedProductService.save(purchasedProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchasedProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /purchased-products/:id} : Partial updates given fields of an existing purchasedProduct, field will ignore if it is null
     *
     * @param id the id of the purchasedProductDTO to save.
     * @param purchasedProductDTO the purchasedProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchasedProductDTO,
     * or with status {@code 400 (Bad Request)} if the purchasedProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the purchasedProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the purchasedProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/purchased-products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PurchasedProductDTO> partialUpdatePurchasedProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PurchasedProductDTO purchasedProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurchasedProduct partially : {}, {}", id, purchasedProductDTO);
        if (purchasedProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchasedProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchasedProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PurchasedProductDTO> result = purchasedProductService.partialUpdate(purchasedProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchasedProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /purchased-products} : get all the purchasedProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchasedProducts in body.
     */
    @GetMapping("/purchased-products")
    public List<PurchasedProductDTO> getAllPurchasedProducts() {
        log.debug("REST request to get all PurchasedProducts");
        return purchasedProductService.findAll();
    }

    /**
     * {@code GET  /purchased-products/:id} : get the "id" purchasedProduct.
     *
     * @param id the id of the purchasedProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchasedProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchased-products/{id}")
    public ResponseEntity<PurchasedProductDTO> getPurchasedProduct(@PathVariable Long id) {
        log.debug("REST request to get PurchasedProduct : {}", id);
        Optional<PurchasedProductDTO> purchasedProductDTO = purchasedProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchasedProductDTO);
    }

    /**
     * {@code DELETE  /purchased-products/:id} : delete the "id" purchasedProduct.
     *
     * @param id the id of the purchasedProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchased-products/{id}")
    public ResponseEntity<Void> deletePurchasedProduct(@PathVariable Long id) {
        log.debug("REST request to delete PurchasedProduct : {}", id);
        purchasedProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
