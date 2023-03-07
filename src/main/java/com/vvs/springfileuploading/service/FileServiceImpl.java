package com.vvs.springfileuploading.service;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;

@Service
public class FileServiceImpl implements FileService {
  
  @Value("${upload.folder}")
  private String fileFolder;

  @Override
  public Mono<Object> upload(MultiValueMap<String, Part> file) {
    return Mono.just(file)
      .map(parts -> parts.toSingleValueMap())
      .map(map -> map.get("file"))
      .cast(FilePart.class)
      .flatMap(it -> it.transferTo(Path.of(fileFolder + it.filename())));
  }

  @Override
  public Mono<?> download(String filename) {
    return Mono.just(filename);
  }

}
