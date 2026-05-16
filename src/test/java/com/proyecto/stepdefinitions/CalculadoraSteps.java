package com.proyecto.stepdefinitions;

import com.proyecto.questions.ElCodigoDeRespuesta;
import com.proyecto.questions.ElCuerpoDeRespuesta;
import com.proyecto.tasks.LlamarCalculadoraSoap;
import io.cucumber.java.Before;
import io.cucumber.java.es.*;
import io.cucumber.java.es.Y;
import io.cucumber.java.es.E;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class CalculadoraSteps {

    private static final String BASE_URL = "http://www.dneonline.com/calculator.asmx";
    private Actor actor;

    @Before
    public void configurarEscenario() {
        // Cast REST puro — sin WebDriver, sin Edge, sin browser
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(BASE_URL)));
        actor = OnStage.theActorCalled("Sistema QA");
    }

    @Dado("el servicio SOAP de calculadora está disponible")
    public void elServicioEstaDisponible() { }

    @Cuando("envío un POST SOAP con la operación {string} y los valores {string} y {string}")
    public void envioPostSoap(String operacion, String valorA, String valorB) {
        actor.attemptsTo(LlamarCalculadoraSoap.conLaOperacion(operacion, valorA, valorB));
    }

    @Entonces("el código de respuesta HTTP debe ser {string}")
    public void elCodigoDeRespuestaEs(String codigoEsperado) {
        actor.should(seeThat(ElCodigoDeRespuesta.actual(), equalTo(codigoEsperado)));
    }

    // ← Agrega AMBAS anotaciones para cubrir "Y" y "And"
    @Entonces("el cuerpo de la respuesta XML debe contener el valor {string}")
    public void elCuerpoXmlContiene(String valorEsperado) {
        actor.should(
                seeThat(ElCuerpoDeRespuesta.actual(), containsString(valorEsperado))
        );
    }

    @Cuando("envío un POST SOAP con un XML inválido al servicio calculadora")
    public void envioXmlInvalido() {
        actor.attemptsTo(LlamarCalculadoraSoap.conLaOperacion("OperacionInexistente", "abc", "xyz"));
    }
}