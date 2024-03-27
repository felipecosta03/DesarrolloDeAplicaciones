package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class AzureFilesStorage implements IFilesStorage {

  private Map<String, BlobClientStrategy> blobClientStrategies;

  public AzureFilesStorage(Map<String, BlobClientStrategy> blobClientStrategies) {
    this.blobClientStrategies = blobClientStrategies;
  }

  @Override
  public String uploadFile(String container, String blobName, ByteArrayInputStream fileStream) {
    BlockBlobClient client =
        blobClientStrategies.get(container).getBlobClient(blobName).getBlockBlobClient();
    client.upload(BinaryData.fromStream(fileStream));
    return client.getBlobUrl();
  }

  @Override
  public void deleteFile(String container, String blobName) {
    BlobClient client = blobClientStrategies.get(container).getBlobClient(blobName);
    client.delete();
  }
}
