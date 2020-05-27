package com.restfulproyecto.restfulspringboot.Models.Decorator;

public class DecoratorDestination extends Decorator {

    private String destination;

    public DecoratorDestination(SabanaTransport sabanaTransport) {
        super(sabanaTransport);
    }

    public DecoratorDestination(SabanaTransport sabanaTransport, String param) {
        super(sabanaTransport);
        setParams(param);
        sabanaTransport.setParams(param);
    }

    public String publish() {
        return super.publish() + "\nDestino: " + destination;
    }

    public void setParams(String params) {
        final String separator = ",";
        String [] param =  params.split(separator);
        destination = param[1];
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
