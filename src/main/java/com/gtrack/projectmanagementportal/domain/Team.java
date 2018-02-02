package com.gtrack.projectmanagementportal.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "process_order")
    private Boolean processOrder;

    @Column(name = "process_external_task")
    private Boolean processExternalTask;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ManyToOne(optional = false)
    @NotNull
    private UserInfo teamHead;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Team code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Team imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isProcessOrder() {
        return processOrder;
    }

    public Team processOrder(Boolean processOrder) {
        this.processOrder = processOrder;
        return this;
    }

    public void setProcessOrder(Boolean processOrder) {
        this.processOrder = processOrder;
    }

    public Boolean isProcessExternalTask() {
        return processExternalTask;
    }

    public Team processExternalTask(Boolean processExternalTask) {
        this.processExternalTask = processExternalTask;
        return this;
    }

    public void setProcessExternalTask(Boolean processExternalTask) {
        this.processExternalTask = processExternalTask;
    }

    public Boolean isActive() {
        return active;
    }

    public Team active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Team createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public UserInfo getTeamHead() {
        return teamHead;
    }

    public Team teamHead(UserInfo userInfo) {
        this.teamHead = userInfo;
        return this;
    }

    public void setTeamHead(UserInfo userInfo) {
        this.teamHead = userInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
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
