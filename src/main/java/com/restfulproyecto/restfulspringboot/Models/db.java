package com.restfulproyecto.restfulspringboot.Models;


import com.google.gson.Gson;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class db implements AutoCloseable{

    private Connection con;
    public db(String uri, String user, String password) throws SQLException {
        con = DriverManager.getConnection(uri, user, password);
    }

    @Override
    public void close() throws Exception {
        con.close();
    }

    public boolean authenticate(User user) throws SQLException {

        boolean abc=true;
        Statement st;

        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        String SQL = "select fullname,address,phone from users where mail='"+user.getEmail()+"' and password='"+user.getPassword()+"'";
        st = con.createStatement();
        try(ResultSet rs = st.executeQuery(SQL)) {
            if (rs.next()) {

                user.setName(rs.getString("fullname"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                System.out.println("perra");

               return abc = true;
            } else {return abc=false;}
        }
    }
public boolean tokenizer(User user) {

        boolean abc = false;
    System.out.println(user.getKey());
    String SQL = "update token set token= '"+user.getKey()+"' where id= '"+user.getEmail()+"'";

    long id = 0;
    try (
            PreparedStatement pstmt = con.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {



        int affectedRows = pstmt.executeUpdate();
        // check the affected rows

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    //return id;
    return abc;
}
    public boolean createUsuario(String llave, User user) throws SQLException {
        Boolean abc = false;
        if(autenticando(llave).next()){
    String SQL = "insert into users (fullname, address, phone, id_zone, mail, password) values (?, ?,?,(select id from zone where name=?),?,?); insert into token (id, token) values ('"+user.getEmail()+"','abc');";
    long id = 0;
    try (
            PreparedStatement pstmt = con.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS)) {

        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getAddress());
        pstmt.setString(3, user.getPhone());
        pstmt.setString(4, "suba");
        pstmt.setString(5, user.getEmail());
        pstmt.setString(6, user.getPassword());
        int affectedRows = pstmt.executeUpdate();
        // check the affected rows
        if (affectedRows > 0) {
            // get the ID back
            try (ResultSet rsa = pstmt.getGeneratedKeys()) {
                if (rsa.next()) {
                    id = rsa.getLong(1);
                    abc = true;
                } else {abc = false;}
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
        //return id;
        return abc;
    }
public ResultSet autenticando (String llave) throws SQLException {
        Boolean abc = false;
    Statement st;
    String qwerty="select * from token where token='"+llave+"'";
    st = con.createStatement();
    ResultSet rs = st.executeQuery(qwerty);
        return rs;

}
    public Boolean createBus(String llave,Bus bus) throws SQLException {

       if(autenticando(llave).next()){
        String SQL = "insert into Bus (id_zone, seat, placa, reference)" +
                " values ((select id from zone where name=?),?,?,?);";

        long id = 0;
        try (PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "suba");
            pstmt.setInt(2, bus.getSeats());
            pstmt.setString(3, bus.getPlate());
            pstmt.setString(4, bus.getReference());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
return true;
        } } else {
           return false;
       }
    }

    public Boolean createTren(String llave,Tren tren) throws SQLException {
        if(autenticando(llave).next()){

        String SQL = "insert into tren (id_zone, seat, reference)" +
                " values ((select id from zone where name=?),?,?);";

        long id = 0;

        try (
                PreparedStatement pstmt = con.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "suba");
            pstmt.setInt(2, tren.getSeats());
            pstmt.setString(3, tren.getReference());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
return true;
        }} else {
            return false;
        }
    }

    public boolean addZone(final String user, final String pass) throws SQLException {
        boolean abc;
        try(Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from users where mail='"+user+"' and password='"+pass+"'")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
                abc = true;
            } else {abc = false;}
        }
        return abc;
    }

    public Route SearchZone() throws SQLException {
        boolean abc;
        Route route = new Route();
        try(Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from zone;")) {

            while (rs.next()){
                route.setDestino(rs.getString("name"));
            }
            if (rs.next()) {
                System.out.println(rs.getString(1));
                abc = true;
            } else {abc = false;}
        }
        return route;
    }

    public Route SearchUserZone(Route route) throws SQLException {
        boolean abc;
        try(Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from user where zone "+ route.getDestino())) {

            while (rs.next()){
                route.setDestino(rs.getString("name"));
            }
            if (rs.next()) {
                System.out.println(rs.getString(1));
                abc = true;
            } else {abc = false;}
        }
        return route;
    }

    public String searchBus(String llave, Bus bus, Route route) throws SQLException {
        List<Bus> dataList = new LinkedList<>();
String json = null;
        Gson gson = new Gson();


        if(autenticando(llave).next()){

            try (Statement sta = con.createStatement(); ResultSet rss = sta.executeQuery("select * from bus;")) {

                while (rss.next()) {

                    bus.setPlate(rss.getString("placa"));
                    bus.setSeats(rss.getInt("seat"));
                    bus.setReference(rss.getString("reference"));
                    dataList.add(new Bus(rss.getString("placa"),rss.getInt("seat"), rss.getString("reference")));
                   json = gson.toJson(dataList);

                }

                return json;
            }} else {
            return null;
        }

        }

    public String searchTren(String llave, Tren tren) throws SQLException {
        List<Bus> dataList = new LinkedList<>();
        String json = null;
        Gson gson = new Gson();
        if(autenticando(llave).next()){
                try (Statement sta = con.createStatement();
                     ResultSet rss = sta.executeQuery("select * from tren;")) {

                    while (rss.next()) {
                        tren.setSeats(rss.getInt("seat"));
                        tren.setReference(rss.getString("reference"));
                        json = gson.toJson(dataList);
                    }
                    return json;
                }} else {
            return null;
        }
        }
    }

