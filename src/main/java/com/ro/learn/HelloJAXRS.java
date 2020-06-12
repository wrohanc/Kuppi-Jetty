package com.ro.learn;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("rest")
public class HelloJAXRS {

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String available() {
        return "Hello World from JAX-RS";
    }

}

