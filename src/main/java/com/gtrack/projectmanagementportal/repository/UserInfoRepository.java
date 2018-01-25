package com.gtrack.projectmanagementportal.repository;

import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    @Query("select user_info from UserInfo user_info where user_info.supervisor.login = ?#{principal.username}")
    List<UserInfo> findBySupervisorIsCurrentUser();

	Page<UserInfo> findByUserLogin(String userLogin, Pageable pageable);
	
    Page<UserInfo> findByUserIdNotIn(Set<Long> userIds, Pageable pageable);
}
