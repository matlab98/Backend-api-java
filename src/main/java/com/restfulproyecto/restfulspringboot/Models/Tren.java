package com.restfulproyecto.restfulspringboot.Models;

public class Tren {
    private int seats;
    private String reference;

    public Tren(int seats, String reference) {
        this.seats = seats;
        this.reference = reference;
    }

    public Tren() {

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
}
