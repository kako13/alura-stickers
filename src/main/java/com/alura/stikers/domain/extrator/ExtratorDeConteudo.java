package com.alura.stikers.domain.extrator;

import com.alura.stikers.domain.model.Conteudo;

import java.util.List;

public interface ExtratorDeConteudo {

    List<Conteudo> extraiDados(String json);
}
