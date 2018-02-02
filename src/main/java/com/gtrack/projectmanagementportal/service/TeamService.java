package com.gtrack.projectmanagementportal.service;

import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     *
     * @param teamDTO the entity to save
     * @return the persisted entity
     */
    TeamDTO save(TeamDTO teamDTO);

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" team.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TeamDTO findOne(Long id);

    /**
     * Delete the "id" team.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Page<TeamDTO> findByTeamHeadUserLoginIsCurrentUser(Pageable pageable);
	Page<TeamDTO> findByActiveAndTeamHeadUserLogin(boolean isActive, String teamHeadLogin, Pageable pageable);
	Page<TeamDTO> findByActiveAndIdNotInAndTeamHeadUserLoginNot(boolean isActive, String teamHeadLogin, Pageable pageable);
	Page<TeamDTO> findByActiveAndIdNotInAndTeamHeadUserLogin(boolean isActive, String teamHeadLogin, Pageable pageable);
	Page<TeamDTO> findByActiveAndIdNotIn(boolean isActive, String userLogin, Pageable pageable);
	Page<TeamDTO> findByActive(boolean isActive, Pageable pageable);
}
