package com.gtrack.projectmanagementportal.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Designation entity.
 */
public class DesignationDTO implements Serializable {

    private Long id;

    private String designation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationDTO designationDTO = (DesignationDTO) o;
        if(designationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
