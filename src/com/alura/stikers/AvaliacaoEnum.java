package com.alura.stikers;

public enum AvaliacaoEnum {

    BRABO("BRABO", "sobreposicao\\brabo.png"), MEH("MEH", "sobreposicao\\meh.png");

    private final String texto;
    private final String imagem;

    AvaliacaoEnum(String texto, String imagem) {
        this.texto = texto;
        this.imagem = imagem;
    }

    public String getTexto() {
        return this.texto;
    }

    public String getImagem() {
        return this.imagem;
    }

    public static AvaliacaoEnum getByRating(String rating) {
        int round = Math.round(Float.parseFloat(rating));

        if (round >= 9)
            return BRABO;
        else
            return MEH;
    }
}
