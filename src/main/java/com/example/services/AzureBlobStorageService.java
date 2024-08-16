package com.example.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import jakarta.annotation.PostConstruct;

@Service
public class AzureBlobStorageService {

	@Value("${spring.cloud.azure.storage.blob.account-name}")
	private String accountName;

	@Value("${spring.cloud.azure.storage.blob.account-key}")
	private String accountKey;

	@Value("${spring.cloud.azure.storage.blob.container-name}")
	private String containerName;

	private BlobServiceClient blobServiceClient;
	private BlobContainerClient blobContainerClient;

	@PostConstruct
	public void init() {
		blobServiceClient = new BlobServiceClientBuilder().connectionString(String.format(
				"DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
				accountName, accountKey)).buildClient();

		blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

		if (!blobContainerClient.exists()) {
			blobContainerClient.create();
		}
	}

	public void uploadFile(MultipartFile file) throws IOException {
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getOriginalFilename());
        blobClient.upload(file.getInputStream(), file.getSize(), true);
    }
}
