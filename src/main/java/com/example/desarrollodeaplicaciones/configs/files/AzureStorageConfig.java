package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

  private String sasToken;

  private String endpoint;

  public AzureStorageConfig(
      @Value("${azure.SAS_TOKEN}") String sasToken, @Value("${azure.ENDPOINT}") String endpoint) {
    this.sasToken = sasToken;
    this.endpoint = endpoint;
  }

  @Bean
  public IFilesStorage getAzureFilesStorage(Map<String, BlobClientStrategy> blobClientStrategies) {
    return new AzureFilesStorage(blobClientStrategies);
  }

  @Bean
  public BlobServiceClient getBlobServiceClient() {
    return new BlobServiceClientBuilder().sasToken(sasToken).endpoint(endpoint).buildClient();
  }

  @Bean
  public Map<String, BlobClientStrategy> getBlobClientStrategies(
      BlobServiceClient blobServiceClient) {
    return Map.of(
        "user-images", new BlobUsersImagesStrategy(blobServiceClient),
        "movies-images", new BlobMoviesImagesStrategy(blobServiceClient));
  }
}
