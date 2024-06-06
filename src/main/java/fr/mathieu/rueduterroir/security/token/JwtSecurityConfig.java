package fr.mathieu.rueduterroir.security.token;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class JwtSecurityConfig {
    private String jwtSecret;

    @Bean
    JwtEncoder jwtEncoder(){
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                jwtSecret.getBytes(),
                0,
                this.jwtSecret.getBytes().length,
                "RSA"
                );
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @PostConstruct
    void jwtSecret(){
        this.jwtSecret = generateJwtSecret();
    }

    private String generateJwtSecret(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(512, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            return bytesToHex(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errir generation JWT secret", e);
        }
    }

    private String bytesToHex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for (byte b : bytes){
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
