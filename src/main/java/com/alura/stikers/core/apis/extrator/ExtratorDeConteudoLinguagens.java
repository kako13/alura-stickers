package com.alura.stikers.core.apis.extrator;

import com.alura.stikers.core.apis.models.ConteudoLinguagensAPI;
import com.alura.stikers.core.json.JsonParser;
import com.alura.stikers.domain.extrator.ExtratorDeConteudo;
import com.alura.stikers.domain.model.Conteudo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtratorDeConteudoLinguagens implements ExtratorDeConteudo {

    @Override
    public List<Conteudo> extraiDados(String json) {
        List<Map<String, String>> listaDeAtributos = JsonParser.parser(json);

        return listaDeAtributos.stream()
                .map(e -> new ConteudoLinguagensAPI(e.get("title"), e.get("image"))).collect(Collectors.toList());
    }
}
