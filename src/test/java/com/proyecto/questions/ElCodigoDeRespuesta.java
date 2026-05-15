package com.proyecto.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElCodigoDeRespuesta implements Question<String> {

    public static ElCodigoDeRespuesta actual() {
        return new ElCodigoDeRespuesta();
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.recall("httpStatusCode");
    }
}