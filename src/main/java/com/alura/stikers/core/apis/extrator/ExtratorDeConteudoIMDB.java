package com.alura.stikers.core.apis.extrator;

import com.alura.stikers.core.json.JsonParser;
import com.alura.stikers.domain.extrator.ExtratorDeConteudo;
import com.alura.stikers.core.apis.models.ConteudoIMDB;
import com.alura.stikers.domain.model.DadosObrigatorios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtratorDeConteudoIMDB implements ExtratorDeConteudo {

    @Override
    public List<DadosObrigatorios> extraiDados(String json) {
        List<Map<String, String>> listaDeAtributos = JsonParser.parser(json);

        return listaDeAtributos.stream()
                .map(filme -> new ConteudoIMDB(filme.get("title"),
                                        filme.get("fullTitle"),
                                        filme.get("image"),
                                        filme.get("imDbRating"))).collect(Collectors.toList());
    }
}
