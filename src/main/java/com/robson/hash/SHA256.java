package com.robson.hash;

import com.robson.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.robson.utils.Utils.*;

/**
 * @author Robson Magno
 * @version 19/01/2022
 */
public class SHA256 {
  public static void main(String[] args) throws NoSuchAlgorithmException {
    // digest = resumo em portugues
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] digest = md.digest(obterSenhaUsuario().getBytes(StandardCharsets.UTF_8));
    System.out.println(Utils.bytesOfHex(digest));
  }
}
