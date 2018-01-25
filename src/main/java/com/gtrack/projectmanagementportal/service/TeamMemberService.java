package com.gtrack.projectmanagementportal.service;

import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TeamMember.
 */
public interface TeamMemberService {

    /**
     * Save a teamMember.
     *
     * @param teamMemberDTO the entity to save
     * @return the persisted entity
     */
    TeamMemberDTO save(TeamMemberDTO teamMemberDTO);

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamMemberDTO> findAll(Pageable pageable);

    /**
     * Get the "id" teamMember.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TeamMemberDTO findOne(Long id);

    /**
     * Delete the "id" teamMember.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Page<TeamMemberDTO> findByTeamId(Long id, Pageable pageable);
	Page<TeamMemberDTO> findByUserIsCurrentUser(Pageable pageable);
	Page<TeamMemberDTO> findByUserLogin(String userLogin, Pageable pageable);
	Set<TeamMember> findByUserLogin(String userLogin);
	Set<TeamMember> findByTeamId(Long teamId);
}
