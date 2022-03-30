package com.alkemy.ong.application.util.mail;

import com.alkemy.ong.infrastructure.database.entity.OrganizationEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailWelcomeTemplate implements IEmail, IContent {
  
  private OrganizationEntity organizationEntity;
  private String sendTo;

  @Override
  public String getBody() {
    String message = "<body style=\"padding: 5px 10px; max-width: 600px; background-color:#52c0f7;"
        + " font-family: 'Roboto', sans-serif;\">";
    message += "<div style=\"height: 60px; padding: 10px 20px;\">";
    message += "<div style=\"width: 150px; overflow: hidden;\">";
    message += "<img src=\"" + this.organizationEntity.getImage() + "\" alt=\"somosmas-logo\">";
    message += "</div>";
    message += "</div>";
    message += "<div style=\"border: 1px solid grey; width: 90%; margin: 10px 20px;\"></div>";
    message += "<div style=\"margin: 20px 20px;\">";
    message += "<h2>Welcome to " + organizationEntity.getName() + "👌" + "</h2>";
    message += "<br>";
    message += "<p style=\"font-size:130%;\">We’re so glad you’ve joined us!.</p>";
    message += "</div>";
    message += "<br>";
    message += "<div style=\"border: 1px solid grey; width: 90%; margin: 10px 20px;\"></div>";
    message += "<div style=\"margin: 20px 20px;\">";
    message += "<div style=\"background-color: #eee; margin: 30px auto; padding: 10px 15px; \">";
    message += "<small>🏠 address: " + organizationEntity.getAddress()
        + "<br>" + "✉ mail: " + organizationEntity.getEmail()
        + "<br>" + " ☎ phone: " + organizationEntity.getPhone()
        + "<br>" + "📷 instagram: " + organizationEntity.getInstagramUrl()
        + "<br>" + "🇫​​​​​ facebook: " + organizationEntity.getFacebookUrl()
        + "<br>" + "🇱​​​​​ linkedin: " + organizationEntity.getLinkedInUrl() + "</small>";
    message += "</div>";
    message += "</div>";
    message += "</body>";
    
    return message;
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
