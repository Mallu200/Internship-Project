package org.xworkz.prodex.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.lang.NonNull;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebConfig.class, SwaggerConfig.class };
    }

    @Override
    protected @NonNull String[] getServletMappings() {
        return new String[]{ "/" };
    }
}