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
import org.example.recipee.services.IngredientService;
import org.example.recipee.vo.IngredientRecipeeVO;
import org.example.recipee.vo.QuantityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Path("/ingredients")
public class IngredientResource {

    @Autowired
    private IngredientService ingredientService;

    @GET()
    @Path("/recipee/{recipeeName}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<IngredientRecipeeVO> getIngredients(@PathParam("recipeeName") String recipeeName) {
        return ingredientService.findIngredients(recipeeName);
    }

    @GET
    @Path("/recipee/{recipeeName}/ingredient/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public QuantityVO getIngredient(@PathParam("recipeeName") String recipeeName, @PathParam("name") String name) {
        return ingredientService.findByName(recipeeName, name);
    }

    @PUT
    @Path("/recipee/{recipeeName}/ingredient/{name}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateIngredient(QuantityVO quantityVO, @PathParam("recipeeName") String recipeeName, @PathParam("name") String name) {
        ingredientService.updateIngredient(quantityVO, recipeeName, name);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    @DELETE
    @Path("/recipee/{recipeeName}/ingredient/{name}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteIngredient(@PathParam("recipeeName") String recipeeName, @PathParam("name") String name) {
        ingredientService.delete(recipeeName, name);
        return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
    }

    @POST
    @Path("/recipee/{recipeeName}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addIngredient(IngredientRecipeeVO ingredient,@PathParam("recipeeName") String recipeeName, @Context UriInfo uriInfo) {
        String absolutePath = uriInfo.getAbsolutePath().toString();
        if (!absolutePath.endsWith("/")){
            absolutePath += "/";
        }
        ingredientService.addIngredient(ingredient, recipeeName);
        return Response.status(Response.Status.CREATED.getStatusCode()).header("Location", URLEncoder.encode(String.format("%s%s/%s", absolutePath, "ingredient", ingredient.getName()), StandardCharsets.ISO_8859_1)).build();
    }
}
