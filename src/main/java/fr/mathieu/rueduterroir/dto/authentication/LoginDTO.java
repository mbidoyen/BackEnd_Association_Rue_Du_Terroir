package fr.mathieu.rueduterroir.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email address.")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @JsonProperty("password")
    private String credential;

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", credential='" + credential + '\'' +
                '}';
    }
}
