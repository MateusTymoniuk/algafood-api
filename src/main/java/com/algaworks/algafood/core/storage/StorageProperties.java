package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;

    @Getter
    @Setter
    public class Local {
        private String diretorioFotos;
    }

    @Getter
    @Setter
    public class S3 {
        private String idChaveAcesso;
        private String chaveSecreta;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;
    }

    public enum TipoStorage {
        LOCAL, S3;
    }
}
