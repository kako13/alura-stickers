package com.alura.stikers.core.apis.models;

import com.alura.stikers.domain.model.DadosObrigatorios;

public record ConteudoNasa(String titulo, String urlImagem) implements DadosObrigatorios {

    @Override
    public String toString() {
        return "Título: \u001b[1m" + this.titulo + ",\n" +
                "\u001b[mImagem: \u001b[1m" + this.urlImagem + ",\n";
    }
}
