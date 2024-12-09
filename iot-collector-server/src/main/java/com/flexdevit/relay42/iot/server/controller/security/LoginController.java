package com.flexdevit.relay42.iot.server.controller.security;

import com.flexdevit.relay42.iot.server.model.dto.LoginRequest;
import com.flexdevit.relay42.iot.server.model.dto.LoginResponse;
import com.flexdevit.relay42.iot.server.service.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        var token = authenticationService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
