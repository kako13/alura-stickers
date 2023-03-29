package com.alura.stikers;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {

        HashMap<String, String> mapNomeRelatorioComURI = ImdbProperties.carregarEndPoints();

        mapNomeRelatorioComURI.entrySet().forEach(e -> {

            System.out.println("\u001b[1mChamando " + e.getKey());
            System.out.println("Endpoint: \u001b[m " + e.getValue());
            System.out.println();

            // fazer uma conexão HTTP e buscar os rankings disponibilizados
            String responseJson = null;
            try {
                responseJson = realizarGet(e.getValue());
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao chamar a api " + e.getKey());
            }

            //extrair apenas os dados que interessam (título, titulo completo, imagem e classificação)
            List<Map<String, String>> listaDeMapFilme = JsonParser.parser(responseJson);
            List<Filme> filmes = listaDeMapFilme.stream()
                    .map(filme -> {
                        return new Filme(filme.get("title"),
                                        filme.get("fullTitle"),
                                        filme.get("image"),
                                        filme.get("imDbRating"));
                    }).collect(Collectors.toList());

            var geradora = new GeradoraDeFigurinhas();

            //exibir e manipular os dados
            filmes.stream()
                    .forEach(f -> {
                        String urlImagem = f.getImage();

                        InputStream inputStream = null;
                        try {
                            inputStream = new URL(urlImagem).openStream();
                            geradora.cria(inputStream, f.getTitulo() + ".png");
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro ao criar figurinhas");
                        }

                        System.out.println(f + "\n");
                    });
            System.out.println("\n");
        });
    }

    private static String realizarGet(String url) throws Exception {
        var client = HttpClient.newHttpClient();
        URI endereco = URI.create(url);
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = null;

        response = client.send(request, HttpResponse.BodyHandlers.ofString());


        return response.body();
    }
}