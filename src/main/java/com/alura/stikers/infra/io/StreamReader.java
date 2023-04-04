package com.alura.stikers.infra.io;

import com.alura.stikers.domain.exceptions.GetDataFromUrlAPIException;
import com.alura.stikers.domain.model.DadosObrigatorios;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class StreamReader {
    public static InputStream recuperaImagemDaUrl(DadosObrigatorios dadosObrigatorios) {
        String urlImagem = dadosObrigatorios.urlImagem();
        InputStream inputStream;
        try {
            inputStream = new URL(urlImagem).openStream();
        } catch (IOException e) {
            throw new GetDataFromUrlAPIException(
                    String.format("Erro ao recuperar dados da url '%s'", dadosObrigatorios.urlImagem()));
        }
        return inputStream;
    }
}
