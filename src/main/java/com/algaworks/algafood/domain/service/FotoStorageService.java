package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    FotoRecuperada recuperar(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID().toString(), nomeOriginal);
    }

    default void substituir(String nomeArquivoSalvo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        if (nomeArquivoSalvo != null) {
            this.remover(nomeArquivoSalvo);
        }
    }


    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
        private Long tamanho;
    }

    @Getter
    @Builder
    class FotoRecuperada {

        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return this.url != null;
        }

        public boolean temInputStream() {
            return this.inputStream != null;
        }
    }
}