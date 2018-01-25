package com.gtrack.projectmanagementportal.repository;

import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the TeamMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Query("select team_member from TeamMember team_member where team_member.user.login = ?#{principal.username}")
    Page<TeamMember> findByUserIsCurrentUser(Pageable pageable);
    
	Page<TeamMember> findByUserLogin(String userLogin, Pageable pageable);
	Set<TeamMember> findByUserLogin(String userLogin);

	Page<TeamMember> findByTeamId(Long id, Pageable pageable);
	Set<TeamMember> findByTeamId(Long id);
}
