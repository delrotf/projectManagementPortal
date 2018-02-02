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
        String userLogin = null;
        String teamHeadUserLogin = null;
        String active = null;
        String allOthers = null;
        String headed = null;
        
        if (query != null) {
            try {
    			json = new JSONObject(query);
    		} catch (JSONException e) {
    			// do nothing.
    		}
            if (json != null) {
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
                	active = json.getString("active");
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                	allOthers = json.getString("allOthers");
        		} catch (JSONException e) {
        			// do nothing.
        		}
                try {
                	headed = json.getString("headed");
        		} catch (JSONException e) {
        			// do nothing.
        		}
            }
        }
        boolean isActive = active != null ? Boolean.parseBoolean(active) : true;
        boolean isAllOthers = allOthers != null ? Boolean.parseBoolean(allOthers) : false;
        boolean isHeaded = headed != null ? Boolean.parseBoolean(headed) : false;
        
        if (userLogin != null && !isAllOthers && !isHeaded) {
        	// drop-down items.
        	page = teamService.findByActiveAndIdNotInAndTeamHeadUserLogin(isActive, teamHeadUserLogin, pageable);
//        	page = teamService.findByActiveAndIdNotIn(isActive, userLogin, pageable);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
        	// all access for admin
        	page = teamService.findAll(pageable);
        } else if (isAllOthers) {
        	// browse all other teams.
        	page = teamService.findByActiveAndIdNotInAndTeamHeadUserLoginNot(isActive, SecurityUtils.getCurrentUserLogin().get(), pageable);
        } else if (isHeaded) {
        	// headed teams.
        	page = teamService.findByActiveAndTeamHeadUserLogin(isActive, teamHeadUserLogin, pageable);
        }
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams");
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
