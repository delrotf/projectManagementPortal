package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.*;
import com.gtrack.projectmanagementportal.service.dto.UserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserInfo and its DTO UserInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DesignationMapper.class})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "supervisor.user.login", target = "supervisorUserLogin")
    @Mapping(source = "supervisor.user.firstName", target = "supervisorUserFirstName")
    @Mapping(source = "supervisor.user.lastName", target = "supervisorUserLastName")
    @Mapping(source = "supervisor.callingName", target = "supervisorCallingName")
    @Mapping(source = "designation.id", target = "designationId")
    @Mapping(source = "designation.designation", target = "designationDesignation")
    UserInfoDTO toDto(UserInfo userInfo);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "supervisorId", target = "supervisor")
    @Mapping(source = "designationId", target = "designation")
    UserInfo toEntity(UserInfoDTO userInfoDTO);

    default UserInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        return userInfo;
    }
}
