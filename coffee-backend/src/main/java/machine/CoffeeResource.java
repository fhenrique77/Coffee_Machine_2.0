package machine;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

@Path("/api/machine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeResource {

    @Inject
    CoffeeMachineService service;

    @GET
    @Path("/remaining")
    public Map<String, Integer> remaining() {
        return service.remaining();
    }

    @POST
    @Path("/buy/{type}")
    public Map<String, Object> buy(@PathParam("type") int type) {
        return result(service.buy(type));
    }

    @POST
    @Path("/fill")
    public Map<String, Object> fill(FillRequest body) {
        return result(service.fill(body));
    }

    @POST
    @Path("/take")
    public Map<String, Object> take() {
        return result(service.take());
    }

    private Map<String, Object> result(String message) {
        return Map.of("message", message, "stock", service.remaining());
    }
}
