package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.*;
import com.gtrack.projectmanagementportal.service.dto.DesignationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Designation and its DTO DesignationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DesignationMapper extends EntityMapper<DesignationDTO, Designation> {

    

    

    default Designation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Designation designation = new Designation();
        designation.setId(id);
        return designation;
    }
}
