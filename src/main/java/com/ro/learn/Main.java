package com.ro.learn;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);

        server.addConnector(http);

        ContextHandlerCollection contexts = new ContextHandlerCollection();

        contexts.setHandlers(new Handler[]{initializeJaxRS(), initializeWS()});

        server.setHandler(contexts);

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static ContextHandler initializeJaxRS() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/rest");

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                HelloJAXRS.class.getCanonicalName());
        return context;
    }

    private static ContextHandler initializeWS() throws ServletException, DeploymentException {
        ServletContextHandler contextWS = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        contextWS.setContextPath("/ws/");

        ServerContainer wsContainer = WebSocketServerContainerInitializer
                .configureContext(contextWS);

        wsContainer.addEndpoint(WSDemo.class);

        return contextWS;
    }
}
