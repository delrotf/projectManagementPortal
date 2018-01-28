package com.gtrack.projectmanagementportal.service.impl;

import com.gtrack.projectmanagementportal.service.DesignationService;
import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Designation.
 */
@Service
@Transactional
public class DesignationServiceImpl implements DesignationService{

    private final Logger log = LoggerFactory.getLogger(DesignationServiceImpl.class);

    private final DesignationRepository designationRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * Save a designation.
     *
     * @param designation the entity to save
     * @return the persisted entity
     */
    @Override
    public Designation save(Designation designation) {
        log.debug("Request to save Designation : {}", designation);
        return designationRepository.save(designation);
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Designation> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationRepository.findAll(pageable);
    }

    /**
     * Get one designation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Designation findOne(Long id) {
        log.debug("Request to get Designation : {}", id);
        return designationRepository.findOne(id);
    }

    /**
     * Get one designation by designation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Designation findOneByDesignation(String designation) {
        log.debug("Request to get Designation : {}", designation);
        return designationRepository.findOneByDesignation(designation);
    }

    /**
     * Delete the designation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Designation : {}", id);
        designationRepository.delete(id);
    }
}
