package com.gtrack.projectmanagementportal.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Team entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private String imageUrl;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Boolean processOrder;

    private Boolean processExternalTask;

    @NotNull
    private Boolean active;

    @NotNull
    private Instant createdDate;

    private Long teamHeadId;

    private String teamHeadUserLogin;
    private String teamHeadCallingName;
    private String teamHeadUserFirstName;
    private String teamHeadUserLastName;
    private byte[] teamHeadImage;
    private String teamHeadImageContentType;
    private String teamHeadUserEmail;
    private String teamHeadPhone;

    private Long teamHeadSupervisorId;
    private String teamHeadSupervisorUserLogin;
    private String teamHeadSupervisorUserFirstName;
    private String teamHeadSupervisorUserLastName;
    private String teamHeadSupervisorCallingName;
    private byte[] teamHeadSupervisorImage;
    private String teamHeadSupervisorImageContentType;


    public String getTeamHeadSupervisorCallingName() {
		return teamHeadSupervisorCallingName;
	}

	public void setTeamHeadSupervisorCallingName(String teamHeadSupervisorCallingName) {
		this.teamHeadSupervisorCallingName = teamHeadSupervisorCallingName;
	}

	public String getTeamHeadSupervisorUserLastName() {
		return teamHeadSupervisorUserLastName;
	}

	public void setTeamHeadSupervisorUserLastName(String teamHeadSupervisorUserLastName) {
		this.teamHeadSupervisorUserLastName = teamHeadSupervisorUserLastName;
	}

	public String getTeamHeadSupervisorUserFirstName() {
		return teamHeadSupervisorUserFirstName;
	}

	public void setTeamHeadSupervisorUserFirstName(String teamHeadSupervisorUserFirstName) {
		this.teamHeadSupervisorUserFirstName = teamHeadSupervisorUserFirstName;
	}

	public String getTeamHeadSupervisorUserLogin() {
		return teamHeadSupervisorUserLogin;
	}

	public void setTeamHeadSupervisorUserLogin(String teamHeadSupervisorUserLogin) {
		this.teamHeadSupervisorUserLogin = teamHeadSupervisorUserLogin;
	}

	public Long getTeamHeadSupervisorId() {
		return teamHeadSupervisorId;
	}

	public void setTeamHeadSupervisorId(Long teamHeadSupervisorId) {
		this.teamHeadSupervisorId = teamHeadSupervisorId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean isProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(Boolean processOrder) {
        this.processOrder = processOrder;
    }

    public Boolean isProcessExternalTask() {
        return processExternalTask;
    }

    public void setProcessExternalTask(Boolean processExternalTask) {
        this.processExternalTask = processExternalTask;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getTeamHeadId() {
        return teamHeadId;
    }

    public void setTeamHeadId(Long userInfoId) {
        this.teamHeadId = userInfoId;
    }

    public String getTeamHeadUserLogin() {
        return teamHeadUserLogin;
    }

    public void setTeamHeadUserLogin(String userInfoUserLogin) {
        this.teamHeadUserLogin = userInfoUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if(teamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", processOrder='" + isProcessOrder() + "'" +
            ", processExternalTask='" + isProcessExternalTask() + "'" +
            ", active='" + isActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }

	public String getTeamHeadCallingName() {
		return teamHeadCallingName;
	}

	public void setTeamHeadCallingName(String teamHeadCallingName) {
		this.teamHeadCallingName = teamHeadCallingName;
	}

	public String getTeamHeadUserFirstName() {
		return teamHeadUserFirstName;
	}

	public void setTeamHeadUserFirstName(String teamHeadUserFirstName) {
		this.teamHeadUserFirstName = teamHeadUserFirstName;
	}

	public String getTeamHeadUserLastName() {
		return teamHeadUserLastName;
	}

	public void setTeamHeadUserLastName(String teamHeadUserLastName) {
		this.teamHeadUserLastName = teamHeadUserLastName;
	}

	public byte[] getTeamHeadImage() {
		return teamHeadImage;
	}

	public void setTeamHeadImage(byte[] teamHeadImage) {
		this.teamHeadImage = teamHeadImage;
	}

	public String getTeamHeadImageContentType() {
		return teamHeadImageContentType;
	}

	public void setTeamHeadImageContentType(String teamHeadImageContentType) {
		this.teamHeadImageContentType = teamHeadImageContentType;
	}

	public Boolean getProcessOrder() {
		return processOrder;
	}

	public Boolean getProcessExternalTask() {
		return processExternalTask;
	}

	public Boolean getActive() {
		return active;
	}

	public byte[] getTeamHeadSupervisorImage() {
		return teamHeadSupervisorImage;
	}

	public void setTeamHeadSupervisorImage(byte[] teamHeadSupervisorImage) {
		this.teamHeadSupervisorImage = teamHeadSupervisorImage;
	}

	public String getTeamHeadSupervisorImageContentType() {
		return teamHeadSupervisorImageContentType;
	}

	public void setTeamHeadSupervisorImageContentType(String teamHeadSupervisorImageContentType) {
		this.teamHeadSupervisorImageContentType = teamHeadSupervisorImageContentType;
	}

	public String getTeamHeadUserEmail() {
		return teamHeadUserEmail;
	}

	public void setTeamHeadUserEmail(String teamHeadUserEmail) {
		this.teamHeadUserEmail = teamHeadUserEmail;
	}

	public String getTeamHeadPhone() {
		return teamHeadPhone;
	}

	public void setTeamHeadPhone(String teamHeadPhone) {
		this.teamHeadPhone = teamHeadPhone;
	}
}
