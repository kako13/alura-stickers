package com.alura.stikers.domain.enums;

public enum AvaliacaoEnum {

    BRABO("BRABO", "sobreposicao\\brabo.png"), MEH("MEH", "sobreposicao\\meh.png");

    private final String texto;
    private final String pathImagem;

    AvaliacaoEnum(String texto, String pathImagem) {
        this.texto = texto;
        this.pathImagem = pathImagem;
    }

    public String getTexto() {
        return this.texto;
    }

    public String getPathImagem() {
        return this.pathImagem;
    }

    public static AvaliacaoEnum getByRating(String rating) {
        int round = Math.round(Float.parseFloat(rating));

        if (round >= 9)
            return BRABO;
        else
            return MEH;
    }
}
