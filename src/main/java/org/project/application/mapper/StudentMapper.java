package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.StudentDTO;
import org.project.domain.model.Student;

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
