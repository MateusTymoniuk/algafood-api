package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNome());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getArquivoInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível salvar o arquivo na S3", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

    @Override
    public void excluir(String nomeFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeFoto);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
                    caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo na S3", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo na S3", e);
        }
    }
}
