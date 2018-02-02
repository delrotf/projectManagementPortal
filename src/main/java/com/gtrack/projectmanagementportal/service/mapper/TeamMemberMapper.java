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
    @Mapping(source = "userInfo.user.login", target = "userInfoUserLogin")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
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
