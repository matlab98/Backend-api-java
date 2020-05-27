package com.restfulproyecto.restfulspringboot.Models.Decorator;

public class DecoratorDriverName extends Decorator {

    private String driverName;

    public DecoratorDriverName(SabanaTransport sabanaTransport, String param) {
        super(sabanaTransport);
        setParams(param);
        sabanaTransport.setParams(param);
    }

    public DecoratorDriverName(SabanaTransport sabanaTransport) {
        super(sabanaTransport);
    }

    public String publish() {
        return super.publish() + "\nConductor: " + driverName;
    }

    public void setParams(String params) {
        final String separator = ",";
        String [] param =  params.split(separator);
        driverName = param[0];
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
