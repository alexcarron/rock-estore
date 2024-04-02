package com.estore.api.estoreapi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Validates and hashes all user passwords
 *
 * @author Samuel Roberts, Victor Rabinovich
 */
public class Password {

  private static final String validPasswordRegex = 
  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
  private static final Pattern regexPattern = Pattern.compile(validPasswordRegex);
  private static Random rand = new Random();

  public static boolean validPassword(String password){
    // Matcher matches = regexPattern.matcher(password);
    // return matches.matches();
		return true; // TODO: Replace with implementation
  }

  public static String createStrongPassword(){
    String strongPassword = "";

    for(int i = 0; i < 10; i++){
      if(i < 6){ 
        int randomLowerCase = rand.nextInt(123-97) + 97;
        strongPassword += String.valueOf((char)randomLowerCase) + "";
      }else if (i < 9) {
        int randomUpperCase = rand.nextInt(91-65) + 65;
        strongPassword += String.valueOf((char)randomUpperCase) + "";
      }else{
        String[] specialChar = {"!","@","#","&","(",")","–","[","{","}","]",
        ":",";","\'","?","/","*","~","$","^","+","=","<",">"};
        int randomIndex = rand.nextInt(specialChar.length);
        strongPassword += specialChar[randomIndex];
      }
    }

    strongPassword += rand.nextInt(1000) + "";

    return strongPassword;
  }

  public static String hashPassword(String password) throws NoSuchAlgorithmException{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

    return bytesToHex(hash);
  }

  /**
   * A method to take a SHA256 hash and put it into hexadecimal
   * @param hash The computed hash
   * @return The hash in base 16
   */
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
