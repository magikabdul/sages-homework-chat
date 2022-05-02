package cloud.cholewa.chat.infrastructure.application.rest.channel;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.port.in.ChannelServicePort;
import cloud.cholewa.chat.infrastructure.application.rest.channel.dto.ChannelCreateRequest;
import cloud.cholewa.chat.infrastructure.application.rest.channel.dto.ChannelRestMapper;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/channels")
public class ChannelController {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ChannelRestMapper channelRestMapper;

    @Inject
    private ChannelServicePort channelServicePort;

    @POST
    public Response createChannel(@Valid ChannelCreateRequest channelCreateRequest,
                                  @HeaderParam("token") String token) {
        Channel channel = channelServicePort.createChannel(
                channelRestMapper.toDomainFromCreateRequest(channelCreateRequest), token);
        return Response.created(getLocation(channel.getId())).build();
    }

    @GET
    public Response changeChannel() {
        return null;
    }

    @GET
    @Path("/participants/add")
    public Response addParticipant() {
        return null;
    }

    @GET
    @Path("/participants/remove")
    public Response removeParticipant() {
        return null;
    }

    @GET
    @Path("/history")
    public Response getChannelHistory() {
        return null;
    }

    @POST
    @Path("/messages")
    public Response postMessage() {
        return null;
    }


    //TODO - find member by nick
    //TODO - getAllMembers
    //TODO - getActiveUsers
    private URI getLocation(Long id) {
        return uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
    }
}
