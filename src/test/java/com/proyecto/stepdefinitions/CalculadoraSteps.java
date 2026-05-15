// Archivo: src/test/java/com/proyecto/stepdefinitions/CalculadoraSteps.java
package com.proyecto.stepdefinitions;

import com.proyecto.questions.ElCodigoDeRespuesta;
import com.proyecto.questions.ElCuerpoDeRespuesta;
import com.proyecto.tasks.LlamarCalculadoraSoap;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CalculadoraSteps {

    // El Actor se comparte entre todos los steps del mismo Scenario.
    // Es el "personaje" que ejecuta las acciones — su nombre aparece
    // en cada paso del reporte de Serenity.
    private Actor actor;

    // ── Hooks ────────────────────────────────────────────────────────────

    // @Before: se ejecuta ANTES de cada Scenario.
    // io.cucumber.java.Before — este import siempre va en inglés
    // porque es un hook del ciclo de vida, no una palabra de Gherkin.
    @Before
    public void configurarEscenario() {
        // OnlineCast permite crear Actores por nombre bajo demanda.
        OnStage.setTheStage(new OnlineCast());
        // El nombre del Actor aparece en el reporte: "Sistema QA intenta..."
        actor = OnStage.theActorCalled("Sistema QA");
    }

    // @After: se ejecuta DESPUÉS de cada Scenario, pase o falle.
    // Recibe el objeto Scenario para saber si falló.
    @After
    public void limpiarEscenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Aquí puedes agregar capturas adicionales o logs de debug.
            // Serenity ya captura automáticamente el estado en el reporte.
            System.out.println(">>> Scenario fallido: " + scenario.getName());
        }
        // Limpia el Actor del ThreadLocal — importante para ejecución paralela.
        OnStage.drawTheCurtain();
    }

    // ── Steps en español ─────────────────────────────────────────────────

    // Mapea la línea del Background en el .feature:
    // "Dado el servicio SOAP de calculadora está disponible"
    @Dado("el servicio SOAP de calculadora está disponible")
    public void elServicioEstaDisponible() {
        // Paso documentativo — la URL real viene de serenity.properties.
        // Si quisieras hacer un health-check antes de cada Scenario,
        // aquí llamarías a una Task de verificación.
    }

    // Mapea:
    // "Cuando envío un POST SOAP con la operación "Add" y los valores "10" y "5""
    // Cada {string} captura un valor entre comillas del .feature
    // y lo pasa como parámetro al método en el mismo orden.
    @Cuando("envío un POST SOAP con la operación {string} y los valores {string} y {string}")
    public void envioPostSoap(String operacion, String valorA, String valorB) {
        actor.attemptsTo(
                LlamarCalculadoraSoap.conLaOperacion(operacion, valorA, valorB)
        );
    }

    // Mapea:
    // "Entonces el código de respuesta HTTP debe ser "200""
    @Entonces("el código de respuesta HTTP debe ser {string}")
    public void elCodigoDeRespuestaEs(String codigoEsperado) {
        // seeThat() le pregunta al Actor la Question y compara con el matcher.
        // Si falla, Serenity muestra: Expected "200" but was "500"
        actor.should(
                seeThat(ElCodigoDeRespuesta.actual(), equalTo(codigoEsperado))
        );
    }

    // Mapea:
    // "Y el cuerpo de la respuesta XML debe contener el valor "15""
    @Y("el cuerpo de la respuesta XML debe contener el valor {string}")
    public void elCuerpoXmlContiene(String valorEsperado) {
        // containsString() busca el valor en cualquier parte del XML.
        // Más flexible que equalTo() para respuestas SOAP que tienen
        // namespaces y etiquetas extra alrededor del valor.
        actor.should(
                seeThat(ElCuerpoDeRespuesta.actual(), containsString(valorEsperado))
        );
    }

    // ── Escenario negativo ───────────────────────────────────────────────

    // Mapea:
    // "Cuando envío un POST SOAP con un XML inválido al servicio calculadora"
    @Cuando("envío un POST SOAP con un XML inválido al servicio calculadora")
    public void envioXmlInvalido() {
        // Enviamos una operación que no existe — el servicio devolverá
        // un HTTP 500 con un SOAP Fault en el XML de respuesta.
        actor.attemptsTo(
                LlamarCalculadoraSoap.conLaOperacion("OperacionInexistente", "abc", "xyz")
        );
    }

    // Mapea:
    // "Entonces el código de respuesta HTTP debe ser "500""
    // Reutiliza el mismo @Entonces de arriba — Cucumber lo empareja
    // automáticamente porque la expresión {string} es la misma.

    // Mapea:
    // "Y el cuerpo de la respuesta XML debe contener "soap:Fault""
    // También reutiliza el @Y de arriba por la misma razón.
}