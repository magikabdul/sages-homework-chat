package cloud.cholewa.rest_client.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class ErrorDto {
    @JsonIgnore
    Instant timestamp;
    String description;
}
