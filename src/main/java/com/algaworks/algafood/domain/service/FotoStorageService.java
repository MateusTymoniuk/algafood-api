package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    void excluir(String nomeFoto);

    InputStream recuperar(String nomeArquivo);

    default void substituir(NovaFoto novaFoto, String nomeAntigo) {
        this.armazenar(novaFoto);

        if (Objects.nonNull(nomeAntigo)) {
            this.excluir(nomeAntigo);
        }
    }

    default String getNomeFoto(String nomeExistente) {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID().toString());
        sb.append("_");
        sb.append(nomeExistente);
        return sb.toString();
    }

    @Getter
    @Builder
    class NovaFoto {
        String nome;
        InputStream arquivoInputStream;
    }
}
