package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

import static com.algaworks.algafood.domain.service.FotoStorageService.*;

@Service
public class FotoProdutoService {

    final ProdutoRepository produtoRepository;
    final FotoStorageService fotoStorageService;

    public FotoProdutoService(ProdutoRepository produtoRepository, FotoStorageService fotoStorageService) {
        this.produtoRepository = produtoRepository;
        this.fotoStorageService = fotoStorageService;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {

        Long restauranteId = fotoProduto.getRestauranteId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        String nomeArquivoSalvo = null;

        Optional<FotoProduto> fotoSalva = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoSalva.isPresent()) {
            nomeArquivoSalvo = fotoSalva.get().getNomeArquivo();
            produtoRepository.delete(fotoSalva.get());
        }

        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(inputStream)
                .build();

        fotoStorageService.substituir(nomeArquivoSalvo, novaFoto);

        return fotoProduto;
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId).
                orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }
}