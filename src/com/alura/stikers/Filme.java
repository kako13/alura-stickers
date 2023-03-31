package com.alura.stikers;

public class Filme extends Conteudo {

    private final String tituloCompleto;

    private final String imDbRating;



    public Filme(String titulo, String tituloCompleto, String urlImagem, String imDbRating) {
        super(titulo.replace(":", "-"), urlImagem);
        this.tituloCompleto=tituloCompleto;
        this.imDbRating=imDbRating;
    }

    public String getTituloCompleto() {
        return tituloCompleto;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    @Override
    public String toString() {
        return "Título: \u001b[1m" + this.getTituloCompleto() + ",\n" +
                "\u001b[mImagem: \u001b[1m" + super.getUrlImagem() + ",\n" +
                "\u001b[38;2;255;255;255m\u001b[48;2;42;122;228mClassificação: \u001b[1m" + imDbRating + "\u001b[m\n" +
                getStars();
    }

    private String getStars() {
        String emoji = "\u2B50";
        int round = Math.round(Float.parseFloat(getImDbRating()));

        return emoji.repeat(round);
    }
}
