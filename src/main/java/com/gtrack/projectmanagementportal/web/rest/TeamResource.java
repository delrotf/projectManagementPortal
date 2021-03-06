package com.gtrack.projectmanagementportal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.security.AuthoritiesConstants;
import com.gtrack.projectmanagementportal.security.SecurityUtils;
import com.gtrack.projectmanagementportal.service.TeamMemberService;
import com.gtrack.projectmanagementportal.service.TeamService;
import com.gtrack.projectmanagementportal.web.rest.errors.BadRequestAlertException;
import com.gtrack.projectmanagementportal.web.rest.util.HeaderUtil;
import com.gtrack.projectmanagementportal.web.rest.util.PaginationUtil;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;

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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Team.
 */
@RestController
@RequestMapping("/api")
public class TeamResource {

    private final Logger log = LoggerFactory.getLogger(TeamResource.class);
    
    public static final String VIEW_ID = "viewId";
    public static final String VIEW_TEAMS_My = "myTeams";
    public static final String VIEW_TEAMS_ALL = "allTeams";
    public static final String VIEW_TEAMS_IM_MEMBER_OF = "teamsImMemberOf";
    public static final String VIEW_TEAMS_BROWSE_MORE = "browseMoreTeams";
    public static final String VIEW_TEAMS_SELECT = "selectTeams";

    public static final String VIEW_TEAMS_USERS_HEADED = "usersHeadedTeams";
    public static final String VIEW_TEAMS_USERS_MEMBER_OF = "usersMemberOf";
    public static final String VIEW_TEAMS_USERS_MEMBER_OF_MY = "usersMemberOfMyTeams";
    
    public static final String ACTION = "action";
    public static final String ACTION_ADD_TEAMS_TO_USER = "addTeamsToUser";

    private static final String ENTITY_NAME = "team";

    private final TeamService teamService;

    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * POST  /teams : Create a new team.
     *
     * @param teamDTO the teamDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamDTO, or with status 400 (Bad Request) if the team has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teams")
    @Timed
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO) throws URISyntaxException {
        log.debug("REST request to save Team : {}", teamDTO);
        if (teamDTO.getId() != null) {
            throw new BadRequestAlertException("A new team cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamDTO result = teamService.save(teamDTO);
        return ResponseEntity.created(new URI("/api/teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teams : Updates an existing team.
     *
     * @param teamDTO the teamDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamDTO,
     * or with status 400 (Bad Request) if the teamDTO is not valid,
     * or with status 500 (Internal Server Error) if the teamDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teams")
    @Timed
    public ResponseEntity<TeamDTO> updateTeam(@Valid @RequestBody TeamDTO teamDTO) throws URISyntaxException {
        log.debug("REST request to update Team : {}", teamDTO);
        if (teamDTO.getId() == null) {
            return createTeam(teamDTO);
        }
        TeamDTO result = teamService.save(teamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teams : get all the teams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teams in body
     */
    @GetMapping("/teams")
    @Timed
    public ResponseEntity<List<TeamDTO>> getAllTeams(@RequestParam(value="query", required=false) String query, Pageable pageable) {
        log.debug("REST request to get a page of Teams; query: {}", query);
    	Page<TeamDTO> page = null;
        JSONObject json = null;
        
        String viewId = null;
        String action = null;
        
        String userLogin = null;
        String teamHeadUserLogin = null;
        String inactive = null;
        
        if (query != null) {
            try {
    			json = new JSONObject(query);
    		} catch (JSONException e) {
    			// do nothing.
    		}

            if (json != null) {
                try {
                    viewId = json.getString(VIEW_ID);
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                    action = json.getString(ACTION);
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                    userLogin = json.getString("userLogin");
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                    teamHeadUserLogin = json.getString("teamHeadUserLogin");
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                	inactive = json.getString("inactive");
        		} catch (JSONException e) {
        			// do nothing.
        		}
            }
        }
        boolean isInactive = inactive != null ? Boolean.parseBoolean(inactive) : false;
        
        HttpHeaders headers = null;
        if (action != null) {
            if (action.equals(ACTION_ADD_TEAMS_TO_USER) && userLogin != null) {
            	// drop-down items.
            	if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            		page = teamService.findByActiveAndIdNotIn(true, userLogin, pageable);
            	} else {
            		page = teamService.findByActiveAndIdNotInAndTeamHeadUserLogin(true, userLogin, pageable);
            	}
//            	page = teamService.findByActiveAndIdNotIn(isActive, userLogin, pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?action=" + ACTION_ADD_TEAMS_TO_USER + "&userLogin=" + userLogin);
            }
        }
        if (viewId != null) {
        	if (viewId.equals(VIEW_TEAMS_ALL) && SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            	// all access for admin
            	page = teamService.findByActive(!isInactive, pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams");
            } else if (viewId.equals(VIEW_TEAMS_IM_MEMBER_OF)) {
            	// teams Im member of.
            	page = teamService.findByActiveAndIdInAndTeamHeadUserLoginNot(true, SecurityUtils.getCurrentUserLogin().get(), pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_IM_MEMBER_OF);
            } else if (viewId.equals(VIEW_TEAMS_USERS_MEMBER_OF) && userLogin != null) {
            	// teams user is member of.
            	if (!isInactive) {
                	page = teamService.findByActiveAndIdInAndTeamHeadUserLoginNot(true, userLogin, pageable);
                	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_USERS_MEMBER_OF + "&userLogin=" + userLogin);
            	} else {
	            	page = teamService.findByActiveAndIdInAndTeamHeadUserLoginNot(false, userLogin, pageable);
	            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_USERS_MEMBER_OF + "&isInactive=" + isInactive + "&userLogin=" + userLogin);
            	}
        	} else if (viewId.equals(VIEW_TEAMS_USERS_MEMBER_OF_MY) && userLogin != null) {
            	// teams user is member of my teams.
            	page = teamService.findByActiveAndIdInAndTeamHeadUserLogin(true, userLogin, SecurityUtils.getCurrentUserLogin().get(), pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_USERS_MEMBER_OF_MY + "&userLogin=" + userLogin);
            } else if (viewId.equals(VIEW_TEAMS_BROWSE_MORE)) {
            	// browse all other teams.
            	page = teamService.findByActiveAndIdNotInAndTeamHeadUserLoginNot(true, SecurityUtils.getCurrentUserLogin().get(), pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_BROWSE_MORE);
            } else if (viewId.equals(VIEW_TEAMS_My)) {
            	// my headed teams.
            	if (!isInactive) {
                	page = teamService.findByActiveAndTeamHeadUserLogin(true, SecurityUtils.getCurrentUserLogin().get(), pageable);
                	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_My);
            	} else {
            		page = teamService.findByActiveAndTeamHeadUserLogin(false, SecurityUtils.getCurrentUserLogin().get(), pageable);
            		headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_My + "&isInactive=" + isInactive);
            	}
            } else if (viewId.equals(VIEW_TEAMS_USERS_HEADED) && teamHeadUserLogin != null) {
            	// user headed teams.
            	page = teamService.findByActiveAndTeamHeadUserLogin(true, teamHeadUserLogin, pageable);
            	headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams?viewId=" + VIEW_TEAMS_USERS_HEADED + "&teamHeadUserLogin=" + teamHeadUserLogin);
            }
        }
        
        if (action != null) {
        	
        }
        
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teams/:id : get the "id" team.
     *
     * @param id the id of the teamDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teams/{id}")
    @Timed
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long id) {
        log.debug("REST request to get Team : {}", id);
        TeamDTO teamDTO = teamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teamDTO));
    }

    /**
     * DELETE  /teams/:id : delete the "id" team.
     *
     * @param id the id of the teamDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teams/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        log.debug("REST request to delete Team : {}", id);
        teamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
