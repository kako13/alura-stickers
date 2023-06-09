package com.alura.stikers.infra.http;

import com.alura.stikers.domain.exceptions.ExternalServiceAPIException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteHttp {

    public String realizaGet(String url) throws ExternalServiceAPIException {

        try {
            var client = HttpClient.newHttpClient();
            URI endereco = URI.create(url);
            var request = HttpRequest.newBuilder(endereco).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new ExternalServiceAPIException(String.format("Erro ao consultar a URL '%s'", url));
        }
    }
}
