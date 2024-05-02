package org.example.recipee.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig extends ResourceConfig {

    public RestConfig() {

        register(RecipeeResource.class);
        register(IngredientResource.class);
    }
}