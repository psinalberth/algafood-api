package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.DiscoLocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algafood.core.storage.StorageProperties.*;

@Configuration
public class StorageConfig {

    final StorageProperties storageProperties;

    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public FotoStorageService fotoStorageService(StorageProperties storageProperties) {
        if (StorageType.S3.equals(storageProperties.getType()))
            return new S3FotoStorageService(amazonS3(), storageProperties);
        return new DiscoLocalFotoStorageService(storageProperties);
    }

    @Bean
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
          storageProperties.getS3().getAccessKeyId(),
          storageProperties.getS3().getSecretAccessKey()
        );

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }
}