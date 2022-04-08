package com.alkemy.ong.application.util.image;

import java.io.InputStream;
import java.util.Base64;
import lombok.Builder;

@Builder
public final class ImageImp implements IImage {

  private String fileName;
  private String contentType;
  private InputStream inputStream;

  public static byte[] decodeImage(String encodedImage) {
    return Base64.getDecoder().decode(encodedImage);
  }

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
    return inputStream;
  }
}
