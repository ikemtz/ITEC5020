/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ikemtz.week1;

import java.io.Console;

/**
 *
 * @author Ikemtz
 */
public class Week1 {

  private static Console console;
  private static final int ALPHABET_COUNT = 26;

  public static void main(String[] args) {
    console = System.console();
    System.out.println("**     Caesar Cipher    **");
    System.out.println("** Developed By @ikemtz **");
    if (console == null) {
      System.out.println("Error: A command line interface is required to run this application.");
    } else {
      // I know that this deviates from the assignment specification, but the
      // interface stated in the assignment isn't user friendly
      while (true) {
        var key = getKey();
        var inText = console.readLine("Please enter a message: ");
        var translatedText = translate(inText, key);

        System.out.println();

        System.out.print("The translated message is: ");
        System.out.println(translatedText);
        
        System.out.println();
      }
    }
  }

  // We need a way to handle the infinite amount of potential user errors when
  // inputing an integer value
  public static int getKey() {
    while (true) {
      try {
        var stringKey = console.readLine("Please enter a key value (valid range is -3 to 3): ");
        var key = Integer.parseInt(stringKey);
        if (key >= -3 && key <= 3) {
          return key;
        }
      } catch (NumberFormatException e) {
      }
      System.out.println("Invalid key, please try again.");
    }
  }

  // This method will loop through each character within the message
  // For each character it will call translateChar
  // ultimately returning the translated string
  public static String translate(String inText, int key) {

    var encodedString = new char[inText.length()];
    for (int i = 0; inText.length() > i; i++) {
      encodedString[i] = translateChar(inText.charAt(i), key);
    }
    return new String(encodedString);
  }

  // This method was introduced to make the code more readable
  // This method is the core of the cipher logic
  private static char translateChar(char inputChar, int key) {
    if (!Character.isLetter(inputChar)) {
      return inputChar;
    } else {
      var outputChar = (char) (inputChar + key);
      if (!Character.isLetter(outputChar)) {
        var upperLimit = Character.isLowerCase(inputChar) ? 'z' : 'Z';
        if (outputChar > upperLimit) {
          outputChar = (char) (outputChar - ALPHABET_COUNT);
        } else {
          outputChar = (char) (outputChar + ALPHABET_COUNT);
        }
      }
      return outputChar;
    }
  }
}
