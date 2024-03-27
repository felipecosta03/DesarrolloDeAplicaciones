package com.example.desarrollodeaplicaciones.configs.files;

import com.azure.storage.blob.BlobClient;

public interface BlobClientStrategy{
    BlobClient getBlobClient(String blobName);
}
