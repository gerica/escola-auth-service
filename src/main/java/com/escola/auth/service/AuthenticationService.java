package com.escola.auth.service;

import com.escola.auth.model.request.AuthenticationRequest;
import com.escola.auth.model.response.AuthenticationResponse;
import com.escola.auth.repository.UserRepository;
import com.escola.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository; // Assuming you have a User entity and repository
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // If we get here, the user is authenticated
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(); // Or handle exception appropriately
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .roles(user.getRoles())
                .build();
    }
}