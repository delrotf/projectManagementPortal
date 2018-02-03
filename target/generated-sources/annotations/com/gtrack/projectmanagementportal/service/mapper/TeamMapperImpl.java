package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.domain.User;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import com.gtrack.projectmanagementportal.service.dto.TeamDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-02-03T00:36:25+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_152 (Oracle Corporation)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<Team> toEntity(List<TeamDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Team> list = new ArrayList<Team>( dtoList.size() );
        for ( TeamDTO teamDTO : dtoList ) {
            list.add( toEntity( teamDTO ) );
        }

        return list;
    }

    @Override
    public List<TeamDTO> toDto(List<Team> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TeamDTO> list = new ArrayList<TeamDTO>( entityList.size() );
        for ( Team team : entityList ) {
            list.add( toDto( team ) );
        }

        return list;
    }

    @Override
    public TeamDTO toDto(Team team) {
        if ( team == null ) {
            return null;
        }

        TeamDTO teamDTO = new TeamDTO();

        String login = teamTeamHeadUserLogin( team );
        if ( login != null ) {
            teamDTO.setTeamHeadUserLogin( login );
        }
        Long id = teamTeamHeadId( team );
        if ( id != null ) {
            teamDTO.setTeamHeadId( id );
        }
        teamDTO.setId( team.getId() );
        teamDTO.setName( team.getName() );
        teamDTO.setCode( team.getCode() );
        teamDTO.setImageUrl( team.getImageUrl() );
        teamDTO.setProcessOrder( team.isProcessOrder() );
        teamDTO.setProcessExternalTask( team.isProcessExternalTask() );
        teamDTO.setActive( team.isActive() );
        teamDTO.setCreatedDate( team.getCreatedDate() );

        return teamDTO;
    }

    @Override
    public Team toEntity(TeamDTO teamDTO) {
        if ( teamDTO == null ) {
            return null;
        }

        Team team = new Team();

        team.setTeamHead( userInfoMapper.fromId( teamDTO.getTeamHeadId() ) );
        team.setId( teamDTO.getId() );
        team.setName( teamDTO.getName() );
        team.setCode( teamDTO.getCode() );
        team.setImageUrl( teamDTO.getImageUrl() );
        team.setProcessOrder( teamDTO.isProcessOrder() );
        team.setProcessExternalTask( teamDTO.isProcessExternalTask() );
        team.setActive( teamDTO.isActive() );
        team.setCreatedDate( teamDTO.getCreatedDate() );

        return team;
    }

    private String teamTeamHeadUserLogin(Team team) {
        if ( team == null ) {
            return null;
        }
        UserInfo teamHead = team.getTeamHead();
        if ( teamHead == null ) {
            return null;
        }
        User user = teamHead.getUser();
        if ( user == null ) {
            return null;
        }
        String login = user.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long teamTeamHeadId(Team team) {
        if ( team == null ) {
            return null;
        }
        UserInfo teamHead = team.getTeamHead();
        if ( teamHead == null ) {
            return null;
        }
        Long id = teamHead.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
