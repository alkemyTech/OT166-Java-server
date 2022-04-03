package com.alkemy.ong.application.util.mail.template;

import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import java.text.MessageFormat;

public class ContactEmailTemplate implements IContent, IEmail {

  private static final String THANK_TEXT = "Thank you for contacting us";

  private final String sendTo;
  private final String contactName;

  public ContactEmailTemplate(String email,String name) {
    this.sendTo = email;
    this.contactName = name;
  }

  @Override
  public String getBody() {
    return MessageFormat.format(THANK_TEXT, contactName);
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
