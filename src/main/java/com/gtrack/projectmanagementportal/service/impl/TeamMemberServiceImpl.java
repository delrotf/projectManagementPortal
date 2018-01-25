package com.gtrack.projectmanagementportal.service.impl;

import com.gtrack.projectmanagementportal.service.TeamMemberService;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.repository.TeamMemberRepository;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;
import com.gtrack.projectmanagementportal.service.mapper.TeamMemberMapper;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TeamMember.
 */
@Service
@Transactional
public class TeamMemberServiceImpl implements TeamMemberService{

    private final Logger log = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    private final TeamMemberRepository teamMemberRepository;

    private final TeamMemberMapper teamMemberMapper;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository, TeamMemberMapper teamMemberMapper) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberMapper = teamMemberMapper;
    }

    /**
     * Save a teamMember.
     *
     * @param teamMemberDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamMemberDTO save(TeamMemberDTO teamMemberDTO) {
        log.debug("Request to save TeamMember : {}", teamMemberDTO);
        TeamMember teamMember = teamMemberMapper.toEntity(teamMemberDTO);
        teamMember = teamMemberRepository.save(teamMember);
        return teamMemberMapper.toDto(teamMember);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamMembers");
        return teamMemberRepository.findAll(pageable)
            .map(teamMemberMapper::toDto);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamMemberDTO> findByTeamId(Long id, Pageable pageable) {
        log.debug("Request to get all TeamMembers By Team Id: " + id);
        return teamMemberRepository.findByTeamId(id, pageable)
            .map(teamMemberMapper::toDto);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamMemberDTO> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all TeamMembers By Current User");
        return teamMemberRepository.findByUserIsCurrentUser(pageable)
            .map(teamMemberMapper::toDto);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamMemberDTO> findByUserLogin(String userLogin, Pageable pageable) {
        log.debug("Request to get all TeamMembers By User Login");
        return teamMemberRepository.findByUserLogin(userLogin, pageable)
            .map(teamMemberMapper::toDto);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<TeamMember> findByUserLogin(String userLogin) {
        log.debug("Request to get all Teams By User Login: {}", userLogin);
        return teamMemberRepository.findByUserLogin(userLogin);
    }

    /**
     * Get all the teamMembers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Set<TeamMember> findByTeamId(Long teamId) {
        log.debug("Request to get all Teams By Team Id: {}", teamId);
        return teamMemberRepository.findByTeamId(teamId);
    }

    /**
     * Get one teamMember by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TeamMemberDTO findOne(Long id) {
        log.debug("Request to get TeamMember : {}", id);
        TeamMember teamMember = teamMemberRepository.findOne(id);
        return teamMemberMapper.toDto(teamMember);
    }

    /**
     * Delete the teamMember by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamMember : {}", id);
        teamMemberRepository.delete(id);
    }
}
