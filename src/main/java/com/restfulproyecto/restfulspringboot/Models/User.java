package com.restfulproyecto.restfulspringboot.Models;

import com.restfulproyecto.restfulspringboot.Models.Composite.Component;

public class User implements Component {

    //region Members
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    //endregion

    //region Constructors
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.name = "";
        this.address = "";
        this.phone = "";
    }

    public User(String email, String password, String name ,String address, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    //endregion

    //region Overridden methods
    @Override
    public String getInfo() {
        return name + " con correo electronico " + email + "\n";
    }
    //endregion

    //region public methods
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //endregion

}
