package cloud.cholewa.chat.user.adapters.rest;

import cloud.cholewa.chat.user.ports.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
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
        userService.register(userMapper.toDomain(userRequest));
        return Response.created(getLocation("d")).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid UserRequestDto userRequest) {
        System.out.println("User logging");
        return Response.accepted().build();
    }

    private URI getLocation(String id) {
        return uriInfo.getAbsolutePathBuilder().path(id).build();
    }
}
