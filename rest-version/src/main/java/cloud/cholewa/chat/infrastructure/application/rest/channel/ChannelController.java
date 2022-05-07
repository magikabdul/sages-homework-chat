package cloud.cholewa.chat.infrastructure.application.rest.channel;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.port.in.ChannelServicePort;
import cloud.cholewa.chat.infrastructure.application.rest.channel.dto.ChannelCreateRequest;
import cloud.cholewa.chat.infrastructure.application.rest.channel.dto.ChannelRestMapper;
import cloud.cholewa.chat.infrastructure.application.rest.channel.dto.MessagePublishRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
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

    @GET()
    @Path("change/{newChannel}")
    public Response changeChannel(@PathParam("newChannel") String newChannel,
                                  @HeaderParam("token") String token) {
        channelServicePort.changeChannel(newChannel, token);
        return Response.accepted().build();
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
    @Produces({MediaType.APPLICATION_JSON})
    public Response getChannelHistory(@HeaderParam("token") String token) {
        return Response.ok(channelServicePort.getHistory(token)).build();
    }

    @POST
    @Path("/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishMessage(@Valid MessagePublishRequest messagePublishRequest,
                                   @HeaderParam("token") String token) {

        return Response.ok(channelServicePort.publishMessage(messagePublishRequest.getBody(), token)).build();
    }

    @POST
    @Path("/files/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(MultipartFormDataInput input) {
        channelServicePort.saveFile(input);
        return Response.ok().build();
    }

    @GET
    @Path("/files/download")
    @Produces(MediaType.MEDIA_TYPE_WILDCARD)
    public Response downloadFile(@QueryParam("fileName") String fileName) {
        var file = channelServicePort.getFile(fileName);
        String contentDisposition = "attachment; filename=\"" + file.getName() + "\"";

        return Response.ok(file).header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).build();
    }

    private URI getLocation(Long id) {
        return uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
    }
}
