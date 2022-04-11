package com.alkemy.ong.application.util.image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import lombok.Builder;

@Builder
public class Image implements IImage {

  private final String fileName;
  private final String contentType;
  private final String encodedImage;

  @Override
  public String getFileName() {
    return fileName;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public InputStream getInputStream() {
    return decodeImage(encodedImage);
  }

  private InputStream decodeImage(String encodedImage) {
    return new ByteArrayInputStream(Base64.getDecoder().decode(encodedImage));
  }
}
