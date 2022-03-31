package com.alkemy.ong.infrastructure.aws.util;

import java.io.InputStream;

public interface IUpload {

  String getFileName();

  String getContentType();

  InputStream getInputStream();

}
