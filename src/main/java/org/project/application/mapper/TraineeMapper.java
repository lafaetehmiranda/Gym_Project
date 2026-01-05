package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.TraineeDTO;
import org.project.domain.model.Trainee;

/**
 * Mapper for converting between Trainee domain model and DTO.
 */
@ApplicationScoped
public class TraineeMapper {

    public TraineeDTO toDTO(Trainee trainee) {
        return new TraineeDTO(
                trainee.getId(),
                trainee.getName(),
                trainee.getCreatedAt(),
                trainee.getUpdatedAt(),
                trainee.isActive(),
                trainee.getStudentId(),
                trainee.getDescription(),
                trainee.getValue(),
                trainee.getAvailability());
    }
}
