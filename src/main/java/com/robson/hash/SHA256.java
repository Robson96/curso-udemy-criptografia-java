package com.robson.hash;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * @author Robson Magno
 * @version 19/01/2022
 */
public class SHA256 {
  public static void main(String[] args) throws NoSuchAlgorithmException {
    // digest = resumo em portugues
    String password = "senha123";
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
    System.out.println(HexFormat.of().formatHex(digest));
  }
}
