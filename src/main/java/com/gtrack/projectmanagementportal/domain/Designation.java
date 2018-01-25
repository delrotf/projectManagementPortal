package com.gtrack.projectmanagementportal.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Designation.
 */
@Entity
@Table(name = "designation")
public class Designation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "designation")
    private String designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public Designation designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
        Designation designation = (Designation) o;
        if (designation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Designation{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
