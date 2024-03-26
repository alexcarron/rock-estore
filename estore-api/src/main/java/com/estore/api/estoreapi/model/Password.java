package com.estore.api.estoreapi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Validates all user passwords
 *
 * @author Samuel Roberts
 */
public class Password {
  private static final String validPasswordRegex =
  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";;
  private static final Pattern regexPattern = Pattern.compile(validPasswordRegex);

  public static boolean validPassword(String password){
    // Matcher matches = regexPattern.matcher(password);
    // return matches.matches();
		return true; // TODO: Replace with implementation
  }

  public static String createStrongPassword(String password){
    return validPasswordRegex;
  }

}
