package com.softserve.edu.jroutes.configuration;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.*;
import org.springframework.web.servlet.view.*;
import org.springframework.web.servlet.view.tiles3.*;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan({
        "com.softserve.edu.jroutes.configuration",
        "com.softserve.edu.jroutes.controller",
        "com.softserve.edu.jroutes.validators",
        "com.softserve.edu.jroutes.exception"})
public class MVCDispatcherServlet extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(MVCDispatcherServlet.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/template/");
        LOGGER.info("addResourceHandlers()");
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(2);
        LOGGER.info("viewResolver()");
        return viewResolver;
    }

    /**
     * Configure Apache Tiles for the view
     */
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(1);
        LOGGER.info("tilesViewResolver()");
        return viewResolver;
    }

    /**
     * Configures Apache tiles definitions bean used by Apache TilesViewResolver
     * to resolve views selected for rendering by @Controllers
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitionsFactoryClass(TilesXML.class);
        tilesConfigurer.setCheckRefresh(true);
        TilesXML.addDefinitions();
        LOGGER.info("tilesConfigurer()");
        return tilesConfigurer;
    }

    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        TilesViewResolver viewResolver = new TilesViewResolver();
        registry.viewResolver(viewResolver);
        LOGGER.info("configureViewResolvers()");
    }

    /**
     * Spring localization
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        LOGGER.info("messageSource()");
        return messageSource;
    }

    /**
     * Spring localization
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        LOGGER.info("localeResolver()");
        return resolver;
    }

    /**
     * Spring localization
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
        LOGGER.info("addInterceptors()");
    }
}