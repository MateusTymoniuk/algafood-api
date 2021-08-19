package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CadastroFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto cadastrar(FotoProduto fotoProduto, InputStream arquivoInputStream) {
        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();

        String novoNome = fotoStorageService.getNomeFoto(fotoProduto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoByProduto(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        fotoProduto.setNomeArquivo(novoNome);

        fotoProduto = produtoRepository.save(fotoProduto);

        NovaFoto novaFoto = NovaFoto.builder()
                .nome(fotoProduto.getNomeArquivo())
                .arquivoInputStream(arquivoInputStream)
                .build();
        fotoStorageService.substituir(novaFoto, nomeArquivoExistente);

        return fotoProduto;
    }


    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoByProduto(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId, restauranteId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);

        produtoRepository.delete(fotoProduto);

        fotoStorageService.excluir(fotoProduto.getNomeArquivo());
    }
}
