package com.restfulproyecto.restfulspringboot.Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoManager {

    //region Members
    private static CryptoManager cryptoManager;

    private byte [] salt = {32, 54, 32, 66, 23, 43, 65, 32};
    private byte[] iv;
    //endregion

    //region Private constructor
    private CryptoManager() {
    }
    //endregion

    //region public methods
    public static CryptoManager getInstance() {
        if (cryptoManager == null) cryptoManager = new CryptoManager();
        return cryptoManager;
    }

    public String encrypt(String message, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        final String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] iv_ = cipher.getIV();
        iv = iv_;
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
    }

    public String decrypt(String message, BigInteger nonce) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        if (iv == null) throw new RuntimeException();
        if (salt == null) throw new RuntimeException();

        final int iterationCount = 65536;
        final int keyLength = 256;
        final String keyGenerator = "PBKDF2WithHmacSHA256";
        final String algorithm = "AES";
        final String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(keyGenerator);
        KeySpec keySpec = new PBEKeySpec(nonce.toString().toCharArray(), salt, iterationCount, keyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), algorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return new String(cipher.doFinal(Base64.getDecoder().decode(message.getBytes())));
    }

    public SecretKey deriveKey(char [] nonce) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int iterationCount = 65536;
        final int keyLength = 256;
        final String keyGenerator = "PBKDF2WithHmacSHA256";
        final String algorithm = "AES";

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(keyGenerator);

        KeySpec keySpec = new PBEKeySpec(nonce, salt, iterationCount, keyLength);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return new SecretKeySpec(secretKey.getEncoded(), algorithm);
    }
    //endregion
}
