package com.gtrack.projectmanagementportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.service.DesignationService;
import com.gtrack.projectmanagementportal.service.UserInfoService;
import com.gtrack.projectmanagementportal.web.rest.errors.BadRequestAlertException;
import com.gtrack.projectmanagementportal.web.rest.util.HeaderUtil;
import com.gtrack.projectmanagementportal.web.rest.util.PaginationUtil;
import com.gtrack.projectmanagementportal.service.dto.UserInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserInfo.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserInfoResource.class);

    private static final String ENTITY_NAME = "userInfo";

    public static final String PAGE_USERS_SELECT = "selectUsers";
    public static final String PAGE_USERS = "users";

    private final UserInfoService userInfoService;
    private final DesignationService designationService;

    public UserInfoResource(UserInfoService userInfoService, DesignationService designationService) {
        this.userInfoService = userInfoService;
        this.designationService = designationService;
    }

    /**
     * POST  /user-infos : Create a new userInfo.
     *
     * @param userInfoDTO the userInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userInfoDTO, or with status 400 (Bad Request) if the userInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-infos")
    @Timed
    public ResponseEntity<UserInfoDTO> createUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to save UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity.created(new URI("/api/user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-infos : Updates an existing userInfo.
     *
     * @param userInfoDTO the userInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userInfoDTO,
     * or with status 400 (Bad Request) if the userInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the userInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-infos")
    @Timed
    public ResponseEntity<UserInfoDTO> updateUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to update UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() == null) {
            return createUserInfo(userInfoDTO);
        }
        
        String designationDesignation = userInfoDTO.getDesignationDesignation();
        
        if (designationDesignation != null) {
	        Designation designation = designationService.findOneByDesignation(designationDesignation);
	        
	        Long designationId = null;
	        // if designation is null, save a new one.
	        if (designation == null) {
	        	designation = new Designation();
	            designation.setDesignation(userInfoDTO.getDesignationDesignation());
	            Designation persistedDesignation = designationService.save(designation);
	            log.debug("persistedDesignation : {}", persistedDesignation);
	            designationId = persistedDesignation.getId();
	        } else {
	        	designationId = designation.getId();
	        }
	        
	        userInfoDTO.setDesignationId(designationId);
        }
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-infos : get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userInfos in body
     */
    @GetMapping("/user-infos1")
    @Timed
    public ResponseEntity<List<UserInfoDTO>> getAllUserInfos(Pageable pageable) {
        log.debug("REST request to get a page of UserInfos");
        Page<UserInfoDTO> page = userInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-infos : get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userInfos in body
     */
    @GetMapping("/user-infos")
    @Timed
    public ResponseEntity<List<UserInfoDTO>> getAllUserInfosByQuery(@RequestParam(value="query", required=false) String query, Pageable pageable) {
        log.debug("REST request to get a page of UserInfos; query: " + query);
        
        Page<UserInfoDTO> page = null;
        JSONObject json = null;
		String teamId = null;
		String teamName = null;
		String userInfoId = null;
		String userId = null;
		String userLogin = null;
      
        if (query != null) {
            try {
            	json = new JSONObject(query);
            } catch (JSONException e) {
            	// do nothing.
            }
            if (json != null) {
	        	try {
					teamId = json.getString("teamId");
				} catch (JSONException e1) {
	            	// do nothing.
				}
	        	try {
					teamName = json.getString("teamName");
				} catch (JSONException e) {
	            	// do nothing.
				}
	        	try {
					userInfoId = json.getString("userInfoId");
				} catch (JSONException e) {
	            	// do nothing.
				}
	        	try {
					userId = json.getString("userId");
				} catch (JSONException e) {
	            	// do nothing.
				}
	        	try {
					userLogin = json.getString("userLogin");
				} catch (JSONException e) {
	            	// do nothing.
				}
            }
        }
        HttpHeaders headers = null;
        if (teamId != null) {
        	// users drop-down items.
//            page = userInfoService.findByUserIdNotIn(Long.valueOf(teamId), pageable);
            page = userInfoService.findByIdNotIn(Long.valueOf(teamId), pageable);
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-infos?teamId=" + teamId + "&teamName=" + teamName);
        } else if (userLogin != null) {
        	// for updating user's info in settings.
        	page = userInfoService.findByUserLogin(userLogin, pageable);
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-infos?userLogin=" + userLogin);
        } else {
        	// listing all users in table.
            page = userInfoService.findAll(pageable);
            headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-infos");
        }
        
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-infos/:id : get the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-infos/{id}")
    @Timed
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        UserInfoDTO userInfoDTO = userInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userInfoDTO));
    }

    /**
     * DELETE  /user-infos/:id : delete the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete UserInfo : {}", id);
        userInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
