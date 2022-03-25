package com.alkemy.ong.bigtest.mail;

import com.alkemy.ong.application.util.mail.EmailDelegate;
import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendMailIntegrationTest {

  @Autowired
  private EmailDelegate emailDelegate;

  @Test
  public void shouldSendEmail(){
    emailDelegate.send(new Mail());
  }

  private class Mail implements IEmail,IContent{

    @Override
    public String getBody() {
      return "Welcome to SOMOS MAS";
    }

    @Override
    public String getContentType() {
      return "text/plain";
    }

    @Override
    public String getSubject() {
      return "subject";
    }

    @Override
    public String getTo() {
      return "mati.fiore1996@gmail.com";
    }

    @Override
    public IContent getContent() {
      return this;
    }
  }

}
