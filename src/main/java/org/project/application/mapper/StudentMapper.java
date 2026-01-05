package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.StudentDTO;
import org.project.domain.model.Student;

/**
 * Mapper for converting between Student domain model and DTO.
 */
@ApplicationScoped
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getCreatedAt(),
                student.getUpdatedAt(),
                student.isActive());
    }
}
