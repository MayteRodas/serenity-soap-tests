package com.proyecto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElCuerpoDeRespuesta implements Question<String> {

    public static ElCuerpoDeRespuesta actual() {
        return new ElCuerpoDeRespuesta();
    }

    @Override
    public String answeredBy(Actor actor) {
        // El XML completo de la respuesta SOAP
        return actor.recall("responseBody");
    }
}