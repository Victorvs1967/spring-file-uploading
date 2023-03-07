package com.vvs.springfileuploading.handler;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FileUploadHandler {
  
  @Value("${upload.folder}")
  private String fileFolder;

  public Mono<ServerResponse> fileUpload(ServerRequest request) {
    return request.body(BodyExtractors.toMultipartData())
      .map(it -> it.get("file"))
      .flatMapMany(Flux::fromIterable)
      .cast(FilePart.class)
      .flatMap(it -> it.transferTo(Path.of(fileFolder + it.filename())))
      .then(ServerResponse
        .ok()
        .body(Flux.just("OK"), String.class));
  }
}
