package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FotoLocalStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path caminhoFoto = recuperarCaminhoFoto(novaFoto.getNome());
            OutputStream fotoOutputStream = Files.newOutputStream(caminhoFoto);
            FileCopyUtils.copy(novaFoto.getArquivoInputStream(), fotoOutputStream);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void excluir(String nomeFoto) {
        try {
            Path caminhoFoto = recuperarCaminhoFoto(nomeFoto);
            Files.deleteIfExists(caminhoFoto);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path caminhoFoto = recuperarCaminhoFoto(nomeArquivo);
            return FotoRecuperada.builder()
                    .arquivoInputStream(Files.newInputStream(caminhoFoto))
                    .build();
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    private Path recuperarCaminhoFoto(String nomeArquivo) {
        return Path
                .of(storageProperties.getLocal().getDiretorioFotos())
                .resolve(Path.of(nomeArquivo));
    }
}
