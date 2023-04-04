package com.alura.stikers.domain.extrator;

import com.alura.stikers.domain.model.DadosObrigatorios;

import java.util.List;

public interface ExtratorDeConteudo {

    List<DadosObrigatorios> extraiDados(String json);
}
