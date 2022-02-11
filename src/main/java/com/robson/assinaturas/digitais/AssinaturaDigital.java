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

  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, SignatureException {

    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(1024);

    KeyPair keyPairAlice = keyPairGenerator.generateKeyPair();
    KeyPair keyPairBob = keyPairGenerator.generateKeyPair();

    // Alice vai utilizar a chave publica de Bob
    Cipher cipher = Cipher.getInstance(CIPHER_RSA);
    cipher.init(Cipher.ENCRYPT_MODE, keyPairBob.getPublic());

    String msg = "Mensagem secreta!";

    // Alice cifra a msg com a chave publica de Bob
    byte[] msgCod = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

    // Alice assina a msg com a sua chave privada
    Signature signature = Signature.getInstance("SHA256WithRSA");
    signature.initSign(keyPairAlice.getPrivate());
    signature.update(msg.getBytes(StandardCharsets.UTF_8));
    byte[] msgAss = signature.sign();

    // Bob decodifica a mensagem com a sua chave privada
    cipher.init(Cipher.DECRYPT_MODE, keyPairBob.getPrivate());
    byte[] msgDec = cipher.doFinal(msgCod);

    // Bob verifica se foi Alice que assinou a msg
    // com a chave publica da Alice
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
