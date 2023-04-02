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

            List<InsumoFigurinha> insumoFigurinhas = extrator.extraiConteudos(json);

            var geradora = new GeradoraDeFigurinhas();

            //exibir e manipular os dados/Gerar figurinhas
            insumoFigurinhas.forEach(insumoFigurinha -> {
                        String urlImagem = insumoFigurinha.getUrlImagem();

                        InputStream inputStream;
                        try {
                            inputStream = new URL(urlImagem).openStream();

                            geradora.cria(inputStream, insumoFigurinha.getTitulo() + ".png", AvaliacaoEnum.BRABO);
                        } catch (Exception ex) {
                            throw new RuntimeException("Erro ao criar figurinhas");
                        }

                        System.out.println(insumoFigurinha + "\n");
                    });
            System.out.println("\n");
        });
    }
}