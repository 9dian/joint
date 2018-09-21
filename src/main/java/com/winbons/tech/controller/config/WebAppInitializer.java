package com.winbons.tech.controller.config;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * Servlet 3.0 replacement for web.xml
 * @author sfang
 * @created sfang
 * $DateTime: Apr 4, 2016 10:27:42 PM
 * @see [相关类/方法]
 */
public class WebAppInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

    /**
     * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
     */
    @Override
    public void onStartup(ServletContext servletContext) {

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        // if you're not passing in the config classes to the constructor,
        // refresh
        rootContext.refresh();

        // Manage the lifecycle of the root appcontext
        servletContext.addListener(new ContextLoaderListener(rootContext));
        //servletContext.setInitParameter("defaultHtmlEscape", "true");

        // now the config for the Dispatcher servlet
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(WebMvcConfig.class);

        // Filters
        // http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/package-frame.html

        // Enables support for DELETE and PUT request methods with web browser clients
        // http://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/HiddenHttpMethodFilter.html

        FilterRegistration.Dynamic fr = servletContext.addFilter(
                "hiddenHttpMethodFilter", new HiddenHttpMethodFilter());
        fr.addMappingForUrlPatterns(null, true, "/*");

        fr = servletContext.addFilter("encodingFilter",
                new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");

        // The main Spring MVC servlet. -- DispatcherServlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(mvcContext));
        dispatcher.setLoadOnStartup(1);
        Set<String> mappingConflicts = dispatcher.addMapping("/");

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                logger.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }

        SpringServlet jerseySpringServlet = new SpringServlet();

        ServletRegistration.Dynamic jerseyServlet = servletContext.addServlet("jersey-serlvet", jerseySpringServlet);
        jerseyServlet.setInitParameter("com.sun.jersey.config.property.packages", "com.winbons.tech.service");
        jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE.toString());

        jerseyServlet.setLoadOnStartup(1);

        Set jerseyServletMappingConflicts = jerseyServlet.addMapping(new String[] { "/rest/*" });

        if (!jerseyServletMappingConflicts.isEmpty())
            throw new IllegalStateException("'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");

    }
}
