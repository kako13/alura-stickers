package com.alura.stikers;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {

        HashMap<String, Fonte> mapNomeRelatorioComURI = ImdbProperties.carregarEndPoints();

        mapNomeRelatorioComURI.entrySet().forEach(e -> {

            System.out.println();
            System.out.println("\u001b[1mChamando " + e.getKey());
            System.out.println("Endpoint: \u001b[m " + e.getValue().getUrlApi());
            System.out.println();

            // fazer uma conexão HTTP e buscar os rankings disponibilizados
            String json = new ClienteHttp().buscaDados(e.getValue().getUrlApi());

            //extrair apenas os dados que interessam (título, titulo completo, imagem e classificação)
            ExtratorDeConteudo extrator;
            try {
                Class<?> classe = Class.forName(e.getValue().getClasse());
                extrator = (ExtratorDeConteudo) classe.getConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao instanciar Extrator");
            }

            List<Conteudo> conteudos = extrator.extraiConteudos(json);

            var geradora = new GeradoraDeFigurinhas();

            //exibir e manipular os dados/Gerar figurinhas
            conteudos.forEach(conteudo -> {
                        String urlImagem = conteudo.getUrlImagem();

                        InputStream inputStream;
                        try {
                            inputStream = new URL(urlImagem).openStream();

                            geradora.cria(inputStream, conteudo.getTitulo() + ".png", AvaliacaoEnum.BRABO);
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro ao criar figurinhas");
                        }

                        System.out.println(conteudo + "\n");
                    });
            System.out.println("\n");
        });
    }
}