package com.alkemy.ong.application.util.mail.template;

import com.alkemy.ong.application.util.mail.IContent;
import com.alkemy.ong.application.util.mail.IEmail;
import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WelcomeTemplate implements IEmail, IContent {

  private final OrganizationEntity organizationEntity;
  private final String sendTo;

  @Override
  public String getBody() {
    return "<body style=\"padding: 5px 10px; max-width: 600px; background-color:#52c0f7;"
        + " font-family: 'Roboto', sans-serif;\">"
        + "<div style=\"height: 60px; padding: 10px 20px;\">"
        + "<div style=\"width: 150px; overflow: hidden;\">"
        + "<img src=\"" + organizationEntity.getImage() + "\" alt=\"somosmas-logo\">"
        + "</div>"
        + "</div>"
        + "<div style=\"border: 1px solid grey; width: 90%; margin: 10px 20px;\"></div>"
        + "<div style=\"margin: 20px 20px;\">"
        + "<h2>Welcome to " + organizationEntity.getName() + "👌" + "</h2>"
        + "<br>"
        + "<p style=\"font-size:130%;\">We’re so glad you’ve joined us!.</p>"
        + "</div>"
        + "<br>"
        + "<div style=\"border: 1px solid grey; width: 90%; margin: 10px 20px;\"></div>"
        + "<div style=\"margin: 20px 20px;\">"
        + "<div style=\"background-color: #eee; margin: 30px auto; padding: 10px 15px; \">"
        + "<small>🏠 address: " + organizationEntity.getAddress()
        + "<br>" + "✉ mail: " + organizationEntity.getEmail()
        + "<br>" + " ☎ phone: " + organizationEntity.getPhone()
        + "<br>" + "📷 instagram: " + organizationEntity.getInstagramUrl()
        + "<br>" + "🇫​​​​​ facebook: " + organizationEntity.getFacebookUrl()
        + "<br>" + "🇱​​​​​ linkedin: " + organizationEntity.getLinkedInUrl() + "</small>"
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
    return "Welcome to " + organizationEntity.getName() + "!";
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
