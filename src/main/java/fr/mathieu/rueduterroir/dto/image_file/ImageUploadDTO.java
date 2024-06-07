package fr.mathieu.rueduterroir.dto.image_file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageUploadDTO {

    @JsonProperty("data")
    private byte[] data;
    @JsonProperty("name")
    private String filename;
    @JsonProperty("type")
    private String type;
    @JsonProperty("size")
    private Integer size;

}
