package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.*;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;

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
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "team.image", target = "teamImage")
    @Mapping(source = "team.imageContentType", target = "teamImageContentType")
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
