package com.gtrack.projectmanagementportal.service.impl;

import com.gtrack.projectmanagementportal.service.DesignationService;
import com.gtrack.projectmanagementportal.domain.Designation;
import com.gtrack.projectmanagementportal.repository.DesignationRepository;
import com.gtrack.projectmanagementportal.service.dto.DesignationDTO;
import com.gtrack.projectmanagementportal.service.mapper.DesignationMapper;
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

    private final DesignationMapper designationMapper;

    public DesignationServiceImpl(DesignationRepository designationRepository, DesignationMapper designationMapper) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
    }

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationDTO save(DesignationDTO designationDTO) {
        log.debug("Request to save Designation : {}", designationDTO);
        Designation designation = designationMapper.toEntity(designationDTO);
        designation = designationRepository.save(designation);
        return designationMapper.toDto(designation);
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DesignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationRepository.findAll(pageable)
            .map(designationMapper::toDto);
    }

    /**
     * Get one designation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DesignationDTO findOne(Long id) {
        log.debug("Request to get Designation : {}", id);
        Designation designation = designationRepository.findOne(id);
        return designationMapper.toDto(designation);
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
