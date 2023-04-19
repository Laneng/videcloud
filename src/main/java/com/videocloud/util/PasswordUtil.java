package com.videocloud.util;
import org.apache.commons.codec.binary.Base64;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @Author fdy
 * @Date 2023/4/19 9:08 星期三
 * @Description
 */

public class PasswordUtil {




    /*加密*/

    public static String encode(String data, String key) throws Exception {
        String algorithm = "AES/CBC/PKCS5Padding";
        byte[] keyBytes = key.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(encrypted);
    }


    /*解密*/


    public static String decode(String encryptedData, String key) throws Exception {
        String algorithm = "AES/CBC/PKCS5Padding";
        byte[] encryptedBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = key.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted);
    }
}
