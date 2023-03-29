package com.alura.stikers;


import java.util.*;


public class ImdbProperties {

    public static HashMap<String, String> carregarEndPoints() {

        ResourceBundle bundle = ResourceBundle.getBundle("imdb");

        //Metodo getkeys nao retorna ordenado
        Enumeration<String> keys = bundle.getKeys();

        HashMap<String, String> mapChaveURI = new LinkedHashMap<>();

        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            String propertyValue = bundle.getString(nextElement);

            List<String> valueList = Arrays.stream(propertyValue.split(", ")).toList();

            mapChaveURI.put(valueList.get(0), valueList.get(1));
        }

        return mapChaveURI;
    }
}
