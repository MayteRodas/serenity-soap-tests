package com.proyecto.interactions;

import com.proyecto.entities.CalculadoraEntity;
import net.serenitybdd.rest.SerenityRest;          // ✅ reemplaza RestAssured
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;

public class EnviarPostSoap implements Interaction {

    private final String operacion;
    private final String valorA;
    private final String valorB;

    private EnviarPostSoap(String operacion, String valorA, String valorB) {
        this.operacion = operacion;
        this.valorA    = valorA;
        this.valorB    = valorB;
    }

    public static EnviarPostSoap paraLaOperacion(String operacion,
                                                 String valorA,
                                                 String valorB) {
        return new EnviarPostSoap(operacion, valorA, valorB);
    }

    @Step("{0} envía el POST SOAP para la operación '#operacion'")
    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Construir el XML usando la Entity
        String soapBody = CalculadoraEntity.createBody(operacion, valorA, valorB);

        // 2. Leer URL desde serenity.conf vía Serenity (no Java puro)
        String baseUrl = Serenity.environmentVariables()
                .getProperty("soap.base.url",
                        "http://www.dneonline.com/calculator.asmx");

        // 3. Ejecutar el POST SOAP con SerenityRest (no RestAssured directo)
        SerenityRest.given()                        // ✅ SerenityRest
                .baseUri(baseUrl)
                .contentType("text/xml; charset=utf-8")
                .header("SOAPAction",
                        "\"http://tempuri.org/" + operacion + "\"")
                .body(soapBody)
                .when()
                .post();

        // 4. Guardar la respuesta en la memoria del Actor
        actor.remember("httpStatusCode",
                String.valueOf(SerenityRest.lastResponse().getStatusCode()));
        actor.remember("responseBody",
                SerenityRest.lastResponse().getBody().asString());
    }
}