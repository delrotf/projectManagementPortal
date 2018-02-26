package com.gtrack.projectmanagementportal.service.impl;

import com.gtrack.projectmanagementportal.service.TeamMemberService;
import com.gtrack.projectmanagementportal.service.TeamService;
import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.repository.TeamRepository;
import com.gtrack.projectmanagementportal.security.SecurityUtils;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import com.gtrack.projectmanagementportal.service.mapper.TeamMapper;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService{

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;
    private final TeamMemberService teamMemberService;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMemberService teamMemberService, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMemberService = teamMemberService;
        this.teamMapper = teamMapper;
    }

    /**
     * Save a team.
     *
     * @param teamDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamDTO save(TeamDTO teamDTO) {
        log.debug("Request to save Team : {}", teamDTO);
        Team team = teamMapper.toEntity(teamDTO);
        team = teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByTeamHeadUserLoginIsCurrentUser(Pageable pageable) {
        log.debug("Request to get Teams by teamhead");
        return teamRepository.findByTeamHeadUserLoginIsCurrentUser(pageable)
            .map(teamMapper::toDto);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActiveAndIdNotIn(boolean isActive, String userLogin, Pageable pageable) {
        log.debug("Request to get Teams by Id not in");
        
        Set<TeamMember> teamMembersOfUser = teamMemberService.findByUserInfoUserLogin(userLogin);
        
        if (teamMembersOfUser != null && !teamMembersOfUser.isEmpty()) {
            Set<Long> ids = new HashSet<>();
            for (TeamMember teamMember : teamMembersOfUser) {
    			ids.add(teamMember.getTeam().getId());
    		}
            return teamRepository.findByActiveAndIdNotIn(isActive, ids, pageable)
                .map(teamMapper::toDto);
        } else {
            return teamRepository.findByActive(isActive, pageable)
                    .map(teamMapper::toDto);
//        	return findAll(pageable);
        }
    }

    /**
     * Get all other teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActiveAndIdNotInAndTeamHeadUserLoginNot(boolean isActive, String userLogin, Pageable pageable) {
        log.debug("Request to get Teams by Id not in");
        
        Set<TeamMember> teamMembersOfUser = teamMemberService.findByUserInfoUserLogin(userLogin);
        
        if (teamMembersOfUser != null && !teamMembersOfUser.isEmpty()) {
            Set<Long> ids = new HashSet<>();
            for (TeamMember teamMember : teamMembersOfUser) {
    			ids.add(teamMember.getTeam().getId());
    		}
            
            return teamRepository.findByActiveAndIdNotInAndTeamHeadUserLoginNot(isActive, ids, userLogin, pageable)
                    .map(teamMapper::toDto);
        } else {
            return teamRepository.findByActiveAndTeamHeadUserLoginNot(isActive, userLogin, pageable)
                    .map(teamMapper::toDto);
        }
        
    }

    /**
     * Get all the teams I'm member of.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActiveAndIdInAndTeamHeadUserLoginNot(boolean isActive, String userLogin, Pageable pageable) {
        log.debug("Request to get Teams by Id not in");
        
        Set<TeamMember> teamMembersOfUser = teamMemberService.findByUserInfoUserLogin(userLogin);
        
        Set<Long> ids = new HashSet<>();

        if (teamMembersOfUser != null && !teamMembersOfUser.isEmpty()) {
            for (TeamMember teamMember : teamMembersOfUser) {
    			ids.add(teamMember.getTeam().getId());
    		}
        }
        
        return teamRepository.findByActiveAndIdInAndTeamHeadUserLoginNot(isActive, ids, userLogin, pageable)
                .map(teamMapper::toDto);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActiveAndIdNotInAndTeamHeadUserLogin(boolean isActive, String userLogin, Pageable pageable) {
        log.debug("Request to get Teams by Id not in");
        
        Set<TeamMember> teamMembersOfUser = teamMemberService.findByUserInfoUserLogin(userLogin);
        
        if (teamMembersOfUser != null && !teamMembersOfUser.isEmpty()) {
            Set<Long> ids = new HashSet<>();
            for (TeamMember teamMember : teamMembersOfUser) {
    			ids.add(teamMember.getTeam().getId());
    		}
            
            return teamRepository.findByActiveAndIdNotInAndTeamHeadUserLogin(isActive, ids, SecurityUtils.getCurrentUserLogin().get(), pageable)
                    .map(teamMapper::toDto);
        } else {
            return teamRepository.findByActiveAndTeamHeadUserLogin(isActive, SecurityUtils.getCurrentUserLogin().get(), pageable)
                    .map(teamMapper::toDto);
        }
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActiveAndTeamHeadUserLogin(boolean isActive, String teamHeadLogin, Pageable pageable) {
        log.debug("Request to get Teams by teamhead");
        return teamRepository.findByActiveAndTeamHeadUserLogin(isActive, teamHeadLogin, pageable)
            .map(teamMapper::toDto);
    }

    /**
     * Get all the teams by active.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByActive(boolean active, Pageable pageable) {
        log.debug("Request to get all Teams by active: {}", active);
        return teamRepository.findByActive(active, pageable)
            .map(teamMapper::toDto);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        return teamRepository.findAll(pageable)
            .map(teamMapper::toDto);
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TeamDTO findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOne(id);
        return teamMapper.toDto(team);
    }

    /**
     * Delete the team by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.delete(id);
    }
}
