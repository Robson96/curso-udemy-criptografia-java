package com.robson.simetricas;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static com.robson.utils.Utils.CIPHER_AES;

public class CriptografandoArquivo {

  public static void main(String[] args)
      throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidAlgorithmParameterException, InvalidKeyException,
      IOException {

    // 128 bits
    // primeiroBloco(iv + textoClaro -> xor) -> SegundoBloco(textoCifrado -> xor) -> ...
    byte[] iv = new byte[16];
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(iv);

    // Gerar chave privada
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(256); // bits
    SecretKey privateKey = keyGenerator.generateKey();

    Cipher cipher = Cipher.getInstance(CIPHER_AES);
    cipher.init(Cipher.ENCRYPT_MODE, privateKey, new IvParameterSpec(iv));

    // Gerar saida
    BufferedOutputStream outputStream =
        new BufferedOutputStream(
            new FileOutputStream("importante.pptx.enigma"));

    // Le arquivo
    BufferedInputStream inputStream =
        new BufferedInputStream(
            new FileInputStream("importante.pptx"));

    CipherOutputStream cipherOutputStream =
        new CipherOutputStream(outputStream, cipher);


    for (int b = inputStream.read(); b != -1; b = inputStream.read()) {
      cipherOutputStream.write(b);
    }

    cipherOutputStream.flush();
    cipherOutputStream.close();
    inputStream.close();

    // setamos para outro modo
    cipher.init(Cipher.DECRYPT_MODE, privateKey, new IvParameterSpec(iv));

    // Gerar saida
    BufferedOutputStream outputStream2 =
        new BufferedOutputStream(
            new FileOutputStream("importante2.pptx"));

    // Le arquivo
    BufferedInputStream inputStream2 =
        new BufferedInputStream(
            new FileInputStream("importante.pptx.enigma"));

    CipherInputStream cipherInputStream =
        new CipherInputStream(inputStream2, cipher);


    for (int b = cipherInputStream.read(); b != -1; b = cipherInputStream.read()) {
      outputStream2.write(b);
    }
    outputStream2.flush();
    outputStream2.close();
    cipherInputStream.close();

  }
}
