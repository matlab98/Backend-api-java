package com.restfulproyecto.restfulspringboot.Controllers;

import com.restfulproyecto.restfulspringboot.Models.Decorator.DecoratorDestination;
import com.restfulproyecto.restfulspringboot.Models.Decorator.DecoratorDriverName;
import com.restfulproyecto.restfulspringboot.Models.Decorator.SabanaTransport;
import com.restfulproyecto.restfulspringboot.Models.Bus;
import com.restfulproyecto.restfulspringboot.Models.Composite.Group;
import com.restfulproyecto.restfulspringboot.Models.User;
import com.restfulproyecto.restfulspringboot.Utils.CryptoManager;
import com.restfulproyecto.restfulspringboot.Utils.IPManager;
import javax.crypto.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class SystemSabana implements ILogin {

    //region Members
    private Map<String, BigInteger> auth;
    private Map<String, User> users;
    private Map<String, Group> groups;
    private List<SabanaTransport> transport;

    private static SystemSabana instance;

    //endregion

    //region Private constructor
    private SystemSabana() {
        auth = new HashMap();
        groups = new HashMap();
        users = new HashMap();
        transport = new ArrayList();
    }
    //endregion

    //region Public methods
    public static SystemSabana getInstance() {
        if (instance == null) instance = new SystemSabana();
        return instance;
    }

    @Override
    public BigInteger authenticate(User user) throws UnknownHostException {

        return authenticate(new BigInteger("0"));
    }

    public BigInteger authenticate(BigInteger nonce) throws UnknownHostException {
        auth.put(IPManager.getIpAddress(), nonce);
        return nonce;
    }

    public Object callMethod(String methodName, String ip) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, RemoteException, IndexOutOfBoundsException {


        if (!auth.containsKey(ip)) throw new RemoteException("El numero de autenticacion es invalido!");


        String methodNamePlainText = decrypt(methodName, ip);

        final String separator = ",";

        String [] params = methodNamePlainText.split(separator);

        if (params[0].equals("showBusRoutes")) {
            return getClass().getDeclaredMethod(params[0]).invoke(instance);
        }
        if (params[0].equals("createUser")) {
            if (params.length < 6) return null;
            return getClass().getDeclaredMethod(params[0], User.class).invoke(instance, new User(params[1], params[2], params[3], params[4], params[5]));
        }
        if (params[0].equals("addComponentToGroup")) {
            if (params.length < 7) return null;
            return getClass().getDeclaredMethod(params[0], User.class, String.class).invoke(instance, new User(params[1], params[2], params[3], params[4], params[5]), params[6]);
        }
        if (params[0].equals("searchGroup")) {
            if (params.length < 2) return null;
            return getClass().getDeclaredMethod(params[0], String.class).invoke(instance, params[1]);
        }
        if (params[0].equals("createBus")) {
            if (params.length < 6) return null;
            return getClass().getDeclaredMethod(params[0], String.class, int.class, String.class, String.class, String.class).invoke(instance, params[1], Integer.parseInt(params[2]), params[3], params[4], params[5]);
        }
        if (params[0].equals("createGroup")) {
            if (params.length < 2) return null;
            return getClass().getDeclaredMethod(params[0], String.class).invoke(instance, params[1]);
        }
        throw new RuntimeException("Metodo no implementado!");
    }

    //endregion

    //region Private methods
    private String decrypt(String message, String ip) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        BigInteger nonce = auth.get(ip);
        CryptoManager cryptoManager = CryptoManager.getInstance();
        return cryptoManager.decrypt(message, nonce);
    }

    private String showBusRoutes() {
        StringBuilder info = new StringBuilder("Bienvenido\n");

        for (SabanaTransport sabanaTransport:  transport) info.append(sabanaTransport.publish() + "\n");

        return info.toString();
    }

    private void createUser(User user) {
        if (users.containsKey(user.getEmail())) return;
        users.put(user.getEmail(), user);
    }

    private void addComponentToGroup(User user, String groupName) {
        //This search only considers a composite tree with depth 2
        if (!users.containsKey(user.getEmail())) return;

        if (groups.containsKey(groupName)) {
            Group group = groups.get(groupName);
            group.addComponent(user);
        } else {
            Group group = new Group(groupName);
            group.addComponent(user);
            groups.put(groupName, group);
        }
    }

    private void createGroup(String groupName) {
        if (groups.containsKey(groupName)) return;

        Group group = new Group(groupName);
        groups.put(groupName, group);
    }

    private String searchGroup(String groupName) {
        Group group = groups.get(groupName);

        if (group == null) return "";

        return group.getInfo();
    }

    private void createBus(String plate, int seats, String reference, String driverName, String destination) {
        final char separator = ',';
        String params = driverName + separator + destination;
        SabanaTransport bus = new DecoratorDestination(new DecoratorDriverName(new Bus(plate, seats, reference)), params);
        transport.add(bus);
    }
    //endregion
}
