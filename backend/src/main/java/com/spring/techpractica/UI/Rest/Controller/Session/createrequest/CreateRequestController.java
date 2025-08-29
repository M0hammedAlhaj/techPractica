package com.spring.techpractica.UI.Rest.Controller.Session.createrequest;

import com.spring.techpractica.Application.Session.createrequest.CreateRequestCommand;
import com.spring.techpractica.Application.Session.createrequest.CreateRequestUseCase;
import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Resources.Request.RequestResources;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/requirements")
@AllArgsConstructor
public class CreateRequestController {

    private final CreateRequestUseCase useCase;

    @PostMapping("/{requirementId}/requests")
    public ResponseEntity<?> invoke(@PathVariable UUID requirementId,
                                    @AuthenticationPrincipal UserAuthentication userAuthentication,
                                    @RequestBody CreateRequestDto createRequestDto) {

        Request request = useCase.execute(new CreateRequestCommand(userAuthentication.getUserId(),
                requirementId,
                createRequestDto.getBrief()));

        StandardSuccessResponse.<RequestResources>builder()
                .data(new RequestResources(request))
                .message("Request created Successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.ok(request);
    }
}
