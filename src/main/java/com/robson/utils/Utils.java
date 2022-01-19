package com.robson.utils;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Utils {
  public static String CIPHER_AES_CBC = "AES/CBC/PKCS5Padding";
  public static int AES_KEY_SIZE = 256; // bits
  private Utils() {}
  public static String encode64(byte[] bytes) {
    return getEncoder().encodeToString(bytes);
  }
  public static String decode64(byte[] bytes) {
    return getDecoder().decode(bytes).toString();
  }
  public static String printKey(byte[] bytes) {
    StringBuffer sb = new StringBuffer();
    for (byte b: bytes) {
      sb.append(String.format("%02X ", b));
    }
    return sb.toString();
  }
  public static String obterSenhaUsuario() { return "senha12345"; }
  public static String bytesOfHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b: bytes) {
      sb.append(Integer.toHexString(0xff & b));
    }
    return sb.toString();
  }
}
