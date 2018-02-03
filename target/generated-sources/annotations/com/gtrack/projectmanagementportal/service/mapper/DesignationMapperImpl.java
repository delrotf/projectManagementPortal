package com.gtrack.projectmanagementportal.service.mapper;

import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.service.dto.DesignationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-02-03T00:36:25+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_152 (Oracle Corporation)"
)
@Component
public class DesignationMapperImpl implements DesignationMapper {

    @Override
    public Designation toEntity(DesignationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Designation designation = new Designation();

        designation.setId( dto.getId() );
        designation.setDesignation( dto.getDesignation() );

        return designation;
    }

    @Override
    public DesignationDTO toDto(Designation entity) {
        if ( entity == null ) {
            return null;
        }

        DesignationDTO designationDTO = new DesignationDTO();

        designationDTO.setId( entity.getId() );
        designationDTO.setDesignation( entity.getDesignation() );

        return designationDTO;
    }

    @Override
    public List<Designation> toEntity(List<DesignationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Designation> list = new ArrayList<Designation>( dtoList.size() );
        for ( DesignationDTO designationDTO : dtoList ) {
            list.add( toEntity( designationDTO ) );
        }

        return list;
    }

    @Override
    public List<DesignationDTO> toDto(List<Designation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DesignationDTO> list = new ArrayList<DesignationDTO>( entityList.size() );
        for ( Designation designation : entityList ) {
            list.add( toDto( designation ) );
        }

        return list;
    }
}
