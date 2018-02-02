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

    @Query("select team_member from TeamMember team_member where team_member.userInfo.user.login = ?#{principal.username}")
    Page<TeamMember> findByUserInfoUserLoginIsCurrentUser(Pageable pageable);
    
	Page<TeamMember> findByUserInfoUserLogin(String userLogin, Pageable pageable);
	Set<TeamMember> findByUserInfoUserLogin(String userLogin);

	Page<TeamMember> findByUserInfoId(Long userInfoId, Pageable pageable);
	Set<TeamMember> findByUserInfoId(Long userInfoId);

	Page<TeamMember> findByTeamId(Long teamId, Pageable pageable);
	Set<TeamMember> findByTeamId(Long teamId);
}
