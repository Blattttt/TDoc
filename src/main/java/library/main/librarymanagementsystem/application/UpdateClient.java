package library.main.librarymanagementsystem.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;

import java.io.IOException;


public class UpdateClient implements Initializable {

    public String user;
    @FXML Label exceptionField;
    @FXML private Button updateButton;
    @FXML private Button exitButton;
    @FXML private TextField fioField;
    @FXML private TextField phoneField;
    @FXML private TextField passportField;
    @FXML private TextField addressField;
    @FXML private TextField genderField;
    @FXML private TextField mailField;
    @FXML private TextField birthField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        fioField.setText(Get.getFIOS());
        phoneField.setText(Get.getPhoneS());
        passportField.setText(Get.getPassportS());
        addressField.setText(Get.getAddressS());
        genderField.setText(Get.getGenderS());
        mailField.setText(Get.getMailS());
        birthField.setText(Get.getBirthS());

        exceptionField.setVisible(false);
        fioField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        phoneField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        passportField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        addressField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        genderField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        mailField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        birthField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        exitButton.setOnAction(event -> {
            try { UsersController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        updateButton.setOnAction(event -> {
            if (!(fioField.getText().isEmpty() || phoneField.getText().isEmpty() || passportField.getText().isEmpty()
                    || addressField.getText().isEmpty() || genderField.getText().isEmpty() || mailField.getText().isEmpty()
                    || birthField.getText().isEmpty() || fioField.getText().matches("^[0-9]+$")
                    || phoneField.getText().matches("^[A-Za-z ]+$") || passportField.getText().matches("^[A-Za-z ]+$")
                    || genderField.getText().matches("^[0-9]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                            ("UPDATE clients set FIO=" + "'" + fioField.getText() + "'" +
                                    " ,Phone=" + "'" + phoneField.getText() + "'" + " ,Passport=" + "'" + passportField.getText() + "'" +
                                    " ,Address=" + "'" + addressField.getText() + "'" + " ,Gender=" + "'" + genderField.getText() + "'" +
                                    " ,Mail=" + "'" + mailField.getText() + "'" + " ,Birthdate=" + "'" + birthField.getText() + "'" +
                                    " where client_id=" + Get.getNumberS());
                        statement.executeUpdate();
                        try { UsersController.changeScene();
                        } catch (IOException e) { e.printStackTrace(); }
                    }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionField.setVisible(true);});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}