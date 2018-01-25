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
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "supervisor.login", target = "supervisorLogin")
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
