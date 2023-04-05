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

        if (list.isEmpty())
            System.out.println("\n\u001b[38;2;255;251;11m\u001b[48;2;245;64;25mArquivo de propriedade 'apis.properties' vazio\u001b[m");

        return list;
    }
}
