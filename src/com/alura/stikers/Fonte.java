package com.alura.stikers;

public class Fonte {

    private final String urlApi;
    private final String classe;

    public Fonte(String urlApi, String classe) {
        this.urlApi = urlApi;
        this.classe = classe;
    }

    public String getUrlApi() {
        return urlApi;
    }

    public String getClasse() {
        return classe;
    }
}
