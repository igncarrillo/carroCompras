package ar.edu.um.products.service.impl;

import ar.edu.um.products.domain.Distributor;
import ar.edu.um.products.repository.DistributorRepository;
import ar.edu.um.products.service.DistributorService;
import ar.edu.um.products.service.dto.DistributorDTO;
import ar.edu.um.products.service.mapper.DistributorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Distributor}.
 */
@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    private final Logger log = LoggerFactory.getLogger(DistributorServiceImpl.class);

    private final DistributorRepository distributorRepository;

    private final DistributorMapper distributorMapper;

    public DistributorServiceImpl(DistributorRepository distributorRepository, DistributorMapper distributorMapper) {
        this.distributorRepository = distributorRepository;
        this.distributorMapper = distributorMapper;
    }

    @Override
    public DistributorDTO save(DistributorDTO distributorDTO) {
        log.debug("Request to save Distributor : {}", distributorDTO);
        Distributor distributor = distributorMapper.toEntity(distributorDTO);
        distributor = distributorRepository.save(distributor);
        return distributorMapper.toDto(distributor);
    }

    @Override
    public Optional<DistributorDTO> partialUpdate(DistributorDTO distributorDTO) {
        log.debug("Request to partially update Distributor : {}", distributorDTO);

        return distributorRepository
            .findById(distributorDTO.getId())
            .map(existingDistributor -> {
                distributorMapper.partialUpdate(existingDistributor, distributorDTO);

                return existingDistributor;
            })
            .map(distributorRepository::save)
            .map(distributorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistributorDTO> findAll() {
        log.debug("Request to get all Distributors");
        return distributorRepository.findAll().stream().map(distributorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistributorDTO> findOne(Long id) {
        log.debug("Request to get Distributor : {}", id);
        return distributorRepository.findById(id).map(distributorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Distributor : {}", id);
        distributorRepository.deleteById(id);
    }
}
