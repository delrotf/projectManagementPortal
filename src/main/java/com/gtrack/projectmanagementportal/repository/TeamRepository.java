package com.gtrack.projectmanagementportal.repository;

import com.gtrack.projectmanagementportal.domain.Team;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select team from Team team where team.teamHead.login = ?#{principal.username}")
    Page<Team> findByTeamHeadIsCurrentUser(Pageable pageable);
    
    Page<Team> findByTeamHeadLogin(String teamHeadLogin, Pageable pageable);
    
    Page<Team> findByIdNotIn(Set<Long> ids, Pageable pageable);
}
