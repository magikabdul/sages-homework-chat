package cloud.cholewa.chat.commons.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.FileNotFoundException;

@Provider
public class FileNotFoundExceptionMapper implements ExceptionMapper<FileNotFoundException> {

    @Override
    public Response toResponse(FileNotFoundException exception) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
    }
}
