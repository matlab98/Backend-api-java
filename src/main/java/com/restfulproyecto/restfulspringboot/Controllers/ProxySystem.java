package com.restfulproyecto.restfulspringboot.Controllers;

import com.restfulproyecto.restfulspringboot.Models.Database;
import com.restfulproyecto.restfulspringboot.Models.User;
import com.restfulproyecto.restfulspringboot.Models.db;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.restfulproyecto.restfulspringboot.Models.Database;
import com.restfulproyecto.restfulspringboot.Models.db;

public class ProxySystem implements ILogin {
    Database db = new Database();
    db connection;

    {
        try {
            connection = new db(db.getURL(), db.getUSERNAME(), db.getPASSWORD());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //region Members
    private Map<String, String> users;

    private static ProxySystem instance;
    //endregion

    //region Private Constructor
    private ProxySystem() {
        users = new HashMap();
    }
    //endregion

    //region public methods
    public static ProxySystem getInstance() {
        if (instance == null) instance = new ProxySystem();
        return instance;
    }

    public void registerUser(User user) {
        if (users.containsKey(user.getEmail())) return;
        try {
            connection.createUsuario(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        users.put(user.getEmail(), user.getPassword());
    }

    @Override
    public BigInteger authenticate(User user) throws RemoteException, UnknownHostException {
        try {
            if(connection.authenticate(user)){
                users.put(user.getEmail(), user.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!users.containsKey(user.getEmail())) throw new RemoteException("El usuario no existe!");

        String password = users.get(user.getEmail());

        if (!password.equals(user.getPassword())) throw new RemoteException("Las credenciales son invalidas!");

        final int nonceLength = 256;

        BigInteger nonce = BigInteger.probablePrime(nonceLength, new SecureRandom());
        SystemSabana systemSabana = SystemSabana.getInstance();
        systemSabana.authenticate(nonce);
        return nonce;
    }

    //endregion

}
