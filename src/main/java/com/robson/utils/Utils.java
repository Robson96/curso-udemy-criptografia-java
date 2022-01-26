package com.robson.utils;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Utils {

  private Utils() {}

  public static String encodeBase64(byte[] bytes) {
    return getEncoder().encodeToString(bytes);
  }
  public static String decodeBase64(byte[] bytes) {
    return getDecoder().decode(bytes).toString();
  }
  public static String formatKey(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b: bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
