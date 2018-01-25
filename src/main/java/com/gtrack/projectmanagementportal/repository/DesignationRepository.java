package com.gtrack.projectmanagementportal.repository;

import com.gtrack.projectmanagementportal.domain.Designation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Designation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

}
