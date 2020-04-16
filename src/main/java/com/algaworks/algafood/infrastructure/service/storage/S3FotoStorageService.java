package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3FotoStorageService implements FotoStorageService {

    final AmazonS3 amazonS3;
    final StorageProperties storageProperties;

    public S3FotoStorageService(AmazonS3 amazonS3, StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var path = getPathArquivo(novaFoto.getNomeArquivo());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());
            objectMetadata.setContentLength(novaFoto.getTamanho());
            var request = new PutObjectRequest(
                    storageProperties.getS3().getBucketName(),
                    path,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(request);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            var path = getPathArquivo(nomeArquivo);
            var request = new DeleteObjectRequest(
                    storageProperties.getS3().getBucketName(),
                    path
            );

            amazonS3.deleteObject(request);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover arquivo na Amazon S3.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            var path = getPathArquivo(nomeArquivo);
            var url = amazonS3.getUrl(storageProperties.getS3().getBucketName(), path);

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();

        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo na Amazon S3.", e);
        }
    }

    private String getPathArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDirectory(), nomeArquivo);
    }
}