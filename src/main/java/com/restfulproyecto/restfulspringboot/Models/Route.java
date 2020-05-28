package com.restfulproyecto.restfulspringboot.Models;

import sun.util.calendar.BaseCalendar;

public class Route {
    private BaseCalendar.Date date;
    private String start;
    private String stop;
    private String Destino;

    public Route(BaseCalendar.Date date, String start, String stop, String destino) {
        this.date = date;
        this.start = start;
        this.stop = stop;
        Destino = destino;
    }

    public BaseCalendar.Date getDate() {
        return date;
    }

    public void setDate(BaseCalendar.Date date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }
}
