package cloud.cholewa.rest_client.domain.ui;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

public class ApiMethod {

    private final ResteasyClient restClient = new ResteasyClientBuilderImpl().build();
    private final String SERVER_URL = "http://localhost:8080/chat/";

    public Response doGet(String requestPath, String token) {
        return restClient.target(SERVER_URL + requestPath)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header("token", token)
                .get();
    }

    public Response doPost(String requestPath, Object requestBody) {
        return restClient.target(SERVER_URL + requestPath)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestBody, MediaType.APPLICATION_JSON));
    }

    public Response doDelete(String requestPath) {
        return null;
    }
}
