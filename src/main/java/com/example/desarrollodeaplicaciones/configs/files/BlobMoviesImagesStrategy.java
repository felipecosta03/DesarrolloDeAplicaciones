package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.Getter;

public class BlobMoviesImagesStrategy implements BlobClientStrategy {
  private BlobServiceClient blobServiceClient;
  @Getter private BlobContainerClient blobContainerClient;

  public BlobMoviesImagesStrategy(BlobServiceClient blobServiceClient) {
    this.blobServiceClient = blobServiceClient;
    blobContainerClient = blobServiceClient.getBlobContainerClient("movies-images");
  }

  public BlobClient getBlobClient(String blobName) {
    return blobContainerClient.getBlobClient(blobName);
  }
}
