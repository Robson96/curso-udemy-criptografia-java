package com.robson.hash;

import com.robson.utils.Utils;
import org.bouncycastle.crypto.generators.SCrypt;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class DemoScrypt {
  public static void main(String[] args) throws UnsupportedEncodingException {
    // É preciso armazenar o salt
    // para compara novamente a senha do usuario
    // na hora do login
    String salt = "foobar";
    String password = "12345";
    int iteracoes = 2048;

    byte[] salt1 = new byte[16];
    new SecureRandom().nextBytes(salt1);

    byte[] sCrypt = SCrypt.generate(
        password.getBytes("utf-8"),
        salt1, // salte
        iteracoes, // numero de interacoes
        8, // tamanho do bloco
        1, // O numero de threads
        128 // tamanho da chave em bits
        );

    System.out.println(HexFormat.of().formatHex(sCrypt));
  }
}
