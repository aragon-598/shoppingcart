package com.store.shoppingcart.security.entrypoint.rest.controller;

import com.store.shoppingcart.common.dto.ApiResponse;
import com.store.shoppingcart.security.application.dto.AuthenticationCommand;
import com.store.shoppingcart.security.application.dto.RegisterUserCommand;
import com.store.shoppingcart.security.domain.model.AuthToken;
import com.store.shoppingcart.security.domain.model.User;
import com.store.shoppingcart.security.domain.model.UserId;
import com.store.shoppingcart.security.domain.port.in.AuthenticateUserUseCase;
import com.store.shoppingcart.security.domain.port.in.RegisterUserUseCase;
import com.store.shoppingcart.security.entrypoint.rest.dto.AuthResponse;
import com.store.shoppingcart.security.entrypoint.rest.dto.LoginRequest;
import com.store.shoppingcart.security.entrypoint.rest.dto.RegisterRequest;
import com.store.shoppingcart.security.entrypoint.rest.dto.RegisterResponse;
import com.store.shoppingcart.security.entrypoint.rest.dto.UserInfoDto;
import com.store.shoppingcart.security.entrypoint.rest.mapper.AuthDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthDtoMapper mapper;
    
    public AuthController(RegisterUserUseCase registerUserUseCase,
                         AuthenticateUserUseCase authenticateUserUseCase,
                         AuthDtoMapper mapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.mapper = mapper;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUserCommand command = mapper.toCommand(request);
        User user = registerUserUseCase.execute(command);
        
        UserInfoDto userInfo = new UserInfoDto(
            user.getId().value().toString(),
            user.getEmail().value(),
            user.getFirstName(),
            user.getLastName()
        );
        
        RegisterResponse response = new RegisterResponse(user.getId().value().toString(), userInfo);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED.value(), response));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthenticationCommand command = mapper.toCommand(request);
        AuthToken token = authenticateUserUseCase.execute(command);
        
        UserInfoDto userInfo = new UserInfoDto(
            token.user().getId().value().toString(),
            token.user().getEmail().value(),
            token.user().getFirstName(),
            token.user().getLastName()
        );
        
        AuthResponse response = new AuthResponse(
            token.value(),
            token.expiresAt(),
            userInfo
        );
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), response));
    }
}
