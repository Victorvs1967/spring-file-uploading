package com.vvs.springfileuploading.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springfileuploading.service.FileService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class FileHandler {
  
  @Value("${upload.folder}")
  private String fileFolder;

  private final FileService fileService;

  public Mono<ServerResponse> fileUpload(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
      .flatMap(fileService::upload)
      .flatMap(file -> ServerResponse
        .ok()
        .body(Mono.just("Ok"), String.class));
  }

  public Mono<ServerResponse> fileDownload(ServerRequest request) {
    return Mono.just(request.pathVariable("filename"))
      .map(fileService::download)
      .flatMap(file -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(file, String.class));
  }
}
