package com.alura.stikers;

public record Filme(String titulo, String tituloCompleto, String urlImagem,
                    String imDbRating) implements InsumoFigurinha {

    public Filme(String titulo, String tituloCompleto, String urlImagem, String imDbRating) {
        this.titulo = titulo.replace(":", "-");
        this.urlImagem = urlImagem;
        this.tituloCompleto = tituloCompleto;
        this.imDbRating = imDbRating;
    }

    @Override
    public String getUrlImagem() {
        return urlImagem;
    }

    @Override
    public String getTitulo() {
        return null;
    }

    private String getStars() {
        String emoji = "\u2B50";
        int round = Math.round(Float.parseFloat(imDbRating()));

        return emoji.repeat(round);
    }

    @Override
    public String toString() {
        return "Título: \u001b[1m" + this.tituloCompleto() + ",\n" +
                "\u001b[mImagem: \u001b[1m" + this.getUrlImagem() + ",\n" +
                "\u001b[38;2;255;255;255m\u001b[48;2;42;122;228mClassificação: \u001b[1m" + imDbRating + "\u001b[m\n" +
                getStars();
    }
}
