package ar.edu.um.products.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.products.IntegrationTest;
import ar.edu.um.products.domain.Distributor;
import ar.edu.um.products.repository.DistributorRepository;
import ar.edu.um.products.service.dto.DistributorDTO;
import ar.edu.um.products.service.mapper.DistributorMapper;
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
 * Integration tests for the {@link DistributorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistributorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/distributors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private DistributorMapper distributorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistributorMockMvc;

    private Distributor distributor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distributor createEntity(EntityManager em) {
        Distributor distributor = new Distributor().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).isEnabled(DEFAULT_IS_ENABLED);
        return distributor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distributor createUpdatedEntity(EntityManager em) {
        Distributor distributor = new Distributor().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).isEnabled(UPDATED_IS_ENABLED);
        return distributor;
    }

    @BeforeEach
    public void initTest() {
        distributor = createEntity(em);
    }

    @Test
    @Transactional
    void createDistributor() throws Exception {
        int databaseSizeBeforeCreate = distributorRepository.findAll().size();
        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);
        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate + 1);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistributor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDistributor.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    void createDistributorWithExistingId() throws Exception {
        // Create the Distributor with an existing ID
        distributor.setId(1L);
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        int databaseSizeBeforeCreate = distributorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = distributorRepository.findAll().size();
        // set the field null
        distributor.setName(null);

        // Create the Distributor, which fails.
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = distributorRepository.findAll().size();
        // set the field null
        distributor.setIsEnabled(null);

        // Create the Distributor, which fails.
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        restDistributorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDistributors() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList
        restDistributorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    void getDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get the distributor
        restDistributorMockMvc
            .perform(get(ENTITY_API_URL_ID, distributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(distributor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDistributor() throws Exception {
        // Get the distributor
        restDistributorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor
        Distributor updatedDistributor = distributorRepository.findById(distributor.getId()).get();
        // Disconnect from session so that the updates on updatedDistributor are not directly saved in db
        em.detach(updatedDistributor);
        updatedDistributor.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).isEnabled(UPDATED_IS_ENABLED);
        DistributorDTO distributorDTO = distributorMapper.toDto(updatedDistributor);

        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, distributorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDistributor.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    void putNonExistingDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, distributorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(distributorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistributorWithPatch() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor using partial update
        Distributor partialUpdatedDistributor = new Distributor();
        partialUpdatedDistributor.setId(distributor.getId());

        partialUpdatedDistributor.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistributor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistributor))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDistributor.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    void fullUpdateDistributorWithPatch() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor using partial update
        Distributor partialUpdatedDistributor = new Distributor();
        partialUpdatedDistributor.setId(distributor.getId());

        partialUpdatedDistributor.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).isEnabled(UPDATED_IS_ENABLED);

        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistributor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistributor))
            )
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistributor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDistributor.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    void patchNonExistingDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, distributorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();
        distributor.setId(count.incrementAndGet());

        // Create the Distributor
        DistributorDTO distributorDTO = distributorMapper.toDto(distributor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistributorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(distributorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        int databaseSizeBeforeDelete = distributorRepository.findAll().size();

        // Delete the distributor
        restDistributorMockMvc
            .perform(delete(ENTITY_API_URL_ID, distributor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
