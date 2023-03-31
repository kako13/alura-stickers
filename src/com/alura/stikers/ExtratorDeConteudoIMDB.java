package com.alura.stikers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtratorDeConteudoIMDB implements ExtratorDeConteudo {

    @Override
    public List<Conteudo> extraiConteudos(String json) {
        List<Map<String, String>> listaDeAtributos = JsonParser.parser(json);

        return listaDeAtributos.stream()
                .map(filme -> new Filme(filme.get("title"),
                                        filme.get("fullTitle"),
                                        filme.get("image"),
                                        filme.get("imDbRating"))).collect(Collectors.toList());
    }
}
