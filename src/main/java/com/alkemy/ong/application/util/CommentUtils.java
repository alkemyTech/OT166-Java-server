package com.alkemy.ong.application.util;

import java.text.MessageFormat;

public class CommentUtils {

  private CommentUtils() {

  }

  public static String createdBy(String firstName, String lastName) {
    return MessageFormat.format("{0} {1}", firstName, lastName);
  }

}
