package com.robson.simetricas;

import com.robson.utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Robson Magno
 * @version 14/01/2022
 */
public class AES {

  private static String CIPHER = "AES/CBC/PKCS5Padding";
  private static int KEY_SIZE = 256; // bits

  public static void main(String[] args) throws NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

    byte[] iv = new byte[16];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(iv);

    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(KEY_SIZE);
    SecretKey secretKey = keyGenerator.generateKey();

    System.out.println("Chave: " + Utils.printKey(secretKey.getEncoded()));

    Cipher cipher = Cipher.getInstance(CIPHER);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

    String msg = "Importante!!!";
    byte[] bytesMsg = msg.getBytes(StandardCharsets.UTF_8);
    byte[] msgCodificada = cipher.doFinal(bytesMsg);

    System.out.println("Menssagem: " + msg);
    System.out.println("Menssagem codificada: " + Utils.encode64(msgCodificada));

    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

    byte[] msgDecodificada = cipher.doFinal(msgCodificada);

    System.out.println("Menssagem decodificada: " + new String(msgDecodificada));
  }
}
