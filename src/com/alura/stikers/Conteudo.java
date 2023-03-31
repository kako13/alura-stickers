package com.alura.stikers;

public class Conteudo {

    private final String urlImagem;
    private final String titulo;

    public Conteudo(String titulo, String urlImagem) {
        this.titulo=titulo.replace(":", "-");
        this.urlImagem = urlImagem;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "Título: \u001b[1m" + titulo + ",\n" +
                "\u001b[mImagem: \u001b[1m" + urlImagem + ",\n";
    }
}
