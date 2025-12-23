package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.application.dto.CreateTraineeRequest;
import org.project.application.dto.TraineeDTO;
import org.project.application.dto.UpdateTraineeRequest;
import org.project.application.mapper.TraineeMapper;
import org.project.domain.model.Trainee;
import org.project.domain.repository.TraineeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;

    @Inject
    public TraineeService(TraineeRepository traineeRepository, TraineeMapper traineeMapper) {
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
    }

    @Transactional
    public TraineeDTO create(CreateTraineeRequest request) {
        Trainee trainee = Trainee.create(
                request.name(),
                request.studentId(),
                request.description(),
                request.value(),
                request.disponibilidade());
        Trainee savedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDTO(savedTrainee);
    }

    public Optional<TraineeDTO> findById(UUID id) {
        return traineeRepository.findDomainById(id)
                .map(traineeMapper::toDTO);
    }

    public List<TraineeDTO> findAll() {
        return traineeRepository.findAllDomain().stream()
                .map(traineeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TraineeDTO> findAllActive() {
        return traineeRepository.findAllActive().stream()
                .map(traineeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TraineeDTO> findByStudentId(UUID studentId) {
        return traineeRepository.findByStudentId(studentId).stream()
                .map(traineeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<TraineeDTO> update(UUID id, UpdateTraineeRequest request) {
        return traineeRepository.findDomainById(id)
                .map(trainee -> trainee.updateDetails(
                        request.name(),
                        request.description(),
                        request.value(),
                        request.disponibilidade()))
                .map(traineeRepository::save)
                .map(traineeMapper::toDTO);
    }

    @Transactional
    public Optional<TraineeDTO> deactivate(UUID id) {
        return traineeRepository.findDomainById(id)
                .map(Trainee::deactivate)
                .map(traineeRepository::save)
                .map(traineeMapper::toDTO);
    }

    @Transactional
    public Optional<TraineeDTO> activate(UUID id) {
        return traineeRepository.findDomainById(id)
                .map(Trainee::activate)
                .map(traineeRepository::save)
                .map(traineeMapper::toDTO);
    }

    @Transactional
    public void deleteById(UUID id) {
        traineeRepository.deleteById(id);
    }
}
