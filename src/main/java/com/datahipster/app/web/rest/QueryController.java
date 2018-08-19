package com.datahipster.app.web.rest;

import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.annotation.Timed;
import com.datahipster.app.security.jwt.JWTConfigurer;
import com.datahipster.app.security.jwt.TokenProvider;
import com.datahipster.app.web.rest.vm.LoggerVM;
import com.datahipster.app.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QueryController {

    @GetMapping("/query")
    @Timed
    public List<LoggerVM> runQuery() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return context.getLoggerList()
            .stream()
            .map(LoggerVM::new)
            .collect(Collectors.toList());
    }
}
