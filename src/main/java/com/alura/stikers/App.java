package com.alura.stikers;

import com.alura.stikers.core.apis.ApiExterna;
import com.alura.stikers.core.apis.ApiPropertiesService;
import com.alura.stikers.core.apis.models.ConteudoIMDB;
import com.alura.stikers.domain.GeradoraDeSticker;
import com.alura.stikers.domain.enums.AvaliacaoEnum;
import com.alura.stikers.domain.exceptions.ExternalServiceAPIException;
import com.alura.stikers.domain.exceptions.ExtractorNotFoundExcetion;
import com.alura.stikers.domain.exceptions.GetImageFromUrlException;
import com.alura.stikers.domain.exceptions.sticker.StickerCreationException;
import com.alura.stikers.domain.exceptions.sticker.StickerImageReadException;
import com.alura.stikers.domain.exceptions.sticker.StickerImageWriteException;
import com.alura.stikers.domain.extrator.ExtratorDeConteudo;
import com.alura.stikers.domain.model.Conteudo;
import com.alura.stikers.infra.http.ClienteHttp;
import com.alura.stikers.infra.io.StreamReader;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class App {
    public static void main(String[] args) {

        List<ApiExterna> apiExternas = ApiPropertiesService.getExternalAPIs();
        var geradora = new GeradoraDeSticker();

        apiExternas.stream()
                //exibir
                .map(App::imprimeCabecalho)

                //extrair apenas os dados que interessam a cada abstração
                .map(apiExterna -> {
                    ExtratorDeConteudo extrator = null;
                    try {
                        extrator = pegaInstanciaExtrator(apiExterna);
                    } catch (ExtractorNotFoundExcetion e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }

                    String json = null;
                    try {
                        json = new ClienteHttp().realizaGet(apiExterna.urlApi());
                    } catch (ExternalServiceAPIException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }

                    return extrator.extraiDados(json);
                })
                .flatMap(Collection::stream)

                //manipular os dados/Gerar figurinhas
                .forEach(insumoFigurinha -> {
                    try {
                        geraFigurinhaNoDiretorio(geradora, insumoFigurinha);
                    } catch (StickerCreationException | GetImageFromUrlException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                });
    }

    private static void geraFigurinhaNoDiretorio(GeradoraDeSticker geradora, Conteudo conteudo) throws StickerImageReadException, GetImageFromUrlException, StickerImageWriteException {
        InputStream inputStream = StreamReader.recuperaImagemDaUrl(conteudo);
        geradora.cria(inputStream, conteudo.titulo() + ".png", atribuiAvaliacao(conteudo));
        System.out.println(conteudo + "\n");
    }

    private static AvaliacaoEnum atribuiAvaliacao(Conteudo conteudo) {
        if (conteudo instanceof ConteudoIMDB conteudoIMDB) {
            return AvaliacaoEnum.getByRating(conteudoIMDB.imDbRating());
        } else
            return AvaliacaoEnum.BRABO;
    }

    private static ExtratorDeConteudo pegaInstanciaExtrator(ApiExterna e) throws ExtractorNotFoundExcetion {
        Class<?> classe;
        try {
            classe = Class.forName(e.classeExtrator());
            return (ExtratorDeConteudo) classe.getConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new ExtractorNotFoundExcetion("Extrator de conteudo da API " + e.nome() + " não encontrado " +
                    "no arquivo de propriedades");
        }
    }

    private static ApiExterna imprimeCabecalho(ApiExterna apiExterna) {
        System.out.println();
        System.out.println("\u001b[1mChamando " + apiExterna.nome());
        System.out.println("Endpoint: \u001b[m " + apiExterna.urlApi());
        System.out.println();
        return apiExterna;
    }
}