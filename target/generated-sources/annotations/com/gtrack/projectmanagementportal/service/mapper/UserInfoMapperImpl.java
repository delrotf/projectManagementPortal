package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.domain.User;
import com.gtrack.projectmanagementportal.domain.UserInfo;
import com.gtrack.projectmanagementportal.service.dto.UserInfoDTO;
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
public class UserInfoMapperImpl implements UserInfoMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DesignationMapper designationMapper;

    @Override
    public List<UserInfo> toEntity(List<UserInfoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserInfo> list = new ArrayList<UserInfo>( dtoList.size() );
        for ( UserInfoDTO userInfoDTO : dtoList ) {
            list.add( toEntity( userInfoDTO ) );
        }

        return list;
    }

    @Override
    public List<UserInfoDTO> toDto(List<UserInfo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserInfoDTO> list = new ArrayList<UserInfoDTO>( entityList.size() );
        for ( UserInfo userInfo : entityList ) {
            list.add( toDto( userInfo ) );
        }

        return list;
    }

    @Override
    public UserInfoDTO toDto(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        String login = userInfoUserLogin( userInfo );
        if ( login != null ) {
            userInfoDTO.setUserLogin( login );
        }
        Long id = userInfoDesignationId( userInfo );
        if ( id != null ) {
            userInfoDTO.setDesignationId( id );
        }
        String login1 = userInfoSupervisorUserLogin( userInfo );
        if ( login1 != null ) {
            userInfoDTO.setSupervisorUserLogin( login1 );
        }
        Long id1 = userInfoSupervisorId( userInfo );
        if ( id1 != null ) {
            userInfoDTO.setSupervisorId( id1 );
        }
        Long id2 = userInfoUserId( userInfo );
        if ( id2 != null ) {
            userInfoDTO.setUserId( id2 );
        }
        String designation = userInfoDesignationDesignation( userInfo );
        if ( designation != null ) {
            userInfoDTO.setDesignationDesignation( designation );
        }
        userInfoDTO.setId( userInfo.getId() );
        userInfoDTO.setFirstName( userInfo.getFirstName() );
        userInfoDTO.setLastName( userInfo.getLastName() );
        userInfoDTO.setCallingName( userInfo.getCallingName() );
        userInfoDTO.setImageUrl( userInfo.getImageUrl() );
        userInfoDTO.setPhone( userInfo.getPhone() );

        return userInfoDTO;
    }

    @Override
    public UserInfo toEntity(UserInfoDTO userInfoDTO) {
        if ( userInfoDTO == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setDesignation( designationMapper.fromId( userInfoDTO.getDesignationId() ) );
        userInfo.setUser( userMapper.userFromId( userInfoDTO.getUserId() ) );
        userInfo.setSupervisor( fromId( userInfoDTO.getSupervisorId() ) );
        userInfo.setId( userInfoDTO.getId() );
        userInfo.setFirstName( userInfoDTO.getFirstName() );
        userInfo.setLastName( userInfoDTO.getLastName() );
        userInfo.setCallingName( userInfoDTO.getCallingName() );
        userInfo.setImageUrl( userInfoDTO.getImageUrl() );
        userInfo.setPhone( userInfoDTO.getPhone() );

        return userInfo;
    }

    private String userInfoUserLogin(UserInfo userInfo) {
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

    private Long userInfoDesignationId(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }
        Designation designation = userInfo.getDesignation();
        if ( designation == null ) {
            return null;
        }
        Long id = designation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String userInfoSupervisorUserLogin(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }
        UserInfo supervisor = userInfo.getSupervisor();
        if ( supervisor == null ) {
            return null;
        }
        User user = supervisor.getUser();
        if ( user == null ) {
            return null;
        }
        String login = user.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long userInfoSupervisorId(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }
        UserInfo supervisor = userInfo.getSupervisor();
        if ( supervisor == null ) {
            return null;
        }
        Long id = supervisor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long userInfoUserId(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }
        User user = userInfo.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String userInfoDesignationDesignation(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }
        Designation designation = userInfo.getDesignation();
        if ( designation == null ) {
            return null;
        }
        String designation1 = designation.getDesignation();
        if ( designation1 == null ) {
            return null;
        }
        return designation1;
    }
}
