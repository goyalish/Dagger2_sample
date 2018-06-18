package com.hariofspades.dagger2advanced.security;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESAlgorithm {
    private static String keyAlgorithm = "AES";
    private static String encryptAlgorithm = "AES/CBC/PKCS7PADDING";
    private static final String key = "mBI+LWhYkOjClxjfuUrFZHHYfO17lBgRh4bLnVuuLRQ=";
    private static final String initVector = "hhMtWk0xUL6sFlkOrw9hXA==";

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            SecretKeySpec key2 = new SecretKeySpec(decodeBase64(key), keyAlgorithm);
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