/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package week2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Ikemtz
 */
public class Week2 extends Application {
  public ChoiceBox cbKey;
  public TextArea taTF1;
  public TextArea taTF2;
  public CaesarCipher caesarCipher;
  private final Insets defaultInsets = new Insets(5, 25, 5, 25);
  private final Font defaultFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);

  @Override
  public void start(Stage primaryStage) {
    this.caesarCipher = new CaesarCipher();

    // Key HBox
    Label lblKey = new Label("Key:");
    lblKey.setFont(defaultFont);
    lblKey.setPadding(new Insets(0, 0, 0, 80)); // This is needed for alignment
    String keyChoices[] = { "-3", "-2", "-1", "0", "1", "2", "3" };
    cbKey = new ChoiceBox(FXCollections.observableArrayList(keyChoices));
    HBox hbKey = hboxFactory(lblKey, cbKey);

    // Text To Encrypt HBox
    Label lblEncryptText = new Label("Text To Encrypt:");
    lblEncryptText.setFont(defaultFont);
    taTF1 = textAreaFactory();
    HBox hbEncryptText = hboxFactory(lblEncryptText, taTF1);

    // Encrypted Text HBox
    Label lblEncryptedText = new Label("Encrypted Text:");
    lblEncryptedText.setFont(defaultFont);
    taTF2 = textAreaFactory();
    taTF2.setEditable(false);
    HBox hbEncryptedText = hboxFactory(lblEncryptedText, taTF2);

    // Commands HBox
    Button btnClear = buttonFactory("Clear");
    btnClear.isCancelButton();
    btnClear.setOnAction((ActionEvent e) -> {
      Week2.this.clear();
    });
    Button btnCopy = buttonFactory("Copy Up");
    btnCopy.setOnAction((ActionEvent e) -> {
      Week2.this.copy();
    });
    Button btnTranslate = buttonFactory("Translate");
    btnTranslate.setOnAction((ActionEvent e) -> {
      Week2.this.translate();
    });
    HBox hbCommands = hboxFactory(btnClear, btnCopy, btnTranslate);
    hbCommands.alignmentProperty().set(Pos.CENTER);

    VBox vbRoot = new VBox();
    vbRoot.setPadding(defaultInsets);
    vbRoot.getChildren().addAll(hbKey, hbEncryptText, hbEncryptedText, hbCommands);

    Scene scene = new Scene(vbRoot, 600, 450);

    primaryStage.setTitle("CaesarCipherFx - Isaac Martinez");
    primaryStage.setScene(scene);
    primaryStage.show();
    this.clear();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  // Factory method to simplify creation of HBox controls used in the form
  public HBox hboxFactory(Control... childElements) {
    HBox result = new HBox();
    result.setSpacing(20);
    result.setPadding(defaultInsets);
    result.getChildren().addAll(childElements);
    return result;
  }

  // Factory method to simplify creation of textArea controls used in the form
  public TextArea textAreaFactory() {
    TextArea result = new TextArea();
    result.setPrefRowCount(5);
    result.setPrefHeight(400);
    result.setPrefWidth(350);
    return result;
  }

  // Factory method to simplify creation of button controls used in the form
  public Button buttonFactory(String text) {
    Button result = new Button();
    result.setText(text);
    result.setPadding(new Insets(5, 25, 5, 25));
    result.setFont(defaultFont);
    HBox.setMargin(result, new Insets(20, 20, 20, 20));
    return result;
  }

  // Handling translate button click
  public void translate() {
    int keyVal = getKey();
    String text = taTF1.getText();
    taTF2.setText(this.caesarCipher.translate(text, keyVal));
  }

  // Handling Clear button click
  public void clear() {
    cbKey.valueProperty().setValue("0");
    this.taTF1.textProperty().setValue("");
    this.taTF2.textProperty().setValue("");
  }

  // Handling copy button click
  public void copy() {
    // Automatically negating they selected key (ie: -3 => 3, 3 => -3)
    this.cbKey.setValue(Integer.toString(-getKey()));
    this.taTF1.textProperty().setValue(taTF2.getText());
    this.taTF2.textProperty().setValue("");
  }

  // Wrapper function to get selected Key integer
  public int getKey() {
    return Integer.parseInt(this.cbKey.getValue().toString());
  }

}
