/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package week2;

/**
 *
 * @author Ikemtz
 */
public class CaesarCipher {

  private final int ALPHABET_COUNT = 26;

  // This method will loop through each character within the message
  // For each character it will call translateChar
  // ultimately returning the translated string
  public String translate(String inText, int key) {

    char[] encodedString = new char[inText.length()];
    for (int i = 0; inText.length() > i; i++) {
      encodedString[i] = translateChar(inText.charAt(i), key);
    }
    return new String(encodedString);
  }

  // This method was introduced to make the code more readable
  // This method is the core of the cipher logic
  private char translateChar(char inputChar, int key) {
    if (!Character.isLetter(inputChar)) {
      return inputChar;
    } else {
      char outputChar = (char) (inputChar + key);
      if (!Character.isLetter(outputChar)) {
        char upperLimit = Character.isLowerCase(inputChar) ? 'z' : 'Z';
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
