package fr.mathieu.rueduterroir.security.token.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails userDetails);
}
