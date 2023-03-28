package com.alura.stikers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    private static final Pattern REGEX_ITENS = Pattern.compile(".*\\[(.+)\\].*");
    private static final Pattern REGEX_ATRIBUTOS_JSON = Pattern.compile("\"(.+?)\":\"(.*?)\"");
    private static final Pattern REGEX_DELIMITADOR_ITENS = Pattern.compile("\\}.\\{");

    public static List<Map<String, String>> parser(String json) {

        Matcher matcher = REGEX_ITENS.matcher(json);

        if (!matcher.find())
            throw new IllegalArgumentException("Não encontrou itens");

        String[] itens = matcher.group(1).split(REGEX_DELIMITADOR_ITENS.pattern());

        List<Map<String, String>> dados = new ArrayList<>();

        Arrays.stream(itens)
                .forEach(e -> {

                    HashMap<String, String> atributosItem = new HashMap<>();
                    Matcher matcherAtributosJson = REGEX_ATRIBUTOS_JSON.matcher(e);

                    while (matcherAtributosJson.find()) {
                        String atributo = matcherAtributosJson.group(1);
                        String valor = matcherAtributosJson.group(2);
                        atributosItem.put(atributo, valor);
                    }

                    dados.add(atributosItem);
                });

        return dados;
    }
}
