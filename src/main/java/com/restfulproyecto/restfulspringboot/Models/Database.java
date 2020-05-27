package com.restfulproyecto.restfulspringboot.Models;

public class Database {
    private String URL;
    private String HOST = "localhost";
    private String PORT = "5432";
    private String USERNAME;
    private String PASSWORD;
    private String DATABASE="postgres";

    public Database() {
        this.URL = "jdbc:postgresql://"+HOST+":"+PORT+"/"+DATABASE;
        this.USERNAME = "postgres";
        this.PASSWORD = "root";
    }

    public String getURL() {
        return URL;
    }

    public String getHOST() {
        return HOST;
    }

    public String getPORT() {
        return PORT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
