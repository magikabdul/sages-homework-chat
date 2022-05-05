package cloud.cholewa.chat.commons.exceptions;

import cloud.cholewa.chat.domain.user.exceptions.ChannelException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_INVALID_TOKEN;

@Provider
public class UserExceptionMapper implements ExceptionMapper<ChannelException> {

    @Override
    public Response toResponse(ChannelException exception) {
        if (exception.getMessage().equals(USER_INVALID_TOKEN)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
