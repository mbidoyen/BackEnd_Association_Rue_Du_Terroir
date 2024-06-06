package fr.mathieu.rueduterroir.model.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("entry")
    private Integer entryNumber;

    @JsonProperty("floor")
    private Integer floorNumber;

    @JsonProperty("apartment")
    private Integer apartmentNumber;
}
