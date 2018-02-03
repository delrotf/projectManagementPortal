package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.Team;
import com.gtrack.projectmanagementportal.domain.TeamMember;
import com.gtrack.projectmanagementportal.domain.User;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import com.gtrack.projectmanagementportal.service.dto.TeamMemberDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-02-03T00:36:24+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_152 (Oracle Corporation)"
)
@Component
public class TeamMemberMapperImpl implements TeamMemberMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<TeamMember> toEntity(List<TeamMemberDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TeamMember> list = new ArrayList<TeamMember>( dtoList.size() );
        for ( TeamMemberDTO teamMemberDTO : dtoList ) {
            list.add( toEntity( teamMemberDTO ) );
        }

        return list;
    }

    @Override
    public List<TeamMemberDTO> toDto(List<TeamMember> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TeamMemberDTO> list = new ArrayList<TeamMemberDTO>( entityList.size() );
        for ( TeamMember teamMember : entityList ) {
            list.add( toDto( teamMember ) );
        }

        return list;
    }

    @Override
    public TeamMemberDTO toDto(TeamMember teamMember) {
        if ( teamMember == null ) {
            return null;
        }

        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();

        String name = teamMemberTeamName( teamMember );
        if ( name != null ) {
            teamMemberDTO.setTeamName( name );
        }
        String login = teamMemberUserInfoUserLogin( teamMember );
        if ( login != null ) {
            teamMemberDTO.setUserInfoUserLogin( login );
        }
        Long id = teamMemberTeamId( teamMember );
        if ( id != null ) {
            teamMemberDTO.setTeamId( id );
        }
        Long id1 = teamMemberUserInfoId( teamMember );
        if ( id1 != null ) {
            teamMemberDTO.setUserInfoId( id1 );
        }
        teamMemberDTO.setId( teamMember.getId() );
        teamMemberDTO.setUpdatedTime( teamMember.getUpdatedTime() );

        return teamMemberDTO;
    }

    @Override
    public TeamMember toEntity(TeamMemberDTO teamMemberDTO) {
        if ( teamMemberDTO == null ) {
            return null;
        }

        TeamMember teamMember = new TeamMember();

        teamMember.setUserInfo( userInfoMapper.fromId( teamMemberDTO.getUserInfoId() ) );
        teamMember.setTeam( teamMapper.fromId( teamMemberDTO.getTeamId() ) );
        teamMember.setId( teamMemberDTO.getId() );
        teamMember.setUpdatedTime( teamMemberDTO.getUpdatedTime() );

        return teamMember;
    }

    private String teamMemberTeamName(TeamMember teamMember) {
        if ( teamMember == null ) {
            return null;
        }
        Team team = teamMember.getTeam();
        if ( team == null ) {
            return null;
        }
        String name = team.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String teamMemberUserInfoUserLogin(TeamMember teamMember) {
        if ( teamMember == null ) {
            return null;
        }
        UserInfo userInfo = teamMember.getUserInfo();
        if ( userInfo == null ) {
            return null;
        }
        User user = userInfo.getUser();
        if ( user == null ) {
            return null;
        }
        String login = user.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long teamMemberTeamId(TeamMember teamMember) {
        if ( teamMember == null ) {
            return null;
        }
        Team team = teamMember.getTeam();
        if ( team == null ) {
            return null;
        }
        Long id = team.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long teamMemberUserInfoId(TeamMember teamMember) {
        if ( teamMember == null ) {
            return null;
        }
        UserInfo userInfo = teamMember.getUserInfo();
        if ( userInfo == null ) {
            return null;
        }
        Long id = userInfo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
