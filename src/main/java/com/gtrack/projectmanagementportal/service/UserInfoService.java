package com.gtrack.projectmanagementportal.service;

import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import com.gtrack.projectmanagementportal.service.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserInfo.
 */
public interface UserInfoService {

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save
     * @return the persisted entity
     */
    UserInfoDTO save(UserInfoDTO userInfoDTO);

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserInfoDTO findOne(Long id);

    /**
     * Delete the "id" userInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
	Page<UserInfoDTO> findByUserLogin(String userLogin, Pageable pageable);
	Page<UserInfoDTO> findByUserIdNotIn(Long teamId, Pageable pageable);
}
