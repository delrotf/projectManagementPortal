package com.gtrack.projectmanagementportal.web.rest;

import com.gtrack.projectmanagementportal.ProjectManagementPortalApp;

import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.domain.User;
import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.repository.TeamMemberRepository;
import com.gtrack.projectmanagementportal.service.TeamMemberService;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;
import com.gtrack.projectmanagementportal.service.mapper.TeamMemberMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.gtrack.projectmanagementportal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamMemberResource REST controller.
 *
 * @see TeamMemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectManagementPortalApp.class)
public class TeamMemberResourceIntTest {

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamMemberMockMvc;

    private TeamMember teamMember;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamMemberResource teamMemberResource = new TeamMemberResource(teamMemberService);
        this.restTeamMemberMockMvc = MockMvcBuilders.standaloneSetup(teamMemberResource)
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
    public static TeamMember createEntity(EntityManager em) {
        TeamMember teamMember = new TeamMember()
            .updatedTime(DEFAULT_UPDATED_TIME);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        teamMember.setUser(user);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        teamMember.setTeam(team);
        return teamMember;
    }

    @Before
    public void initTest() {
        teamMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamMember() throws Exception {
        int databaseSizeBeforeCreate = teamMemberRepository.findAll().size();

        // Create the TeamMember
        TeamMemberDTO teamMemberDTO = teamMemberMapper.toDto(teamMember);
        restTeamMemberMockMvc.perform(post("/api/team-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamMemberDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeCreate + 1);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void createTeamMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamMemberRepository.findAll().size();

        // Create the TeamMember with an existing ID
        teamMember.setId(1L);
        TeamMemberDTO teamMemberDTO = teamMemberMapper.toDto(teamMember);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMemberMockMvc.perform(post("/api/team-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamMemberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeamMembers() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        // Get all the teamMemberList
        restTeamMemberMockMvc.perform(get("/api/team-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);

        // Get the teamMember
        restTeamMemberMockMvc.perform(get("/api/team-members/{id}", teamMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamMember.getId().intValue()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeamMember() throws Exception {
        // Get the teamMember
        restTeamMemberMockMvc.perform(get("/api/team-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();

        // Update the teamMember
        TeamMember updatedTeamMember = teamMemberRepository.findOne(teamMember.getId());
        // Disconnect from session so that the updates on updatedTeamMember are not directly saved in db
        em.detach(updatedTeamMember);
        updatedTeamMember
            .updatedTime(UPDATED_UPDATED_TIME);
        TeamMemberDTO teamMemberDTO = teamMemberMapper.toDto(updatedTeamMember);

        restTeamMemberMockMvc.perform(put("/api/team-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamMemberDTO)))
            .andExpect(status().isOk());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate);
        TeamMember testTeamMember = teamMemberList.get(teamMemberList.size() - 1);
        assertThat(testTeamMember.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = teamMemberRepository.findAll().size();

        // Create the TeamMember
        TeamMemberDTO teamMemberDTO = teamMemberMapper.toDto(teamMember);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamMemberMockMvc.perform(put("/api/team-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamMemberDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamMember in the database
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeamMember() throws Exception {
        // Initialize the database
        teamMemberRepository.saveAndFlush(teamMember);
        int databaseSizeBeforeDelete = teamMemberRepository.findAll().size();

        // Get the teamMember
        restTeamMemberMockMvc.perform(delete("/api/team-members/{id}", teamMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        assertThat(teamMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamMember.class);
        TeamMember teamMember1 = new TeamMember();
        teamMember1.setId(1L);
        TeamMember teamMember2 = new TeamMember();
        teamMember2.setId(teamMember1.getId());
        assertThat(teamMember1).isEqualTo(teamMember2);
        teamMember2.setId(2L);
        assertThat(teamMember1).isNotEqualTo(teamMember2);
        teamMember1.setId(null);
        assertThat(teamMember1).isNotEqualTo(teamMember2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamMemberDTO.class);
        TeamMemberDTO teamMemberDTO1 = new TeamMemberDTO();
        teamMemberDTO1.setId(1L);
        TeamMemberDTO teamMemberDTO2 = new TeamMemberDTO();
        assertThat(teamMemberDTO1).isNotEqualTo(teamMemberDTO2);
        teamMemberDTO2.setId(teamMemberDTO1.getId());
        assertThat(teamMemberDTO1).isEqualTo(teamMemberDTO2);
        teamMemberDTO2.setId(2L);
        assertThat(teamMemberDTO1).isNotEqualTo(teamMemberDTO2);
        teamMemberDTO1.setId(null);
        assertThat(teamMemberDTO1).isNotEqualTo(teamMemberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teamMemberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teamMemberMapper.fromId(null)).isNull();
    }
}
