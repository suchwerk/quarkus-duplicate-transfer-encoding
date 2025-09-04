package org.acme;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @Inject
    @RestClient
    GreetingClient client;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hello(JsonObject json) {
        return client.getBigData(json);
    }

    @POST
    @Path("delegate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response helloDelegate(JsonObject json) {
        return Response.ok(GreetingGenerator.generateJsonPayload(64)).build();        
    }
}
