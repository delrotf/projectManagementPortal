package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.*;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {UserInfoMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {

    @Mapping(source = "teamHead.id", target = "teamHeadId")
    @Mapping(source = "teamHead.user.login", target = "teamHeadUserLogin")
    @Mapping(source = "teamHead.callingName", target = "teamHeadCallingName")
    @Mapping(source = "teamHead.user.firstName", target = "teamHeadUserFirstName")
    @Mapping(source = "teamHead.user.lastName", target = "teamHeadUserLastName")
    @Mapping(source = "teamHead.user.email", target = "teamHeadUserEmail")
    @Mapping(source = "teamHead.phone", target = "teamHeadPhone")
    @Mapping(source = "teamHead.supervisor.id", target = "teamHeadSupervisorId")
    @Mapping(source = "teamHead.supervisor.user.firstName", target = "teamHeadSupervisorUserFirstName")
    @Mapping(source = "teamHead.supervisor.user.lastName", target = "teamHeadSupervisorUserLastName")
    @Mapping(source = "teamHead.supervisor.callingName", target = "teamHeadSupervisorCallingName")
    @Mapping(source = "teamHead.supervisor.user.login", target = "teamHeadSupervisorUserLogin")
    @Mapping(source = "teamHead.supervisor.image", target = "teamHeadSupervisorImage")
    @Mapping(source = "teamHead.supervisor.imageContentType", target = "teamHeadSupervisorImageContentType")
    @Mapping(source = "teamHead.image", target = "teamHeadImage")
    @Mapping(source = "teamHead.imageContentType", target = "teamHeadImageContentType")
    TeamDTO toDto(Team team); 

    @Mapping(source = "teamHeadId", target = "teamHead")
    Team toEntity(TeamDTO teamDTO);

    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
