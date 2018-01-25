package com.gtrack.projectmanagementportal.service.impl;

import com.gtrack.projectmanagementportal.service.TeamMemberService;
import com.gtrack.projectmanagementportal.service.UserInfoService;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import com.gtrack.projectmanagementportal.repository.UserInfoRepository;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import com.gtrack.projectmanagementportal.service.dto.UserInfoDTO;
import com.gtrack.projectmanagementportal.service.mapper.UserInfoMapper;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserInfo.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService{

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final TeamMemberService teamMemberService;
    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, TeamMemberService teamMemberService, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.teamMemberService = teamMemberService;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable)
            .map(userInfoMapper::toDto);
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findByUserIdNotIn(Long teamId, Pageable pageable) {
        log.debug("Request to get UserInfo by Id not in");
        
        Set<TeamMember> teamMembersOfTeam = teamMemberService.findByTeamId(teamId);
        
        if (teamMembersOfTeam != null && !teamMembersOfTeam.isEmpty()) {
            
            Set<Long> userIds = new HashSet<>();
            
            // exclude system generated user ids.
            userIds.add(1L);
            userIds.add(2L);
            userIds.add(3L);
            userIds.add(4L);
            
            for (TeamMember teamMember : teamMembersOfTeam) {
            	userIds.add(teamMember.getUser().getId());
    		}
            return userInfoRepository.findByUserIdNotIn(userIds, pageable)
                .map(userInfoMapper::toDto);
        } else {
        	return findAll(pageable);
        }
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findByUserLogin(String userLogin, Pageable pageable) {
        log.debug("Request to get all UserInfos by User Login");
        return userInfoRepository.findByUserLogin(userLogin, pageable)
            .map(userInfoMapper::toDto);
    }

    /**
     * Get one userInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        UserInfo userInfo = userInfoRepository.findOne(id);
        return userInfoMapper.toDto(userInfo);
    }

    /**
     * Delete the userInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.delete(id);
    }
}
