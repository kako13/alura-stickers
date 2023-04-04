package com.alura.stikers;

import com.alura.stikers.core.apis.ApiExterna;
import com.alura.stikers.core.apis.ApiPropertiesService;
import com.alura.stikers.core.apis.models.ConteudoIMDB;
import com.alura.stikers.domain.GeradoraDeSticker;
import com.alura.stikers.domain.enums.AvaliacaoEnum;
import com.alura.stikers.domain.exceptions.InstanceDataExtractorExcetion;
import com.alura.stikers.domain.extrator.ExtratorDeConteudo;
import com.alura.stikers.domain.model.DadosObrigatorios;
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
                    ExtratorDeConteudo extrator = pegaInstanciaExtrator(apiExterna);
                    String json = new ClienteHttp().realizaGet(apiExterna.urlApi());
                    return extrator.extraiDados(json);
                })
                .flatMap(Collection::stream)

                //manipular os dados/Gerar figurinhas
                .forEach(insumoFigurinha -> geraFigurinhaNoDiretorio(geradora, insumoFigurinha));
    }

    private static void geraFigurinhaNoDiretorio(GeradoraDeSticker geradora, DadosObrigatorios dadosObrigatorios) {
        InputStream inputStream = StreamReader.recuperaImagemDaUrl(dadosObrigatorios);
        geradora.cria(inputStream, dadosObrigatorios.titulo() + ".png", atribuiAvaliacao(dadosObrigatorios));
        System.out.println(dadosObrigatorios + "\n");
    }

    private static AvaliacaoEnum atribuiAvaliacao(DadosObrigatorios dadosObrigatorios) {
        if (dadosObrigatorios instanceof ConteudoIMDB conteudoIMDB) {
            return AvaliacaoEnum.getByRating(conteudoIMDB.imDbRating());
        } else
            return AvaliacaoEnum.BRABO;
    }

    private static ExtratorDeConteudo pegaInstanciaExtrator(ApiExterna e) {
        Class<?> classe;
        try {
            classe = Class.forName(e.classeExtrator());
            return (ExtratorDeConteudo) classe.getConstructor().newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new InstanceDataExtractorExcetion("Erro ao instanciar extrator de dados");
        }
    }

    private static ApiExterna imprimeCabecalho(ApiExterna apiExterna) {
        System.out.println();
        System.out.println("\u001b[1mChamando " + apiExterna.Nome());
        System.out.println("Endpoint: \u001b[m " + apiExterna.urlApi());
        System.out.println();
        return apiExterna;
    }
}