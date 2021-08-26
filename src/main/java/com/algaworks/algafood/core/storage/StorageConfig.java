package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.S3;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.FotoLocalStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algafood.core.storage.StorageProperties.TipoStorage.S3;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        S3 s3 = storageProperties.getS3();

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3.getIdChaveAcesso(), s3.getChaveSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3.getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        }

        return new FotoLocalStorageService();
    }
}
