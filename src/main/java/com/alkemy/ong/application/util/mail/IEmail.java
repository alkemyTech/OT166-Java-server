package com.alkemy.ong.application.util.mail;

public interface IEmail {

  String getSubject();

  String getTo();

  IContent getContent();

}
