package com.gtrack.projectmanagementportal.service;

import com.gtrack.projectmanagementportal.domain.Designation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Designation.
 */
public interface DesignationService {

    /**
     * Save a designation.
     *
     * @param designation the entity to save
     * @return the persisted entity
     */
    Designation save(Designation designation);

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Designation> findAll(Pageable pageable);

    /**
     * Get the "id" designation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Designation findOne(Long id);

    /**
     * Delete the "id" designation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	Designation findOneByDesignation(String designation);
}
