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

import static com.robson.utils.Utils.AES_KEY_SIZE;
import static com.robson.utils.Utils.CIPHER_AES_CBC;

/**
 * @author Robson Magno
 * @version 14/01/2022
 */
public class AES {
  public static void main(String[] args) throws NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException,
      InvalidAlgorithmParameterException {

    // vetor de inicialização
    // para cifra o primeiro bloco, o resultado do primeiro
    // vai ser como entrada pro segundo bloco e sucessivamente...
    // deve salvar o IV para uso posterior
    // O iv deve ter sempre o tamnho de 16 bytes
    byte[] iv = new byte[16];
    // Classe que gera numeros seguros pseudos aleatorios
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(iv);

    // Gera a chave secreta
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(AES_KEY_SIZE); // 256 bits
    SecretKey secretKey = keyGenerator.generateKey();

    System.out.println("Chave: " + Utils.formatKey(secretKey.getEncoded()));

    // Classe core para codificar/decodificar
    Cipher cipher = Cipher.getInstance(CIPHER_AES_CBC);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

    String msg = "Importante!!!";
    byte[] bytesMsg = msg.getBytes(StandardCharsets.UTF_8);
    byte[] msgCodificada = cipher.doFinal(bytesMsg);

    System.out.println("Menssagem: " + msg);
    System.out.println("Menssagem codificada: " +Utils.encodeBase64(msgCodificada));

    cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

    byte[] msgDecodificada = cipher.doFinal(msgCodificada);

    System.out.println("Menssagem decodificada: " + new String(msgDecodificada));
  }
}
