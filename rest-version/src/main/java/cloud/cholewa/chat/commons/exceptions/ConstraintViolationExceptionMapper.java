package cloud.cholewa.chat.commons.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.util.stream.Collectors.joining;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String DESCRIPTION = "Validation exception: ";
    public static final String KEY_VALUE_SEPARATOR = ": ";
    public static final String ERROR_DELIMITER = ", ";

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorResponse(generateDescription(exception)))
                .build();
    }

    private String generateDescription(ConstraintViolationException exception) {
        return DESCRIPTION + exception.getConstraintViolations().stream()
                .map(this::toViolationMessage)
                .collect(joining(ERROR_DELIMITER));
    }

    private String toViolationMessage(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + KEY_VALUE_SEPARATOR + violation.getMessage();
    }
}
