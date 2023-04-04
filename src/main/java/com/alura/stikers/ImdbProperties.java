package com.alura.stikers;


import java.util.*;


public class ImdbProperties {

    public static HashMap<String, Fonte> carregarEndPoints() {

        ResourceBundle bundle = ResourceBundle.getBundle("apis");

        //Metodo getkeys nao retorna ordenado
        Enumeration<String> keys = bundle.getKeys();

        HashMap<String, Fonte> mapChaveURI = new LinkedHashMap<>();

        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            String propertyValue = bundle.getString(nextElement);

            List<String> valueList = Arrays.stream(propertyValue.split(", ")).toList();

            mapChaveURI.put(valueList.get(0), new Fonte(valueList.get(1), valueList.get(2)));
        }

        return mapChaveURI;
    }
}
