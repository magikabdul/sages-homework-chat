package cloud.cholewa.chat.commons.exceptions;

import cloud.cholewa.chat.domain.channel.exceptions.MessageException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MessageExceptionMapper implements ExceptionMapper<MessageException> {

    @Override
    public Response toResponse(MessageException exception) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
    }
}
