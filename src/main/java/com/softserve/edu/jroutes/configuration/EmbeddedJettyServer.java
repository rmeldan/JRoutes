package com.softserve.edu.jroutes.configuration;

import org.apache.tomcat.*;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

public class EmbeddedJettyServer {

    public static void main(String... args) throws Exception {
        startServer();
    }

    public static void startServer() throws Exception {
        Server server = new Server(8080);
        server.setHandler(createHandler());
        server.start();
        server.join();
    }

    private static WebAppContext createHandler() throws IOException {
        WebAppContext context = new WebAppContext();
        context.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        context.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
        context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
        context.addBean(new ServletContainerInitializersStarter(context), true);
        context.setContextPath("/jroutes");

        return context;
    }

    private static List<ContainerInitializer> jspInitializers() {
        List<ContainerInitializer> initializers = new ArrayList<>();
        initializers.add(new ContainerInitializer(new JettyJasperInitializer(), null));
        //1
        //2
        //3
        //4
        return initializers;
    }
}