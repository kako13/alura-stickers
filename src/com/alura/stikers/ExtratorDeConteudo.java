package com.alura.stikers;

import java.util.List;

public interface ExtratorDeConteudo {

    List<Conteudo> extraiConteudos(String json);
}
