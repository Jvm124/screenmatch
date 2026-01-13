package com.jvm.screenmatch.service;

import com.jvm.screenmatch.model.DatosSerie;
import tools.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        }catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
