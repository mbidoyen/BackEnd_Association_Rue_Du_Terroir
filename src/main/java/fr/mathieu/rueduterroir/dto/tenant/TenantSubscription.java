package fr.mathieu.rueduterroir.dto.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.mathieu.rueduterroir.dto.image_file.ImageUploadDTO;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TenantSubscription {

    @NotNull(message = "Civility is required")
    @NotBlank(message = "Civility is required")
    @JsonProperty("civility")
    private String civility;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters.")
    @JsonProperty("firstname")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters.")
    @JsonProperty("lastname")
    private String lastName;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email address.")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    private String password;

    @NotNull(message = "Occupant is required")
    @Max(value = 10, message = "Maximum number of occupants is 10")
    @Min(value = 1, message = "Minimum number of occupants is 10")
    @JsonProperty("residents")
    private Integer occupants;

    @Size(max = 10, message = "Phone number cannot be more than 10 characters.")
    @JsonProperty("phone")
    private String phone;

    @JsonProperty("garage_number")
    @Min(value = 1, message = "The garage number is invalid")
    private Integer garage;

    @NotNull(message = "Tenant number is required.")
    @NotBlank(message = "Tenant number is required.")
    @Length(min = 10, max = 10, message = "The tenant number must contain 10 characters")
    @JsonProperty("tenant_number")
    private String tenantNumber;

    @NotNull(message = "The address has not been selected")
    @JsonProperty("address_id")
    @Min(value = 1, message = "This address is unknown")
    @Max(value = 252, message = "This address is unknown")
    private Integer addressId;

    @JsonProperty("profil_picture")
    private ImageUploadDTO imageFileDto;
}
