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
