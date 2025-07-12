package com.escola.auth.controller;

import com.escola.auth.model.request.AuthenticationRequest;
import com.escola.auth.model.response.AuthenticationResponse;
import com.escola.auth.service.AuthenticationService;
import io.leangen.graphql.annotations.GraphQLMutation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @GraphQLMutation(name = "authenticate")
    @MutationMapping
    public AuthenticationResponse authenticate(@Argument AuthenticationRequest request) {
        log.info("AuthenticationController.authenticate");
        return service.authenticate(request);
    }
}