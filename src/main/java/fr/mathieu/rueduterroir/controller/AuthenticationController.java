package fr.mathieu.rueduterroir.controller;

import fr.mathieu.rueduterroir.model.dto.authentication.AuthenticateResponseDTO;
import fr.mathieu.rueduterroir.model.dto.authentication.LoginDTO;
import fr.mathieu.rueduterroir.security.token.service.IJwtService;
import fr.mathieu.rueduterroir.utils.LoggerUtil;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticateResponseDTO> signup(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        log.error(loginDTO.toString());
        if (bindingResult.hasErrors()) {
            throw new ValidationException((Throwable) bindingResult);
        }

        UsernamePasswordAuthenticationToken tokenSpring = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getCredential());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(tokenSpring);
        } catch (BadCredentialsException e) {
            LoggerUtil.Error("Authentication failed due to bad credentials : " + e);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);

        AuthenticateResponseDTO authenticateResponseDTO = AuthenticateResponseDTO.builder()
                .email(userDetails.getUsername())
                .token(jwt)
                .build();

        LoggerUtil.Success("User authentication successful");
        return ResponseEntity.ok(authenticateResponseDTO);
    }
}
