package com.restfulproyecto.restfulspringboot.Models;

import com.restfulproyecto.restfulspringboot.Models.Decorator.SabanaTransport;

public class Bus implements SabanaTransport {

    //region Members
    private String plate;
    private int seats;
    private String reference;
    //endregion

    //region Constructors
    public Bus() {}

    public Bus(String plate, int seats, String reference) {
        this.plate = plate;
        this.seats = seats;
        this.reference = reference;
    }
    //endregion

    //region Overridden methods
    @Override
    public String publish() {
        return "El bus publicado tiene las siguientes caracteristicas: \n" +
                "Placa: " + plate + "\n" +
                "Cupos: " + seats + "\n" +
                "Referencia: " + reference;
    }

    @Override
    public void setParams(String params) {

    }
    //endregion

    //region Public methods

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    //endregion
}
