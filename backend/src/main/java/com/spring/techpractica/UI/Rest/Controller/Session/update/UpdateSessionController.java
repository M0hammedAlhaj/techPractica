package com.spring.techpractica.UI.Rest.Controller.Session.update;

import com.spring.techpractica.Application.Session.CreateSession.CreateSessionCommand;
import com.spring.techpractica.Application.Session.update.UpdateSessionCommand;
import com.spring.techpractica.Application.Session.update.UpdateSessionUseCase;
import com.spring.techpractica.Core.Requirement.Model.RequirementRequest;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request.CreateSessionRequest;
import com.spring.techpractica.UI.Rest.Resources.Session.SessionResources;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class UpdateSessionController {

    private final UpdateSessionUseCase updateSessionUseCase;

    @PostMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(@RequestBody @Valid CreateSessionRequest request,
                                           @AuthenticationPrincipal UserAuthentication userAuthentication,
                                           @PathVariable UUID sessionId) {

        Session session = updateSessionUseCase.execute(new UpdateSessionCommand(
                userAuthentication.getUserId(),
                sessionId,
                request.name(),
                request.description(),
                request.isPrivate(),
                request.system(),
                request.requirements().stream().map(
                        requirementRequest -> new RequirementRequest(requirementRequest.getFieldName()
                                , requirementRequest.getTechnologies())
                ).toList()
        ));

        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Session updated successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
