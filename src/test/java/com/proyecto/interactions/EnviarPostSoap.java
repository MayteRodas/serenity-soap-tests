package com.proyecto.interactions;

import com.proyecto.entities.CalculadoraEntity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.annotations.Step;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

        // 2. Leer la URL base desde serenity.properties con Java puro
        //    getClassLoader().getResourceAsStream busca el archivo
        //    dentro del classpath — Gradle incluye la raíz del proyecto.
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("serenity.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer serenity.properties", e);
        }

        String baseUrl = props.getProperty("soap.base.url",
                "http://www.dneonline.com/calculator.asmx");
        //    Si la propiedad no existe usa la URL por defecto
        //    para que no falle en local aunque el archivo esté vacío.

        // 3. Ejecutar el POST SOAP
        Response response = RestAssured
                .given()
                .baseUri(baseUrl)
                .contentType("text/xml; charset=utf-8")
                .header("SOAPAction",
                        "\"http://tempuri.org/" + operacion + "\"")
                .body(soapBody)
                .when()
                .post()
                .then()
                .extract()
                .response();

        // 4. Guardar la respuesta en la memoria del Actor
        actor.remember("httpStatusCode", String.valueOf(response.getStatusCode()));
        actor.remember("responseBody",   response.getBody().asString());
    }
}