package org.example.recipee.server;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.example.recipee.services.RecipeeService;
import org.example.recipee.vo.RecipeeVO;
import org.example.recipee.vo.RecipeeWithNameVO;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Path("/recipees")
public class RecipeeResource {

    @Autowired
    private RecipeeService recipeeService;

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<RecipeeWithNameVO> getRecipees() {
        return recipeeService.getRecipees();
    }
    @GET
    @Path("/{name}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public RecipeeVO getRecipee(@PathParam("name") String name) {
        return recipeeService.findByName(name);
    }

    @PUT
    @Path("/{name}")
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response updateRecipee(RecipeeVO recipeeVO, @PathParam("name") String name) {
        this.recipeeService.updateRecipee(name, recipeeVO);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @DELETE
    @Path("/{name}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response deleteRecipee(@PathParam("name") String name) {
        this.recipeeService.delete(name);
        return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response addRecipee(RecipeeWithNameVO recipee, @Context UriInfo uriInfo) throws URISyntaxException {
        this.recipeeService.add(recipee);
        String absolutePath = uriInfo.getAbsolutePath().toString();
        if (!absolutePath.endsWith("/")){
            absolutePath += "/";
        }
        return Response.status(Response.Status.CREATED.getStatusCode()).header("Location", URLEncoder.encode(String.format("%s%s", absolutePath, recipee.getName()), StandardCharsets.ISO_8859_1)).build();
    }
}
