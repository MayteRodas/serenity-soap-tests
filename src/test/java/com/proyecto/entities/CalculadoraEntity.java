package com.proyecto.entities;

public class CalculadoraEntity {

    public static String createBody(String operacion,
                                    String valorA,
                                    String valorB) {

        String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body>"
                + "<" + operacion + " xmlns=\"http://tempuri.org/\">"
                + "<intA>" + valorA + "</intA>"
                + "<intB>" + valorB + "</intB>"
                + "</" + operacion + ">"
                + "</soap:Body>"
                + "</soap:Envelope>";

        return body;
    }
}