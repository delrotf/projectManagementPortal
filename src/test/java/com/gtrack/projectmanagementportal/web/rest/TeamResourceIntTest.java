package com.gtrack.projectmanagementportal.web.rest;

import com.gtrack.projectmanagementportal.ProjectManagementPortalApp;

import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import com.gtrack.projectmanagementportal.repository.TeamRepository;
import com.gtrack.projectmanagementportal.service.TeamService;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import com.gtrack.projectmanagementportal.service.mapper.TeamMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.gtrack.projectmanagementportal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamResource REST controller.
 *
 * @see TeamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectManagementPortalApp.class)
public class TeamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_PROCESS_ORDER = false;
    private static final Boolean UPDATED_PROCESS_ORDER = true;

    private static final Boolean DEFAULT_PROCESS_EXTERNAL_TASK = false;
    private static final Boolean UPDATED_PROCESS_EXTERNAL_TASK = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamMockMvc;

    private Team team;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamResource teamResource = new TeamResource(teamService);
        this.restTeamMockMvc = MockMvcBuilders.standaloneSetup(teamResource)
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
    public static Team createEntity(EntityManager em) {
        Team team = new Team()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .processOrder(DEFAULT_PROCESS_ORDER)
            .processExternalTask(DEFAULT_PROCESS_EXTERNAL_TASK)
            .active(DEFAULT_ACTIVE)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        UserInfo teamHead = UserInfoResourceIntTest.createEntity(em);
        em.persist(teamHead);
        em.flush();
        team.setTeamHead(teamHead);
        return team;
    }

    @Before
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTeam.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testTeam.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testTeam.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testTeam.isProcessOrder()).isEqualTo(DEFAULT_PROCESS_ORDER);
        assertThat(testTeam.isProcessExternalTask()).isEqualTo(DEFAULT_PROCESS_EXTERNAL_TASK);
        assertThat(testTeam.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTeam.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team with an existing ID
        team.setId(1L);
        TeamDTO teamDTO = teamMapper.toDto(team);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setName(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setActive(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamRepository.findAll().size();
        // set the field null
        team.setCreatedDate(null);

        // Create the Team, which fails.
        TeamDTO teamDTO = teamMapper.toDto(team);

        restTeamMockMvc.perform(post("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc.perform(get("/api/teams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].processOrder").value(hasItem(DEFAULT_PROCESS_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].processExternalTask").value(hasItem(DEFAULT_PROCESS_EXTERNAL_TASK.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.processOrder").value(DEFAULT_PROCESS_ORDER.booleanValue()))
            .andExpect(jsonPath("$.processExternalTask").value(DEFAULT_PROCESS_EXTERNAL_TASK.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findOne(team.getId());
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .imageUrl(UPDATED_IMAGE_URL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .processOrder(UPDATED_PROCESS_ORDER)
            .processExternalTask(UPDATED_PROCESS_EXTERNAL_TASK)
            .active(UPDATED_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);
        TeamDTO teamDTO = teamMapper.toDto(updatedTeam);

        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTeam.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testTeam.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testTeam.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testTeam.isProcessOrder()).isEqualTo(UPDATED_PROCESS_ORDER);
        assertThat(testTeam.isProcessExternalTask()).isEqualTo(UPDATED_PROCESS_EXTERNAL_TASK);
        assertThat(testTeam.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTeam.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamMockMvc.perform(put("/api/teams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);
        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Get the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", team.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = new Team();
        team1.setId(1L);
        Team team2 = new Team();
        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);
        team2.setId(2L);
        assertThat(team1).isNotEqualTo(team2);
        team1.setId(null);
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamDTO.class);
        TeamDTO teamDTO1 = new TeamDTO();
        teamDTO1.setId(1L);
        TeamDTO teamDTO2 = new TeamDTO();
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
        teamDTO2.setId(teamDTO1.getId());
        assertThat(teamDTO1).isEqualTo(teamDTO2);
        teamDTO2.setId(2L);
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
        teamDTO1.setId(null);
        assertThat(teamDTO1).isNotEqualTo(teamDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teamMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teamMapper.fromId(null)).isNull();
    }
}
