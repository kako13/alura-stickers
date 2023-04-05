package com.alura.stikers.infra.io;

import com.alura.stikers.domain.exceptions.GetImageFromUrlException;
import com.alura.stikers.domain.model.Conteudo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class StreamReader {
    public static InputStream recuperaImagemDaUrl(Conteudo conteudo) throws GetImageFromUrlException {
        String urlImagem = conteudo.urlImagem();
        InputStream inputStream;
        try {
            inputStream = new URL(urlImagem).openStream();
        } catch (IOException e) {
            throw new GetImageFromUrlException(
                    String.format("Erro ao recuperar imagem da url '%s'", conteudo.urlImagem()));
        }
        return inputStream;
    }
}
