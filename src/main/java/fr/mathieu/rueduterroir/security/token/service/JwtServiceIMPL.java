package fr.mathieu.rueduterroir.security.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtServiceIMPL implements IJwtService {
    private final JwtEncoder jwtEncoder;

    @Autowired
    public JwtServiceIMPL(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .claim("auth", authorities)
                .issuer("self")
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);

        return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
}
