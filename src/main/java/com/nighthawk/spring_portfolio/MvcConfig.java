package com.nighthawk.spring_portfolio;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // set up your own index
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    /* map path and location for "uploads" outside of application resources
       ... creates a directory outside "static" folder, "file:volumes/uploads"
       ... CRITICAL, without this uploaded file will not be loaded/displayed by frontend
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/volumes/uploads/**").addResourceLocations("file:volumes/uploads/");
    }

    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200/", "https://drewreed2005.github.io/dre2/", "https://csa-tri-1.github.io/DADDiJkstra-frontend/");//"https://nighthawkcoders.github.io", "https://dijkstra-tri1.vercel.app/", "https://drewreed2005.github.io/", "https://web.postman.co/", "http://localhost:4000", "http://localhost:8085", "http://0.0.0.0:4200/");
    }
    
}