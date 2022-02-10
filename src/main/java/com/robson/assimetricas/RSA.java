package com.robson.assimetricas;

import com.robson.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import static com.robson.utils.Utils.CIPHER_RSA;

public class RSA {
  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

    // Configurando o novo provedor
    Security.addProvider(new BouncyCastleProvider());

    KeyPairGenerator keyPairGenerator =
        KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGenerator.initialize(2048); // 1024, 2048, 4096 bits

    KeyPair keyPair = keyPairGenerator.generateKeyPair();

    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    System.out.println("Chave Privada: " + Utils.encodeBase64(privateKey.getEncoded()));
    System.out.println("===========================================");
    System.out.println("Chave Publica: " + Utils.encodeBase64(publicKey.getEncoded()));
    System.out.println();

    Cipher cipher = Cipher.getInstance(CIPHER_RSA);
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    String msg = "mensagem secreta!";

    byte[] msgCodificada = cipher.doFinal(msg.getBytes("utf-8"));

    System.out.println("Mensagem codificada: " + Utils.encodeBase64(msgCodificada));
    System.out.println();

    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] msgDecodificada = cipher.doFinal(msgCodificada);

    System.out.println("Mensagem Decodificada: " + new String(msgDecodificada));
  }
}
