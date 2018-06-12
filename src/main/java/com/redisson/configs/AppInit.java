package com.redisson.configs;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class AppInit extends ResourceConfig {
    public AppInit() {
        packages("com.redisson.score");
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");
        register(LoggingFilter.class);
        register(JspMvcFeature.class);
    }
}

