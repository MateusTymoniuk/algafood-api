package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FotoLocalStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private String diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path diretorioFotosPath = Path.of(diretorioFotos);
            Path caminhoFoto = diretorioFotosPath.resolve(Path.of(novaFoto.getNome()));
            OutputStream fotoOutputStream = Files.newOutputStream(caminhoFoto);
            FileCopyUtils.copy(novaFoto.getArquivoInputStream(), fotoOutputStream);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void excluir(String nomeFoto) {
        try {
            Path diretorioFotosPath = Path.of(diretorioFotos);
            Path caminhoFoto = diretorioFotosPath.resolve(Path.of(nomeFoto));
            Files.deleteIfExists(caminhoFoto);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        try {
            Path diretorioFotosPath = Path.of(diretorioFotos);
            Path caminhoFoto = diretorioFotosPath.resolve(Path.of(nomeArquivo));
            return Files.newInputStream(caminhoFoto);
        } catch (IOException e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }
}
