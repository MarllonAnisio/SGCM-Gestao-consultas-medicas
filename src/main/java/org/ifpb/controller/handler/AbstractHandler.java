package org.ifpb.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;


public abstract class AbstractHandler implements HttpHandler {

    protected final ObjectMapper objectMapper;

    public AbstractHandler() {
        objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

    }
    protected <T> T readBody(HttpExchange exchange, Class<T> clazz) throws IOException {
       try(InputStream inputStream = exchange.getRequestBody()){
           return objectMapper.readValue(inputStream, clazz);
       }
    }

}
