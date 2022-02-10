package com.robson.simetricas;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ChaCha20_Encrytpion {
  static String plainText = "This is a plain text which will be encrypted by ChaCha20 Algorithm";
  static final String ALGO = "ChaCha20";

  public static void main(String[] args) throws Exception {
    byte[] nonce = new byte[12];
    int counter = 5;
    KeyGenerator keyGenerator = KeyGenerator.getInstance("ChaCha20");
    keyGenerator.init(256);

    // Generate Key
    SecretKey key = keyGenerator.generateKey();

    System.out.println("Original Text  : " + plainText);

    byte[] cipherText = encrypt(plainText.getBytes(), key, counter, nonce);
    System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(cipherText));

    String decryptedText = decrypt(cipherText, key, counter, nonce);
    System.out.println("DeCrypted Text : " + decryptedText);

  }

  public static byte[] encrypt(byte[] plaintext, SecretKey key, int c, byte[] nonce) throws Exception {

    // Get Cipher Instance
    Cipher cipher = Cipher.getInstance(ALGO);

    // Create ChaCha20ParameterSpec
    ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, c);

    // Create SecretKeySpec
    SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

    // Initialize Cipher for ENCRYPT_MODE
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

    // Perform Encryption
    byte[] cipherText = cipher.doFinal(plaintext);

    return cipherText;
  }

  public static String decrypt(byte[] cipherText, SecretKey key, int c, byte[] nonce) throws Exception {
    // Get Cipher Instance
    Cipher cipher = Cipher.getInstance(ALGO);

    // Create ChaCha20ParameterSpec
    ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, c);

    // Create SecretKeySpec
    SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "ChaCha20");

    // Initialize Cipher for DECRYPT_MODE
    cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

    // Perform Decryption
    byte[] decryptedText = cipher.doFinal(cipherText);

    return new String(decryptedText);
  }
}