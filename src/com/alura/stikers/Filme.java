package com.alura.stikers;

public class Filme {

    private String titulo;
    private String image;
    private String imDbRating;


    public Filme(String titulo, String image, String imDbRating) {
        this.titulo = titulo;
        this.image=image;
        this.imDbRating=imDbRating;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImage() {
        return image;
    }

    public String getImDbRating() {
        return imDbRating;
    }


    @Override
    public String toString() {
        return "Título: \u001b[1m" + titulo + "\",\n" +
                "\u001b[mImagem: \u001b[1m" + image + "\",\n" +
                "\u001b[38;2;255;255;255m \u001b[48;2;42;122;228m Classificação: \u001b[1m" + imDbRating + "\u001b[m\n" +
                getStars();
    }

    private String getStars() {
        String emoji = "\u2B50";
        int round = Math.round(Float.valueOf(getImDbRating()));
        String estrelas = emoji.repeat(round);

        return estrelas;
    }
}