package com.alkemy.ong.infrastructure.aws.util;

import com.alkemy.ong.infrastructure.aws.config.AmazonAwsConfig;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadImageDelegate {

  @Autowired
  private AmazonAwsConfig amazonAwsConfig;

  public String upload(IUpload image) throws IOException {

    String url = "";

    try {

      AmazonS3 s3Client = amazonAwsConfig.initialize();
      String bucket = amazonAwsConfig.getBucket();
      ObjectMetadata contentType = new ObjectMetadata();
      contentType.setContentType(image.getContentType());
      PutObjectRequest request = new PutObjectRequest(bucket,
          image.getFileName(), image.getInputStream(), contentType);
      s3Client.putObject(request);

      url = s3Client.getUrl(bucket, image.getFileName()).toString();

    } catch (AmazonServiceException e) {
      e.printStackTrace();
    } catch (SdkClientException e) {
      e.printStackTrace();
    }

    return url;

  }

}
