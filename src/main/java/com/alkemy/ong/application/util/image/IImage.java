package com.alkemy.ong.application.util.image;

import java.io.InputStream;

public interface IImage {

  String getFileName();

  String getContentType();

  InputStream getInputStream();

}
