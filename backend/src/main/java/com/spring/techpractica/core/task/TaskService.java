package com.spring.techpractica.core.task;

import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    public void validateSessionOwnership(Session session, UUID ownerId) {
        if (!session.isOwner(ownerId)) {
            throw new UnauthorizedActionException("User must be the session owner to perform this action.");
        }
    }

    public void validateSessionParticipant(Session session, UUID userId) {
        boolean exists = session.getMembers()
                .stream()
                .anyMatch(member -> member.getUser().getId().equals(userId));

        if (!exists) {
            throw new UnauthorizedActionException("User is not part of this session.");
        }
    }

    public List<Field> validateAndGetFields(Set<String> tagNames) {
        List<Field> fields = fieldRepository.findAllByNames(tagNames);
        if (fields.size() != tagNames.size()) {
            throw new ResourcesNotFoundException(tagNames.stream().toList());
        }
        return fields;
    }

    public List<User> validateAndGetAssignees(Set<UUID> assigneeIds) {
        List<User> users = userRepository.findAllByIds(assigneeIds);
        if (users.size() != assigneeIds.size()) {
            throw new ResourcesNotFoundException("Some assignees were not found.");
        }
        return users;
    }
}
