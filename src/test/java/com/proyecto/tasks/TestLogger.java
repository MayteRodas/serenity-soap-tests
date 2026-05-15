package com.proyecto.tasks;

import net.thucydides.core.annotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {

    private static final Logger log = LoggerFactory.getLogger(TestLogger.class);

    // Registra el inicio de una interacción con sus parámetros.
    // El @Step hace que Serenity lo muestre como un paso en el reporte HTML.
    @Step("Iniciando interacción: {0}")
    public static void interactionStart(String nombre, Object... params) {
        log.info(">>> Interacción: {} | Parámetros: {}", nombre, params);
    }

    // Registra un dato de prueba.
    // Si 'enmascarar' es true, oculta el valor real (útil para tarjetas, passwords).
    @Step("Dato: {0} = {1}")
    public static void testData(String campo, String valor, boolean enmascarar) {
        String valorMostrado = enmascarar
                ? valor.substring(0, Math.min(4, valor.length())) + "****"
                : valor;
        log.info("    [dato] {} = {}", campo, valorMostrado);
    }
}