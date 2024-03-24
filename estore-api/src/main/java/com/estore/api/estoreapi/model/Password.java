package com.estore.api.estoreapi.model;


/**
 * Validates all user passwords
 *
 * @author Samuel Roberts
 */
public class Password {
  private static String validPasswordRegex = "";

  public boolean validPassword(String password){
    return true;
  }

  public String createStrongPassword(String password){
    return "";
  }

}
