package cloud.cholewa.chat.commons.exceptions;

import cloud.cholewa.chat.user.domain.UserExistsException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExistsExceptionMapper implements ExceptionMapper<UserExistsException> {

    @Override
    public Response toResponse(UserExistsException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
