package com.estore.api.estoreapi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

/**
 * Validates all user passwords
 *
 * @author Samuel Roberts
 */
public class Password {
  private static final String validPasswordRegex = 
  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
  private static final Pattern regexPattern = Pattern.compile(validPasswordRegex);
  private static Random rand = new Random();

  public static boolean validPassword(String password){
    Matcher matches = regexPattern.matcher(password);
    return matches.matches();
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

}
