package com.vvs.springfileuploading.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springfileuploading.handler.FileUploadHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class FileUploadRouter {

  @Bean
  public RouterFunction<ServerResponse> fileUploadFunction(FileUploadHandler handler) {
    return route()
      .nest(path("/api"), builder -> builder
        .POST("/upload", handler::fileUpload))
      .build();
  }
  
}
