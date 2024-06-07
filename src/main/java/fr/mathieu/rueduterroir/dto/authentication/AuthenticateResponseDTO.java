package fr.mathieu.rueduterroir.dto.authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AuthenticateResponseDTO {
    private String email;
    private String token;
}
