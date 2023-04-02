package com.alura.stikers;

public record Conteudo(String titulo, String urlImagem) implements InsumoFigurinha {



    @Override
    public String getUrlImagem() {
        return urlImagem;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return "Título: \u001b[1m" + titulo + ",\n" +
                "\u001b[mImagem: \u001b[1m" + urlImagem + ",\n";
    }
}
