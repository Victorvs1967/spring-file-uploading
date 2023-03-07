package com.vvs.springfileuploading.service;

import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;

public interface FileService {
  
  public Mono<?> upload(MultiValueMap<String, Part> file);
  public Mono<?> download(String filename);

}
