package com.gtrack.projectmanagementportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the UserInfo entity.
 */
public class UserInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String callingName;

    private String imageUrl;

    @Lob
    private byte[] image;
    private String imageContentType;

    private String phone;

    private Long userId;

    private String userLogin;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private Long supervisorId;

    private String supervisorUserLogin;

    private String supervisorUserFirstName;

    private String supervisorUserLastName;

    private String supervisorCallingName;

    private Long designationId;

    private String designationDesignation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCallingName() {
        return callingName;
    }

    public void setCallingName(String callingName) {
        this.callingName = callingName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long userInfoId) {
        this.supervisorId = userInfoId;
    }

    public String getSupervisorUserLogin() {
        return supervisorUserLogin;
    }

    public void setSupervisorUserLogin(String userInfoUserLogin) {
        this.supervisorUserLogin = userInfoUserLogin;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getDesignationDesignation() {
        return designationDesignation;
    }

    public void setDesignationDesignation(String designationDesignation) {
        this.designationDesignation = designationDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if(userInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", callingName='" + getCallingName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }

	public String getSupervisorUserFirstName() {
		return supervisorUserFirstName;
	}

	public void setSupervisorUserFirstName(String supervisorUserFirstName) {
		this.supervisorUserFirstName = supervisorUserFirstName;
	}

	public String getSupervisorUserLastName() {
		return supervisorUserLastName;
	}

	public void setSupervisorUserLastName(String supervisorUserLastName) {
		this.supervisorUserLastName = supervisorUserLastName;
	}

	public String getSupervisorCallingName() {
		return supervisorCallingName;
	}

	public void setSupervisorCallingName(String supervisorCallingName) {
		this.supervisorCallingName = supervisorCallingName;
	}
}
