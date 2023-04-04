package com.alura.stikers.core.apis;


import java.util.*;


public class ApiPropertiesService {

    public static List<ApiExterna> getExternalAPIs() {

        ResourceBundle bundle = ResourceBundle.getBundle("apis");

        //Metodo getkeys nao retorna ordenado
        Enumeration<String> keys = bundle.getKeys();

        List<ApiExterna> list = new ArrayList<>();

        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            String propertyValue = bundle.getString(nextElement);

            List<String> valueList = Arrays.stream(propertyValue.split(", ")).toList();

            list.add(new ApiExterna(valueList.get(0), valueList.get(1), valueList.get(2)));
        }

        return list;
    }
}
