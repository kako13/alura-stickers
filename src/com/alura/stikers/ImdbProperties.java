package com.alura.stikers;


import java.util.*;


public class ImdbProperties {

    public static Map<String, String> carregarEndPoints() {

        ResourceBundle bundle = ResourceBundle.getBundle("imdb");

        String baseBundleName = bundle.getBaseBundleName();

        Enumeration<String> keys = bundle.getKeys();

        HashMap<String, String> mapChaveURI = new LinkedHashMap<>();

        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            String uriNome = bundle.getString(nextElement);

            List<String> valueList = Arrays.stream(uriNome.split(", ")).toList();

            mapChaveURI.put(valueList.get(0), valueList.get(1));
        }

        return mapChaveURI;
    }
}
