package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.application.dto.CreateStudentRequest;
import org.project.application.dto.StudentDTO;
import org.project.application.dto.UpdateStudentRequest;
import org.project.application.mapper.StudentMapper;
import org.project.domain.model.Student;
import org.project.domain.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for handling student management business logic.
 */
@ApplicationScoped
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Inject
    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public StudentDTO create(CreateStudentRequest request) {
        Student student = Student.create(request.name());
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    public Optional<StudentDTO> findById(UUID id) {
        return studentRepository.findDomainById(id)
                .map(studentMapper::toDTO);
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAllDomain().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> findAllActive() {
        return studentRepository.findAllActive().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<StudentDTO> update(UUID id, UpdateStudentRequest request) {
        return studentRepository.findDomainById(id)
                .map(student -> student.updateName(request.name()))
                .map(studentRepository::save)
                .map(studentMapper::toDTO);
    }

    @Transactional
    public Optional<StudentDTO> deactivate(UUID id) {
        return studentRepository.findDomainById(id)
                .map(Student::deactivate)
                .map(studentRepository::save)
                .map(studentMapper::toDTO);
    }

    @Transactional
    public Optional<StudentDTO> activate(UUID id) {
        return studentRepository.findDomainById(id)
                .map(Student::activate)
                .map(studentRepository::save)
                .map(studentMapper::toDTO);
    }

    @Transactional
    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }
}
