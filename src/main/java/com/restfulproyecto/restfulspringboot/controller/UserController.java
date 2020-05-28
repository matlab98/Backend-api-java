package com.restfulproyecto.restfulspringboot.controller;

import com.restfulproyecto.restfulspringboot.Controllers.ProxySystem;
import com.restfulproyecto.restfulspringboot.Controllers.SystemSabana;
import com.restfulproyecto.restfulspringboot.Models.*;
import com.restfulproyecto.restfulspringboot.Utils.CryptoManager;
import com.restfulproyecto.restfulspringboot.Utils.IPManager;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Base64;

//endregion
@RestController
@RequestMapping("/user")
public class UserController {
	public static UserController instance;
	Database db = new Database();
	db connection;

	{
		try {
			connection = new db(db.getURL(), db.getUSERNAME(), db.getPASSWORD());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//region Public members

	private UserController() throws SQLException {}
	//endregion
	//region Public methods
	public static UserController getInstance() throws SQLException {
		if (instance == null) instance = new UserController();
		return instance;
	}
	@GetMapping("/login")
	public User askUser(@RequestParam("user") String email, @RequestParam("password") String pass) throws SQLException {

		    User user = new User(email, pass);

		SecretKey key = login(user);
System.out.println("login");
			if (key != null) {
				user.setKey(key);
				System.out.println(key.getEncoded());
				return user;
			}else {return null;}

	}

	@GetMapping("/createUser")
	public User createUser( @RequestParam("key") String llave, @RequestParam("user") String mail, @RequestParam("password") String pwd,
							 @RequestParam("name") String name, @RequestParam("address") String address,
							 @RequestParam("phone") String phone, @RequestParam("zone") String zone) {

		System.out.println("crear usuario");

		User usuario = new User(mail, pwd, name, address, phone);

		try {
			connection.createUsuario(usuario);
		} catch (SQLException e) {

		}

		SecretKey key = login(usuario);
		createUser(usuario, key);
		addUserToZone(usuario, zone, key);
		return usuario;
	}

	@GetMapping("/updateUser")
	public User updateUser( @RequestParam String llave, @RequestParam("user") String mail, @RequestParam("password") String pwd,
							 @RequestParam("address") String address,
							@RequestParam("phone") String phone, @RequestParam("zone") String zone) {

		System.out.println("actualizar usuario");



		return null;
	}

	@GetMapping("/searchUserZone")
	public User searchUserZone(@RequestParam("key") String llave, @RequestParam("zone") String zone) {

		System.out.println("Buscar usuario por zona");
// rebuild key using SecretKeySpec
/*
		User usuario = new User(mail, pwd, name, address, phone);

		try {
			connection.createUsuario(usuario);
		} catch (SQLException e) {

		}

		SecretKey key = login(usuario);
		createUser(usuario, key);
		addUserToZone(usuario, zone, key);*/
		return null;
	}


	@GetMapping("/createRoute")
	public String askRoute(@RequestParam("key") String llave,@RequestParam("route") String route,@RequestParam("start") String start,@RequestParam("stop") String stop,@RequestParam("hora") String dat) {

		System.out.println("Crear ruta");

		return "Lista";
	}

	@GetMapping("/askZoneSearch")
	public String askZoneSearch(@RequestParam("key") String llave) {

		System.out.println("Buscar zona");

		return "Lista";
	}

	@GetMapping("/createBus")
	public Bus askBus(@RequestParam("key") String llave, @RequestParam("placa") String placa, @RequestParam("seat") int seat,
						 @RequestParam("reference") String reference, @RequestParam("conductor") String name,
					  @RequestParam("destino") String destino){
		System.out.println("Crear bus");
        Bus bus = new Bus(placa, seat, reference);
		try {
			connection.createBus(bus);
		} catch (SQLException e) {

		}
		return bus;
	}

	@GetMapping("/searchBusRoute")
	public Bus searchBusRoute(@RequestParam("key") String llave, @RequestParam("route") String ruta) {
		System.out.println("Buscar bus ruta");
		Bus bus = new Bus();
		Route route = new Route();
		route.setDestino(ruta);

		try {
			connection.searchBus(bus,route);
		} catch (SQLException e) {

		}
		return bus;
	}

	@GetMapping("/updateBus")
	public Bus updateBus(@RequestParam("key") String llave, @RequestParam("placa") String placa, @RequestParam("seat") int seat,
						 @RequestParam("reference") String reference, @RequestParam("conductor") String name,
						 @RequestParam("destino") String destino) {
		System.out.println("Actualizar bus");
		Bus bus = new Bus();
		bus.setPlate(placa);

		return bus;
	}


	@GetMapping("/createTren")
	public Tren askTren(@RequestParam("key") String llave, @RequestParam("seat") int seat,
						@RequestParam("reference") String reference, @RequestParam("conductor") String name,
						@RequestParam("destino") String destino){
		System.out.println("Crear tren");
		Tren tren = new Tren (seat, reference);
		try {
			connection.createTren(tren);
		} catch (SQLException e) {

		}
	/*	Bus bus = new Bus(placa, seat, reference);
		try {
			connection.createBus(bus);
		} catch (SQLException e) {

		}
		return bus;*/return tren;
	}

	@GetMapping("/updateTren")
	public Bus updateTren(@RequestParam("key") String llave, @RequestParam("seat") int seat,
						 @RequestParam("reference") String reference, @RequestParam("conductor") String name,
						 @RequestParam("destino") String destino) {

		System.out.println("actualizar tren");

		/*Bus bus = new Bus();
		bus.setPlate(placa);
		try {
			connection.searchBus(bus);
		} catch (SQLException e) {

		}
		return bus;*/ return null;
	}



	@PutMapping("/showBusRoutes")
	public String showBusRoutes() {

		return "Usuario actualizado";
	}
























	private static SecretKey login(User user) {
		try {
			ProxySystem proxySystem = ProxySystem.getInstance();
			BigInteger nonce = proxySystem.authenticate(user);

			return deriveKey(nonce.toString().toCharArray());

		} catch (RemoteException | NoSuchAlgorithmException | InvalidKeySpecException | UnknownHostException e) {

			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static String showBusRoutes(SecretKey key) {
		try {
			String method = encrypt("showBusRoutes", key);
			SystemSabana systemSabana = SystemSabana.getInstance();
			return (String) systemSabana.callMethod(method, IPManager.getIpAddress());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| RemoteException | UnknownHostException e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static void createBus(String plate, int asientos, String reference, String driverName, String destination, SecretKey key) {
		try {
			final char separator = ',';
			String msg = "createBus" + separator + plate + separator + asientos + separator + reference + separator + driverName + separator + destination;
			String method = encrypt(msg,key);
			SystemSabana systemSabana = SystemSabana.getInstance();
			systemSabana.callMethod(method, IPManager.getIpAddress());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| RemoteException | UnknownHostException e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
	}

	private static void createUser(User user, SecretKey key) {
		try {
			final char separator = ',';
			String msg = "createUser" + separator  + user.getEmail() + separator + user.getPassword() +
					separator + user.getName() + separator + user.getAddress() + separator + user.getPhone();
			String method = encrypt(msg, key);
			System.out.println(method);
			SystemSabana systemSabana = SystemSabana.getInstance();
			systemSabana.callMethod(method, IPManager.getIpAddress());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| RemoteException | UnknownHostException e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());

		}
	}


	private static void addUserToZone(User user, String zone, SecretKey key) {
		try {
			final char separator = ',';
			String msg = "addComponentToGroup" + separator + user.getEmail() + separator + user.getPassword() +
					separator + user.getName() + separator + user.getAddress() + separator + user.getPhone() +
					separator + zone;
			String method = encrypt(msg, key);
			SystemSabana systemSabana = SystemSabana.getInstance();
			systemSabana.callMethod(method, IPManager.getIpAddress());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| RemoteException | UnknownHostException e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
	}

	private static String searchByZone(String zone, SecretKey key) {
		try {
			final char separator = ',';
			String msg = "searchGroup" + separator + zone;
			String method = encrypt(msg, key);
			System.out.print(method);
			SystemSabana systemSabana = SystemSabana.getInstance();
			return (String) systemSabana.callMethod(method, IPManager.getIpAddress());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| RemoteException | UnknownHostException e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static SecretKey deriveKey(char [] nonce) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return CryptoManager.getInstance().deriveKey(nonce);
	}

	private static String encrypt(String message, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		CryptoManager cryptoManager = CryptoManager.getInstance();
		return cryptoManager.encrypt(message, key);
	}


}
