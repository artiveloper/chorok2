package io.chorok2.modules.account.controller;

import io.chorok2.modules.account.dto.JwtAuthenticationResponse;
import io.chorok2.modules.account.dto.SignInRequest;
import io.chorok2.modules.account.dto.SignUpRequest;
import io.chorok2.modules.account.service.AccountService;
import io.chorok2.modules.account.validator.SignUpFormValidation;
import io.chorok2.modules.error.ErrorResponse;
import io.chorok2.modules.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final SignUpFormValidation signUpFormValidation;

    @InitBinder("signUpRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidation);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveAccount(@RequestBody @Valid SignUpRequest signUpRequest, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(ErrorResponse.of("InvalidInputValue.code", "InvalidInputValue.message"), HttpStatus.valueOf(""));
        }

        return new ResponseEntity<>(accountService.createAccount(signUpRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

}
