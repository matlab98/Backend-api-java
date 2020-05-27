package com.restfulproyecto.restfulspringboot.Models.Decorator;

public abstract class Decorator implements SabanaTransport {
    private SabanaTransport decoratedTransport;

    public Decorator(SabanaTransport sabanaTransport) {
        this.decoratedTransport = sabanaTransport;
    }

    public String publish() {
        return decoratedTransport.publish();
    }

    public void setParams(String params) {
        decoratedTransport.setParams(params);
    }
}
