package cloud.cholewa.chat.user.adapters.rest;

import cloud.cholewa.chat.user.domain.User;
import cloud.cholewa.chat.user.ports.UserService;

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
    private UserMapper userMapper;

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    public Response register(@Valid UserRequestDto userRequest) {
        User register = userService.register(userMapper.toDomain(userRequest));
        System.out.println(register);
        return Response.created(getLocation(userRequest.getNick())).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UserRequestDto userRequest) {
        String token = userService.login(userMapper.toDomain(userRequest));
        return Response.accepted().entity(new UserResponse(token)).build();
    }

    private URI getLocation(String id) {
        return uriInfo.getAbsolutePathBuilder().path(id).build();
    }
}
