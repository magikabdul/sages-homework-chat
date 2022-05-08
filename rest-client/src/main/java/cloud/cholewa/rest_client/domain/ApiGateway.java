package cloud.cholewa.rest_client.domain;

import cloud.cholewa.rest_client.domain.dto.MessagePublishDto;
import cloud.cholewa.rest_client.domain.ui.ApiMethod;
import jakarta.ws.rs.core.Response;

public class ApiGateway {

    private final ApiMethod method = new ApiMethod();

    public Response doResister(Object requestBody) {
        return method.doPost("users/register", requestBody);
    }

    public Response doLogin(Object requestBody) {
        return method.doPost("users/login", requestBody);
    }

    public Response getHistory(String token) {
        return method.doGetAuthorized("channels/history", token);
    }

    public Response getLastChannelMessage(String token) {
        return method.doGetAuthorized("channels/messages", token);
    }

    public Response sendMessage(MessagePublishDto messagePublishDto, String token) {
        return method.doPostAuthorized("channels/messages", messagePublishDto, token);
    }
}
