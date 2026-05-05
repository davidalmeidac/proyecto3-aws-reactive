package com.example.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Función Lambda para procesar pedidos.
 * Demuestra implementación de AWS Lambda con Java.
 */
public class ProcessOrderFunction implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("Procesando pedido: " + input);

        // Lógica de procesamiento del pedido
        String orderId = (String) input.get("orderId");
        
        // Simular procesamiento
        return Map.of(
            "orderId", orderId,
            "status", "PROCESSED",
            "message", "Pedido procesado exitosamente"
        );
    }
}

