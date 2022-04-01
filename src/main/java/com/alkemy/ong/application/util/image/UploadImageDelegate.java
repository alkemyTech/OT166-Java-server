package com.alkemy.ong.application.util.image;

import com.alkemy.ong.application.exception.UploadImageException;
import com.alkemy.ong.infrastructure.aws.config.AmazonAwsConfig;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadImageDelegate {

  @Autowired
  private AmazonAwsConfig amazonAwsConfig;

  public String upload(IImage image) {
    try {
      AmazonS3 s3Client = amazonAwsConfig.initialize();
      String bucket = amazonAwsConfig.getBucket();
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(image.getContentType());

      s3Client.putObject(
          new PutObjectRequest(bucket,
              image.getFileName(),
              image.getInputStream(),
              metadata).withCannedAcl(CannedAccessControlList.PublicRead));

      return s3Client.getUrl(bucket, image.getFileName()).toExternalForm();

    } catch (RuntimeException e) {
      throw new UploadImageException(e.getMessage());
    }
  }

}
