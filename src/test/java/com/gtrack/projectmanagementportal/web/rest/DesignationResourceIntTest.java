package com.gtrack.projectmanagementportal.web.rest;

import com.gtrack.projectmanagementportal.ProjectManagementPortalApp;

import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.repository.DesignationRepository;
import com.gtrack.projectmanagementportal.service.DesignationService;
import com.gtrack.projectmanagementportal.service.dto.DesignationDTO;
import com.gtrack.projectmanagementportal.service.mapper.DesignationMapper;
import com.gtrack.projectmanagementportal.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.gtrack.projectmanagementportal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DesignationResource REST controller.
 *
 * @see DesignationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectManagementPortalApp.class)
public class DesignationResourceIntTest {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationMapper designationMapper;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignationMockMvc;

    private Designation designation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignationResource designationResource = new DesignationResource(designationService);
        this.restDesignationMockMvc = MockMvcBuilders.standaloneSetup(designationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Designation createEntity(EntityManager em) {
        Designation designation = new Designation()
            .designation(DEFAULT_DESIGNATION);
        return designation;
    }

    @Before
    public void initTest() {
        designation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createDesignationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation with an existing ID
        designation.setId(1L);
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }

    @Test
    @Transactional
    public void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findOne(designation.getId());
        // Disconnect from session so that the updates on updatedDesignation are not directly saved in db
        em.detach(updatedDesignation);
        updatedDesignation
            .designation(UPDATED_DESIGNATION);
        DesignationDTO designationDTO = designationMapper.toDto(updatedDesignation);

        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);
        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Get the designation
        restDesignationMockMvc.perform(delete("/api/designations/{id}", designation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designation.class);
        Designation designation1 = new Designation();
        designation1.setId(1L);
        Designation designation2 = new Designation();
        designation2.setId(designation1.getId());
        assertThat(designation1).isEqualTo(designation2);
        designation2.setId(2L);
        assertThat(designation1).isNotEqualTo(designation2);
        designation1.setId(null);
        assertThat(designation1).isNotEqualTo(designation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationDTO.class);
        DesignationDTO designationDTO1 = new DesignationDTO();
        designationDTO1.setId(1L);
        DesignationDTO designationDTO2 = new DesignationDTO();
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
        designationDTO2.setId(designationDTO1.getId());
        assertThat(designationDTO1).isEqualTo(designationDTO2);
        designationDTO2.setId(2L);
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
        designationDTO1.setId(null);
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationMapper.fromId(null)).isNull();
    }
}
