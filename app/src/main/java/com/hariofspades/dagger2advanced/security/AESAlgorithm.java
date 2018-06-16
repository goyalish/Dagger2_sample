package com.hariofspades.dagger2advanced.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class AESAlgorithm {
    private static final String TOKEN = "passwd";
    private String salt;
    private int pwdIterations = 65536;
    private int keySize = 256;
    private byte[] ivBytes;
    private String keyAlgorithm = "AES";
    private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
    private String secretKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";

    public AESAlgorithm(){
        this.salt = getSalt();
    }

    private String getSalt(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String text = new String(bytes);
        return text;
    }

    /**
     *
     * @param plainText
     * @return encrypted text
     * @throws Exception
     */
    public String encyrpt(String plainText) throws Exception{
        //generate key
        byte[] saltBytes = salt.getBytes("UTF-8");

        SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
        PBEKeySpec spec = new PBEKeySpec(TOKEN.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
        SecretKey secretKey = skf.generateSecret(spec);
        SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);

        //AES initialization
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //generate IV
        this.ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));

        return Base64.encodeToString(encryptedText,Base64.DEFAULT);
        //return new Base64().encodeAsString(encryptedText);
    }

    /**
     *
     * @param encryptText
     * @return decrypted text
     * @throws Exception
     */
   /* public String decrypt(String encryptText) throws Exception {
        byte[] saltBytes = salt.getBytes("UTF-8");
        byte[] encryptTextBytes = new Base64().decode(encryptText);

        SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
        PBEKeySpec spec = new PBEKeySpec(TOKEN.toCharArray(), saltBytes, this.pwdIterations, this.keySize);
        SecretKey secretKey = skf.generateSecret(spec);
        SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), keyAlgorithm);

        //decrypt the message
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

        byte[] decyrptTextBytes = null;
        try {
            decyrptTextBytes = cipher.doFinal(encryptTextBytes);
        } catch (IllegalBlockSizeException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        String text = new String(decyrptTextBytes);
        return text;
    }*/

    private static final String key = "mBI+LWhYkOjClxjfuUrFZHHYfO17lBgRh4bLnVuuLRQ=";
    private static final String initVector = "hhMtWk0xUL6sFlkOrw9hXA==";

    /*public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(decodeBase64(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(decodeBase64(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted,Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedString) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            SecretKeySpec key2 = new SecretKeySpec(decodeBase64(key), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key2,new IvParameterSpec(decodeBase64(initVector)));
            return new String(cipher.doFinal(Base64.decode(encryptedString,Base64.DEFAULT)),"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }*/

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            SecretKeySpec key2 = new SecretKeySpec(decodeBase64(key), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key2, new IvParameterSpec(decodeBase64(initVector)));
            return encodeBase64(cipher.doFinal(plainText.getBytes("UTF-8")));
        } catch (Exception e) {
            Log.e(AESAlgorithm.class.getSimpleName(), e.getMessage());
        }
        return "";
    }

    private static byte[] decodeBase64(String input) {
        return Base64.decode(input, Base64.DEFAULT);
    }

    private static String encodeBase64(byte[] input) {
        return Base64.encodeToString(input,Base64.DEFAULT);
    }
}