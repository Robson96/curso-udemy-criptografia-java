package com.robson.hash;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import static java.lang.System.out;

public class SalvarSenha {
  public static void main(String[] args) {
    Console console = System.console();

    if (console == null)
      System.exit(1);

    out.println("Digite uma senha: ");
    char[] senha = console.readPassword(); // Receber senhas

    salvarSenha(senha);
  }

  private static void salvarSenha(char[] senha) {
    String hash = gerarHash(senha);
    try (FileOutputStream fileOutputStream =
        new FileOutputStream("/home/ubuntu/senhas.txt")) {

      for (char c: hash.toCharArray())
        fileOutputStream.write(c);
      fileOutputStream.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
    out.println("Senha salva!");
  }

  private static String gerarHash(char[] senha) {
    String s = new String(senha);
    byte[] hash = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return HexFormat.of().formatHex(hash);
  }
}
