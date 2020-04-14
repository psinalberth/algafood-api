package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DiscoLocalFotoStorageService implements FotoStorageService {

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path path = Path.of("/catalogo", novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path path = Path.of("/catalogo", nomeArquivo);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path path = Path.of("/catalogo", nomeArquivo);
            return Files.newInputStream(path);
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }
}
