package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.*;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;

import java.time.Instant;

import org.mapstruct.*;

/**
 * Mapper for the entity TeamMember and its DTO TeamMemberDTO.
 */
@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, TeamMapper.class})
public interface TeamMemberMapper extends EntityMapper<TeamMemberDTO, TeamMember> {

    @Mapping(source = "userInfo.id", target = "userInfoId")
    @Mapping(source = "userInfo.callingName", target = "userInfoCallingName")
    @Mapping(source = "userInfo.image", target = "userInfoImage")
    @Mapping(source = "userInfo.imageContentType", target = "userInfoImageContentType")
    @Mapping(source = "userInfo.user.login", target = "userInfoUserLogin")
    @Mapping(source = "userInfo.user.firstName", target = "userInfoUserFirstName")
    @Mapping(source = "userInfo.user.lastName", target = "userInfoUserLastName")
    @Mapping(source = "userInfo.user.email", target = "userInfoUserEmail")
    @Mapping(source = "userInfo.phone", target = "userInfoPhone")
    @Mapping(source = "userInfo.supervisor.id", target = "userInfoSupervisorId")
    @Mapping(source = "userInfo.supervisor.callingName", target = "userInfoSupervisorCallingName")
    @Mapping(source = "userInfo.supervisor.image", target = "userInfoSupervisorImage")
    @Mapping(source = "userInfo.supervisor.imageContentType", target = "userInfoSupervisorImageContentType")
    @Mapping(source = "userInfo.supervisor.user.login", target = "userInfoSupervisorUserLogin")
    @Mapping(source = "userInfo.supervisor.user.firstName", target = "userInfoSupervisorUserFirstName")
    @Mapping(source = "userInfo.supervisor.user.lastName", target = "userInfoSupervisorUserLastName")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "team.code", target = "teamCode")
    @Mapping(source = "team.createdDate", target = "teamCreatedDate")
    @Mapping(source = "team.image", target = "teamImage")
    @Mapping(source = "team.imageContentType", target = "teamImageContentType")
    @Mapping(source = "team.teamHead.user.login", target = "teamTeamHeadUserLogin")
    @Mapping(source = "team.teamHead.callingName", target = "teamTeamHeadCallingName")
    @Mapping(source = "team.teamHead.user.firstName", target = "teamTeamHeadUserFirstName")
    @Mapping(source = "team.teamHead.user.lastName", target = "teamTeamHeadUserLastName")
    @Mapping(source = "team.teamHead.image", target = "teamTeamHeadImage")
    @Mapping(source = "team.teamHead.imageContentType", target = "teamTeamHeadImageContentType")
    TeamMemberDTO toDto(TeamMember teamMember);

    @Mapping(source = "userInfoId", target = "userInfo")
    @Mapping(source = "teamId", target = "team")
    TeamMember toEntity(TeamMemberDTO teamMemberDTO);

    default TeamMember fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeamMember teamMember = new TeamMember();
        teamMember.setId(id);
        return teamMember;
    }
}
