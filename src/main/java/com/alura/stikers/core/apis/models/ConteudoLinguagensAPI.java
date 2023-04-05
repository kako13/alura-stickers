package com.alura.stikers.core.apis.models;

import com.alura.stikers.domain.model.Conteudo;

public record ConteudoLinguagensAPI(String titulo, String urlImagem) implements Conteudo {

    @Override
    public String toString() {
        return "Título: \u001b[1m" + this.titulo + ",\n" +
                "\u001b[mImagem: \u001b[1m" + this.urlImagem + ",\n";
    }
}
