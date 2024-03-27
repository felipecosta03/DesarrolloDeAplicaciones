package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Map;

public class BlobClientConfig {

  @Value("${azure.SAS_TOKEN}")
  private String sasToken;

  @Value("${azure.ENDPOINT}")
  private String endpoint;

  public BlobClientConfig(String sasToken, String endpoint) {
    this.sasToken = sasToken;
    this.endpoint = endpoint;
  }

  @Bean
  public BlobServiceClient getBlobServiceClient() {
    return new BlobServiceClientBuilder()
        .connectionString(
            "https://imagestoragedda.blob.core.windows.net/?sv=2022-11-02&ss=bfqt&srt=c&sp=rwdlacupiytfx&se=2024-08-27T18:27:07Z&st=2024-03-27T10:27:07Z&sip=0.0.0.0&spr=https&sig=xxyj%2F0RN0qn8efyJlPh24U5vbRmVeRCat8aaePHz%2Bw8%3D")
        .buildClient();
  }

  @Bean
  public Map<String,BlobClientStrategy> getBlobClientStrategies(BlobServiceClient blobServiceClient){
    return Map.of(
        "user-images", new BlobUsersImagesStrategy(blobServiceClient),
        "movies-images", new BlobMoviesImagesStrategy(blobServiceClient)
    );
  }
}
