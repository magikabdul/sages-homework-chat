package cloud.cholewa.chat.infrastructure.application.rest.chat;

import cloud.cholewa.chat.domain.chat.port.in.ChatServicePort;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("chat")
public class ChatController {

    @Inject
    private ChatServicePort chatServicePort;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFullInfo() {
        chatServicePort.getFullInfo().forEach(System.out::println);
        return Response.ok(chatServicePort.getFullInfo()).build();
    }
}
