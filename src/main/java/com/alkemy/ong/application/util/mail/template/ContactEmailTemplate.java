package com.alkemy.ong.application.util.mail.template;

import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import java.text.MessageFormat;

public class ContactEmailTemplate implements IContent, IEmail {

  private final String sendTo;
  private final String contactName;

  public ContactEmailTemplate(String email, String name) {
    this.sendTo = email;
    this.contactName = name;
  }

  @Override
  public String getBody() {
    return "<body style=\"padding: 5px 10px; max-width: 600px; background-color:#52c0f7;"
        + "font-family: 'Roboto', sans-serif;\">"
        + "<div style=\"border: 1px solid grey; width: 90%; margin: 10px 20px;\">"
        + "<div style=\"margin: 20px 20px;\">"
        + "<h2>Contact successfully registered</h2>"
        + "<br>"
        + "<p style=\"font-size:130%;\"> Welcome " + this.contactName
        + ", thanks for register in our contact list</p>"
        + "</div>"
        + "</div>"
        + "</body>";
  }

  @Override
  public String getContentType() {
    return "text/html";
  }

  @Override
  public String getSubject() {
    return "Contact successfully registered";
  }

  @Override
  public String getTo() {
    return this.sendTo;
  }

  @Override
  public IContent getContent() {
    return this;
  }
}
