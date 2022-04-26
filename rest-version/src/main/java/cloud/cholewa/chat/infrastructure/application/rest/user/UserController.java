package cloud.cholewa.chat.infrastructure.application.rest.user;

import cloud.cholewa.chat.domain.user.port.in.UserServicePort;
import cloud.cholewa.chat.infrastructure.application.rest.user.dto.UserRequest;
import cloud.cholewa.chat.infrastructure.application.rest.user.dto.UserResponse;
import cloud.cholewa.chat.infrastructure.application.rest.user.dto.UserRestMapper;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("users")
public class UserController {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserRestMapper userRestMapper;

    @Inject
    private UserServicePort userServicePort;

    @POST
    @Path("/register")
    public Response register(@Valid UserRequest userRequest) {
        UserResponse userResponse = userRestMapper.toUserResponseId(
                userServicePort.register(userRestMapper.toDomain(userRequest)));
        return Response.created(getLocation(userResponse.getId())).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserRequest userRequest) {
        UserResponse userResponse = userRestMapper.toUserResponseToken(
                userServicePort.login(userRestMapper.toDomain(userRequest)));
        return Response.ok(userResponse).build();
    }

    private URI getLocation(Long id) {
        return uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
    }
}
