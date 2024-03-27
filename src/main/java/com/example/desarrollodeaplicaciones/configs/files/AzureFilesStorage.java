package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class AzureFilesStorage implements IFilesStorage {

  private Map<String, BlobClientStrategy> blobClientStrategies;

  public AzureFilesStorage(Map<String, BlobClientStrategy> blobClientStrategies) {
    this.blobClientStrategies = blobClientStrategies;
  }

  @Override
  public String uploadFile(String container, String blobName,  byte[] imageBytes) {
    BlockBlobClient client =
        blobClientStrategies.get(container).getBlobClient(blobName).getBlockBlobClient();
    client.upload(new ByteArrayInputStream(imageBytes), imageBytes.length, true);
    return client.getBlobUrl();
  }

  @Override
  public void deleteFile(String container, String blobName) {
    BlobClient client = blobClientStrategies.get(container).getBlobClient(blobName);
    client.delete();
  }
}
