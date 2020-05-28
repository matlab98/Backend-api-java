package com.restfulproyecto.restfulspringboot.Models;
import java.sql.*;


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
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        String SQL = "select fullname,address,phone from users where mail='"+user.getEmail()+"' and password='"+user.getPassword()+"'";
        Statement st = con.createStatement();;
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

    public boolean createUsuario(User user) throws SQLException {
        boolean abc = false;

        String SQL = "insert into users (fullname, address, phone, id_zone, mail, password)" +
                " values (?, ?,?,(select id from zone where name=?),?,?);";

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
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        abc = true;
                    } else {abc = false;}
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //return id;
        return abc;
    }

    public boolean createBus(Bus bus) throws SQLException {
        boolean abc = false;

        String SQL = "insert into Bus (id_zone, seat, placa, reference)" +
                " values ((select id from zone where name=?),?,?,?);";

        long id = 0;

        try (
                PreparedStatement pstmt = con.prepareStatement(SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "suba");

            pstmt.setInt(2, bus.getSeats());
            pstmt.setString(3, bus.getPlate());
            pstmt.setString(4, bus.getReference());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        abc = true;
                    } else {abc = false;}
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //return id;
        return abc;
    }

    public boolean createTren(Tren tren) throws SQLException {
        boolean abc = false;

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
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                        abc = true;
                    } else {abc = false;}
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //return id;
        return abc;
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

    public Bus searchBus(Bus bus, Route route) throws SQLException {

System.out.println(route);
        try(Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("select * from bus;")) {

            while (rs.next()){
                bus.setPlate(rs.getString("placa"));
                bus.setSeats(rs.getInt("seat"));
                bus.setReference(rs.getString("reference"));
            }
            }

        return bus;
    }

}
