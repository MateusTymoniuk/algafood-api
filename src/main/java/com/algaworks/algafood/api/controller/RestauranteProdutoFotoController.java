package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInputDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroFotoProdutoService;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroFotoProdutoService cadastroFotoProdutoService;

    @Autowired
    private FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = cadastroFotoProdutoService.buscar(restauranteId, produtoId);

        return fotoProdutoDTOAssembler.toDTO(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> buscarArquivo(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                             @RequestHeader(name = HttpHeaders.ACCEPT) String nomesMediaTypesAceitos)
            throws HttpMediaTypeNotAcceptableException {
        FotoProduto fotoProduto = cadastroFotoProdutoService.buscar(restauranteId, produtoId);

        MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
        List<MediaType> mediaTypesAceitos = MediaType.parseMediaTypes(nomesMediaTypesAceitos);

        verificarCompatibilidadeMediaTypes(mediaTypeFoto, mediaTypesAceitos);

        InputStream arquivoFoto = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

        return ResponseEntity.ok()
                .contentType(mediaTypeFoto)
                .body(new InputStreamResource(arquivoFoto));
    }

    private void verificarCompatibilidadeMediaTypes(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitos)
            throws HttpMediaTypeNotAcceptableException {
        boolean isMediaTypeAceito = mediaTypesAceitos.stream()
                .anyMatch(mediaTypeAceito -> mediaTypeAceito.isCompatibleWith(mediaTypeFoto));

        if(!isMediaTypeAceito) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitos);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                        @Valid FotoProdutoInputDTO fotoProdutoInputDTO) throws IOException {
        Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);

        MultipartFile arquivoFoto = fotoProdutoInputDTO.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInputDTO.getDescricao());
        fotoProduto.setNomeArquivo(arquivoFoto.getOriginalFilename());
        fotoProduto.setContentType(arquivoFoto.getContentType());
        fotoProduto.setTamanho(arquivoFoto.getSize());

        cadastroFotoProdutoService.cadastrar(fotoProduto, arquivoFoto.getInputStream());

        return fotoProdutoDTOAssembler.toDTO(fotoProduto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        cadastroFotoProdutoService.excluir(restauranteId, produtoId);
    }
}
