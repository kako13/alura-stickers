package com.alura.stikers.core.apis.models;

import com.alura.stikers.domain.model.DadosObrigatorios;

public record ConteudoIMDB(String titulo, String tituloCompleto, String urlImagem, String imDbRating)
                                                                                            implements DadosObrigatorios {

    public ConteudoIMDB(String titulo, String tituloCompleto, String urlImagem, String imDbRating) {
        this.titulo = titulo.replace(":", "-").replace(" ", "_");
        this.tituloCompleto = tituloCompleto;
        this.urlImagem = urlImagem;
        this.imDbRating = imDbRating;
    }

    private String getStars() {
        String emoji = "\u2B50";
        int media = Math.round(Float.parseFloat(imDbRating()));

        return emoji.repeat(media);
    }

    @Override
    public String toString() {
        return "Título: \u001b[1m" + this.tituloCompleto() + ",\n" +
                "\u001b[mImagem: \u001b[1m" + this.urlImagem() + ",\n" +
                "\u001b[38;2;255;255;255m\u001b[48;2;42;122;228mClassificação: \u001b[1m" + this.imDbRating +
                "\u001b[m\n" + getStars();
    }
}
