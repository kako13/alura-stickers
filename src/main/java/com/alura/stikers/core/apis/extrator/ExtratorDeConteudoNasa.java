package com.alura.stikers.core.apis.extrator;

import com.alura.stikers.core.json.JsonParser;
import com.alura.stikers.domain.extrator.ExtratorDeConteudo;
import com.alura.stikers.core.apis.models.ConteudoNasa;
import com.alura.stikers.domain.model.DadosObrigatorios;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtratorDeConteudoNasa implements ExtratorDeConteudo {

    @Override
    public List<DadosObrigatorios> extraiDados(String json) {
        List<Map<String, String>> listaDeAtributos = JsonParser.parser(json);

        return listaDeAtributos.stream()
                .map(e -> new ConteudoNasa(e.get("title"), e.get("url"))).collect(Collectors.toList());
    }
}
