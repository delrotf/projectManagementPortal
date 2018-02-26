package com.gtrack.projectmanagementportal.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TeamMember entity.
 */
public class TeamMemberDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant updatedTime;

    private Long userInfoId;
    private String userInfoCallingName;
    private byte[] userInfoImage;
    private String userInfoImageContentType;

    private String userInfoUserLogin;
    private String userInfoUserFirstName;
    private String userInfoUserLastName;
    private String userInfoUserEmail;
    private String userInfoPhone;
    private Long userInfoSupervisorId;
    private byte[] userInfoSupervisorImage;
    private String userInfoSupervisorImageContentType;
    private String userInfoSupervisorUserLogin;
    private String userInfoSupervisorUserFirstName;
    private String userInfoSupervisorUserLastName;
    private String userInfoSupervisorCallingName;
    
    private Long teamId;
    private String teamName;
    private String teamCode;
    private Instant teamCreatedDate;
    private byte[] teamImage;
    private String teamImageContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getUserInfoUserLogin() {
        return userInfoUserLogin;
    }

    public void setUserInfoUserLogin(String userInfoUserLogin) {
        this.userInfoUserLogin = userInfoUserLogin;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamMemberDTO teamMemberDTO = (TeamMemberDTO) o;
        if(teamMemberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamMemberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamMemberDTO{" +
            "id=" + getId() +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }

	public String getUserInfoCallingName() {
		return userInfoCallingName;
	}

	public void setUserInfoCallingName(String userInfoCallingName) {
		this.userInfoCallingName = userInfoCallingName;
	}

	public String getUserInfoUserFirstName() {
		return userInfoUserFirstName;
	}

	public void setUserInfoUserFirstName(String userInfoUserFirstName) {
		this.userInfoUserFirstName = userInfoUserFirstName;
	}

	public String getUserInfoUserLastName() {
		return userInfoUserLastName;
	}

	public void setUserInfoUserLastName(String userInfoUserLastName) {
		this.userInfoUserLastName = userInfoUserLastName;
	}

	public String getUserInfoUserEmail() {
		return userInfoUserEmail;
	}

	public void setUserInfoUserEmail(String userInfoUserEmail) {
		this.userInfoUserEmail = userInfoUserEmail;
	}

	public String getUserInfoPhone() {
		return userInfoPhone;
	}

	public void setUserInfoPhone(String userInfoPhone) {
		this.userInfoPhone = userInfoPhone;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public byte[] getUserInfoImage() {
		return userInfoImage;
	}

	public void setUserInfoImage(byte[] userInfoImage) {
		this.userInfoImage = userInfoImage;
	}

	public String getUserInfoImageContentType() {
		return userInfoImageContentType;
	}

	public void setUserInfoImageContentType(String userInfoImageContentType) {
		this.userInfoImageContentType = userInfoImageContentType;
	}

	public byte[] getTeamImage() {
		return teamImage;
	}

	public void setTeamImage(byte[] teamImage) {
		this.teamImage = teamImage;
	}

	public String getTeamImageContentType() {
		return teamImageContentType;
	}

	public void setTeamImageContentType(String teamImageContentType) {
		this.teamImageContentType = teamImageContentType;
	}

	public Long getUserInfoSupervisorId() {
		return userInfoSupervisorId;
	}

	public void setUserInfoSupervisorId(Long userInfoSupervisorId) {
		this.userInfoSupervisorId = userInfoSupervisorId;
	}

	public String getUserInfoSupervisorUserLogin() {
		return userInfoSupervisorUserLogin;
	}

	public void setUserInfoSupervisorUserLogin(String userInfoSupervisorUserLogin) {
		this.userInfoSupervisorUserLogin = userInfoSupervisorUserLogin;
	}

	public String getUserInfoSupervisorUserFirstName() {
		return userInfoSupervisorUserFirstName;
	}

	public void setUserInfoSupervisorUserFirstName(String userInfoSupervisorUserFirstName) {
		this.userInfoSupervisorUserFirstName = userInfoSupervisorUserFirstName;
	}

	public String getUserInfoSupervisorUserLastName() {
		return userInfoSupervisorUserLastName;
	}

	public void setUserInfoSupervisorUserLastName(String userInfoSupervisorUserLastName) {
		this.userInfoSupervisorUserLastName = userInfoSupervisorUserLastName;
	}

	public String getUserInfoSupervisorCallingName() {
		return userInfoSupervisorCallingName;
	}

	public void setUserInfoSupervisorCallingName(String userInfoSupervisorCallingName) {
		this.userInfoSupervisorCallingName = userInfoSupervisorCallingName;
	}

	public Instant getTeamCreatedDate() {
		return teamCreatedDate;
	}

	public void setTeamCreatedDate(Instant teamCreatedDate) {
		this.teamCreatedDate = teamCreatedDate;
	}

	public byte[] getUserInfoSupervisorImage() {
		return userInfoSupervisorImage;
	}

	public void setUserInfoSupervisorImage(byte[] userInfoSupervisorImage) {
		this.userInfoSupervisorImage = userInfoSupervisorImage;
	}

	public String getUserInfoSupervisorImageContentType() {
		return userInfoSupervisorImageContentType;
	}

	public void setUserInfoSupervisorImageContentType(String userInfoSupervisorImageContentType) {
		this.userInfoSupervisorImageContentType = userInfoSupervisorImageContentType;
	}
}
