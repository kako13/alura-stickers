package com.alura.stikers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtratorDeConteudoNasa implements ExtratorDeConteudo {

    @Override
    public List<Conteudo> extraiConteudos(String json) {
        List<Map<String, String>> listaDeAtributos = JsonParser.parser(json);

        return listaDeAtributos.stream()
                .map(e -> new Conteudo(e.get("title"), e.get("url"))).collect(Collectors.toList());
    }
}
