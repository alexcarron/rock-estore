package com.estore.api.estoreapi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Validates and hashes all user passwords
 *
 * @author Samuel Roberts, Victor Rabinovich
 */
public class Password {

  private static final String validPasswordRegex = 
  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";;
  private static final Pattern regexPattern = Pattern.compile(validPasswordRegex);

  public static boolean validPassword(String password){
    Matcher matches = regexPattern.matcher(password);
    return matches.matches();
  }

  public static String createStrongPassword(String password){
    return validPasswordRegex;
  }

  public static String hashPassword(String password) throws NoSuchAlgorithmException{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

    return bytesToHex(hash);
  }

  
  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) {
            hexString.append('0');
        }
        hexString.append(hex);
    }
    return hexString.toString();
}
}
