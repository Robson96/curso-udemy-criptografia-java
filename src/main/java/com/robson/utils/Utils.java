package com.robson.utils;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Utils {
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
}
