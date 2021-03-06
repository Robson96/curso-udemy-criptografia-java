package com.robson.hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

public class PBKDF2 {
  public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
    Security.addProvider(new BouncyCastleProvider());

    // É preciso armazenar o salt
    // para compara novamente a senha do usuario
    // na hora do login
    String salt = "foobar"; //
    String password = "12345";
    int iteracoes = 32;

    // função de derivação de chave
    PBEKeySpec keySpec = new PBEKeySpec(
        password.toCharArray(),
        salt.getBytes(StandardCharsets.UTF_8),
        iteracoes,
        512
    );

    // Geradora de chave
    SecretKeyFactory keyFactory =
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256", "BC");
    byte[] hashWithSalt = keyFactory.generateSecret(keySpec).getEncoded();

    System.out.println(HexFormat.of().formatHex(hashWithSalt));
  }
}
