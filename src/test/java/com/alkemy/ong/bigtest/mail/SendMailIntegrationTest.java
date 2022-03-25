package com.alkemy.ong.bigtest.mail;

import com.alkemy.ong.application.util.mail.EmailDelegate;
import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore("Right credentials are needed to run it.")
@SpringBootTest
@RunWith(SpringRunner.class)
public class SendMailIntegrationTest {

  @Autowired
  private EmailDelegate emailDelegate;

  @Test
  void shouldSendEmail() {
    emailDelegate.send(new Email());
  }

  private static class Email implements IEmail, IContent {

    @Override
    public String getBody() {
      return "Welcome to Somos Mas";
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
      return "foo@gmail.com";
    }

    @Override
    public IContent getContent() {
      return this;
    }
  }

}
