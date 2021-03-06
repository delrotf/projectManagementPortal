package com.gtrack.projectmanagementportal.web.rest.vm;

import com.gtrack.projectmanagementportal.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private String phone;
    
    public String getPhone() {
        return phone;
    }

    private String callingName;
    
    public String getCallingName() {
        return callingName;
    }

    private int designation;
    
    public int getDesignation() {
        return designation;
    }

    private int supervisor;
    
    public int getSupervisor() {
        return supervisor;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
