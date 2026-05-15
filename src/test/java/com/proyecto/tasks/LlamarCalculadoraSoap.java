
package com.proyecto.tasks;

import com.proyecto.interactions.EnviarPostSoap;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.annotations.Step;

// Task es la capa de intención — describe QUÉ hace el actor, no CÓMO.
// Una Task puede contener varias Interactions si la acción es compleja.
public class LlamarCalculadoraSoap implements Task {

    private final String operacion;
    private final String valorA;
    private final String valorB;

    private LlamarCalculadoraSoap(String operacion, String valorA, String valorB) {
        this.operacion = operacion;
        this.valorA    = valorA;
        this.valorB    = valorB;
    }

    // Método de fábrica — se llama desde el Step Definition.
    // Se lee casi como inglés/español natural: "con la operacion Add y valores..."
    public static LlamarCalculadoraSoap conLaOperacion(String operacion,
                                                       String valorA,
                                                       String valorB) {
        return Tasks.instrumented(LlamarCalculadoraSoap.class,
                operacion, valorA, valorB);
        // Tasks.instrumented() es la forma correcta de crear Tasks en Serenity
        // — le permite a Serenity interceptar y registrar la ejecución.
    }

    @Step("{0} llama al servicio SOAP calculadora con operación '#operacion'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        // La Task delega el trabajo real en la Interaction.
        // Si en el futuro necesitas autenticarte primero, agregas otra
        // Interaction aquí, antes de EnviarPostSoap, sin tocar el Step Definition.
        actor.attemptsTo(
                EnviarPostSoap.paraLaOperacion(operacion, valorA, valorB)
        );
    }
}