package com.alura.stikers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello world!\n");

        Map<String, String> mapNomeRelatorioComURI = ImdbProperties.carregarEndPoints();

        mapNomeRelatorioComURI.entrySet().forEach(e -> {

            System.out.println("\u001b[1mChamando "+ e.getKey());
            System.out.println("Endpoint: \u001b[m "+ e.getValue());
            System.out.println();

            // fazer uma conexão HTTP e buscar os rankings disponibilizados
            String responseJson = realizarGet(e.getValue());

            //extrair apenas os dados que interessam (título, poster, classificação)
            List<Map<String, String>> listaDeMapFilme =  JsonParser.parser(responseJson);

            //exibir e manipular os dados
            List<Filme> filmes = listaDeMapFilme.stream().map(filme -> {
                return new Filme(filme.get("fullTitle"), filme.get("image"), filme.get("imDbRating"));
            }).collect(Collectors.toList());

            filmes.stream().forEach(System.out::println);
        });


    }

    private static String realizarGet(String url) {
        var client = HttpClient.newHttpClient();
        URI endereco = URI.create(url);
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}