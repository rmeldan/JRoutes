package com.softserve.edu.jroutes.configuration;

import org.apache.log4j.*;
import org.eclipse.jetty.annotations.*;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.*;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.*;

public class EmbeddedJettyServer {

    private static final String CONTEXT_PATH = "/jroutes";
    private static final String WEBAPP_DIRECTORY = "webapp";
    private static final Logger LOGGER = Logger.getLogger(EmbeddedJettyServer.class);

    public static void main(String... args) throws Exception {
        log4j();
        startServer();
    }

    private static void startServer() throws Exception {
        Server server = new Server(8080);
        server.setHandler(createHandler());
        server.start();
        LOGGER.info("startServer(): server started");
        server.join();
        LOGGER.info("startServer(): server joined");
    }

    private static ServletContextHandler createHandler() throws IOException {
        // инициализируем веб-контекст на базе нашей Java-based конфигурации WebContext
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(MVCDispatcherServlet.class);

        // определяем веб-контекст Jetty
        WebAppContext webAppContext = new WebAppContext();
        // базовая папка проекта, где находится WEB-INF
        webAppContext.setResourceBase(new ClassPathResource(WEBAPP_DIRECTORY).getURI().toString());
        // назначаем стандартного слушателя, Context Path, созданные сервлет
        webAppContext.addEventListener(new ContextLoaderListener(webContext));
        webAppContext.setContextPath(CONTEXT_PATH);
        // определяем стандартный сервлет Spring MVC
        ServletHolder servletHolder = new ServletHolder("mvc-dispatcher", new DispatcherServlet(webContext));
        servletHolder.setInitOrder(1);
        webAppContext.addServlet(servletHolder, "/");

        // определяем стандартный фильтр Spring Security и другие
        webAppContext.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", EnumSet.allOf(DispatcherType.class));
        webAppContext.addFilter(new FilterHolder(new OpenSessionInViewFilter()), "/*", EnumSet.allOf(DispatcherType.class));
        CharacterEncodingFilter encFilter = new CharacterEncodingFilter();
        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);
        webAppContext.addFilter(new FilterHolder(encFilter), "/*", EnumSet.allOf(DispatcherType.class));

        webAppContext.setAttribute(AnnotationConfiguration.CONTAINER_INITIALIZERS, jspInitializers());
        webAppContext.addBean(new ServletContainerInitializersStarter(webAppContext), true);
        LOGGER.info("createHandler(): handler created");
        return webAppContext;
    }

    private static List<ContainerInitializer> jspInitializers() {
        List<ContainerInitializer> initializers = new ArrayList<>();
        initializers.add(new ContainerInitializer(new JettyJasperInitializer(), null));
        return initializers;
    }

    private static void log4j() {
        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setName("console");
        consoleAppender.setTarget("System.out");
        consoleAppender.setLayout(new PatternLayout("%-5p: %c - %m%n"));
        consoleAppender.activateOptions();

        // creates file appender
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setName("fileInfo");
        fileAppender.setAppend(true);
        fileAppender.setMaxFileSize("10MB");
        fileAppender.setMaxBackupIndex(4);
        fileAppender.setEncoding("Cp1251");
        fileAppender.setFile(System.getProperty("user.home") + "/jroutes/logs/test.log");
        fileAppender.setLayout(new PatternLayout("%d [%t] %-5p (%F:%L:%M)  %c{1}  - %m%n"));
        fileAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }
}