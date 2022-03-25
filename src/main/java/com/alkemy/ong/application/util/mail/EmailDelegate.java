package com.alkemy.ong.application.util.mail;

import com.alkemy.ong.application.exception.SendEmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailDelegate {

  @Value("${app.sendgrid.key}")
  private String apiKey;

  @Value("${organization.from.email}")
  private String from;

  public void send(IEmail email) {

    Email to = new Email(email.getTo());
    Content content = new Content(email.getContent().getContentType(),
        email.getContent().getBody());
    String subject = email.getSubject();

    Mail mail = new Mail(new Email(from),subject,to,content);
    SendGrid sendGrid = new SendGrid(apiKey);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());

      Response response = sendGrid.api(request);
      log.info("Sendgrid status code: " + response.getStatusCode());
      log.info("Sendgrid body: " + response.getBody());

      if (response.getStatusCode() != 202){
        throw new SendEmailException("Failed to send email: " + response.getBody());
      }

    }catch (IOException | RuntimeException e){
      throw new SendEmailException(e.getMessage());
    }

  }

}
