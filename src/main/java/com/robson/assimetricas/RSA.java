package com.robson.assimetricas;

import com.robson.utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA {
  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGenerator.initialize(1024);

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    System.out.println("Chave Privada: " + Utils.encode64(privateKey.getEncoded()));
    System.out.println("===========================================");
    System.out.println("Chave Publica: " + Utils.encode64(publicKey.getEncoded()));

    final String cipherName = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    Cipher cipher = Cipher.getInstance(cipherName);
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    String msg = "mensagem secreta!";

    byte[] msgCodificada = cipher.doFinal(msg.getBytes("utf-8"));

    System.out.println("Mensagem codificada: " + Utils.encode64(msgCodificada));

    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] msgDecodificada = cipher.doFinal(msgCodificada);

    System.out.println("Mensagem Decodificada: " + new String(msgDecodificada));
    System.out.println(msg.equals(new String(msgDecodificada)));
  }
}
