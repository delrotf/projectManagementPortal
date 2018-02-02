package com.gtrack.projectmanagementportal.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Team entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String code;

    private String imageUrl;

    private Boolean processOrder;

    private Boolean processExternalTask;

    @NotNull
    private Boolean active;

    @NotNull
    private Instant createdDate;

    private Long teamHeadId;

    private String teamHeadUserLogin;

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
            ", processOrder='" + isProcessOrder() + "'" +
            ", processExternalTask='" + isProcessExternalTask() + "'" +
            ", active='" + isActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
