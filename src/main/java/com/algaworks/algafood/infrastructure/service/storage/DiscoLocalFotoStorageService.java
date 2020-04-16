package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class DiscoLocalFotoStorageService implements FotoStorageService {

    final StorageProperties properties;

    public DiscoLocalFotoStorageService(StorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path path = getPathArquivo(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path path = getPathArquivo(nomeArquivo);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path = getPathArquivo(nomeArquivo);
            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(path))
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    private Path getPathArquivo(String nomeArquivo) {
        return properties.getLocal().getDirectory().resolve(Path.of(nomeArquivo));
    }
}