package com.gtrack.projectmanagementportal.service;

import com.gtrack.projectmanagementportal.service.dto.DesignationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Designation.
 */
public interface DesignationService {

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save
     * @return the persisted entity
     */
    DesignationDTO save(DesignationDTO designationDTO);

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DesignationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" designation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DesignationDTO findOne(Long id);

    /**
     * Delete the "id" designation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
