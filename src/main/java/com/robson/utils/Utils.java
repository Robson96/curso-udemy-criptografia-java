package com.robson.utils;

import java.util.Arrays;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Utils {
  public final static String CIPHER_AES = "AES/CBC/PKCS5Padding";
  public final static String CIPHER_RSA = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

  private Utils() {}

  public static String encodeBase64(byte[] bytes) {
    return getEncoder().encodeToString(bytes);
  }
  public static String decodeBase64(byte[] bytes) {
    return Arrays.toString(getDecoder().decode(bytes));
  }
}