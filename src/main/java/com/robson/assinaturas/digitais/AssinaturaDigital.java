package com.robson.assinaturas.digitais;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;

import static com.robson.utils.Utils.CIPHER_RSA;

public class AssinaturaDigital {

  private static final String ALGO_ASS = "SHA256WithRSA";

  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, SignatureException {

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGenerator.initialize(1024);

    KeyPair keyPairAlice = keyPairGenerator.generateKeyPair();
    KeyPair keyPairBob = keyPairGenerator.generateKeyPair();

    Cipher cipher = Cipher.getInstance(CIPHER_RSA);
    cipher.init(Cipher.ENCRYPT_MODE, keyPairBob.getPublic());

    String msg = "Mensagem secreta!";

    byte[] msgCod = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

    Signature signature = Signature.getInstance(ALGO_ASS);
    signature.initSign(keyPairAlice.getPrivate());
    signature.update(msg.getBytes(StandardCharsets.UTF_8));
    byte[] msgAss = signature.sign();


    // Bob decodifica a mensagem
    cipher.init(Cipher.DECRYPT_MODE, keyPairBob.getPrivate());
    byte[] msgDec = cipher.doFinal(msgCod);

    // Bob verifica se foi Alice que assinou a msg
    System.out.println("Checando assinaturas...");
    signature.initVerify(keyPairAlice.getPublic());
    signature.update(msgDec);
    boolean verify = signature.verify(msgAss);

    if (verify) {
      System.out.println("Mensagem checada com sucesso!!!");
    } else {
      System.out.println("Nao é de alice");
    }
  }
}
