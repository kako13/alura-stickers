package com.alura.stikers;

import java.util.List;

public interface ExtratorDeConteudo {

    List<InsumoFigurinha> extraiConteudos(String json);
}
