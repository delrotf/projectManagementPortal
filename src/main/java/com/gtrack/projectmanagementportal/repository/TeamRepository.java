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

    @Query("select team from Team team where team.teamHead.user.login = ?#{principal.username}")
    Page<Team> findByTeamHeadUserLoginIsCurrentUser(Pageable pageable);
    // for My Teams
    Page<Team> findByActiveAndTeamHeadUserLogin(boolean active, String teamHeadLogin, Pageable pageable);
    
    Page<Team> findByActiveAndIdNotIn(boolean active, Set<Long> ids, Pageable pageable);

    // for teams Im member of
    Page<Team> findByActiveAndIdInAndTeamHeadUserLoginNot(boolean active, Set<Long> ids, String teamHeadLogin, Pageable pageable);

    // for all others
    Page<Team> findByActiveAndIdNotInAndTeamHeadUserLoginNot(boolean active, Set<Long> ids, String teamHeadLogin, Pageable pageable);
    Page<Team> findByActiveAndTeamHeadUserLoginNot(boolean active, String teamHeadLogin, Pageable pageable);

    // for drop-down list
    Page<Team> findByActiveAndIdNotInAndTeamHeadUserLogin(boolean active, Set<Long> ids, String teamHeadLogin, Pageable pageable);

    // find all by active
    Page<Team> findByActive(boolean active, Pageable pageable);

    @Query("select team from Team team where team.teamHead.user.login = ?#{principal.username}")
    List<Team> findByTeamHeadUserLoginIsCurrentUser();    
}
