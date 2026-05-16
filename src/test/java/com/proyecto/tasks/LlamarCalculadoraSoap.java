package com.proyecto.tasks;

import com.proyecto.interactions.EnviarPostSoap;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class LlamarCalculadoraSoap implements Task {

    private final String operacion;
    private final String valorA;
    private final String valorB;

    private LlamarCalculadoraSoap(String operacion, String valorA, String valorB) {
        this.operacion = operacion;
        this.valorA    = valorA;
        this.valorB    = valorB;
    }

    // ✅ new directo en lugar de Tasks.instrumented()
    public static LlamarCalculadoraSoap conLaOperacion(String operacion,
                                                       String valorA,
                                                       String valorB) {
        return new LlamarCalculadoraSoap(operacion, valorA, valorB);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                EnviarPostSoap.paraLaOperacion(operacion, valorA, valorB)
        );
    }
}