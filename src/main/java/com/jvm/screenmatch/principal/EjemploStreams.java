package com.jvm.screenmatch.principal;


import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Orlando","Piero","Richard","Sofia","Gabriela","Sora");
        nombres.stream()
                .sorted()
                .filter(n->n.startsWith("S"))
                .limit(5)
                .map(String::toUpperCase)
                .forEach(System.out::println);

    }
}
