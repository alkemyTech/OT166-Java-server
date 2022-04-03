package com.alkemy.ong.application.util.mail.template;

import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import java.text.MessageFormat;

public class ContactEmailTemplate implements IContent, IEmail {

  private static final String TYPE = "text/plain";
  private static final String SUBJECT = "Contact successfully registered";
  private static final String THANK_TEXT = "Thanks {0} for register in our contact list";

  private final String emailTo;
  private final String contactName;

  public ContactEmailTemplate(String email,String name) {
    this.emailTo = email;
    this.contactName = name;
  }

  @Override
  public String getBody() {
    return MessageFormat.format(THANK_TEXT, contactName);
  }

  @Override
  public String getContentType() {
    return TYPE;
  }

  @Override
  public String getSubject() {
    return SUBJECT;
  }

  @Override
  public String getTo() {
    return emailTo;
  }

  @Override
  public IContent getContent() {
    return this;
  }
}
