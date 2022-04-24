package cloud.cholewa.chat.commons.exceptions;

import lombok.Value;

import java.time.Instant;

@Value
public class ErrorResponse {

    Instant timestamp = Instant.now();
    String description;
}
