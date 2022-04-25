package cloud.cholewa.chat.commons.exceptions;

import cloud.cholewa.chat.user.domain.UserCredentialException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserCredentialExceptionMapper implements ExceptionMapper<UserCredentialException> {

    @Override
    public Response toResponse(UserCredentialException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
