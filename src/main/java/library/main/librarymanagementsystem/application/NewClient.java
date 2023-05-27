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


public class NewClient implements Initializable {
    public int n;
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

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM clients");
                n = 1;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("client_id") > 0){
                        n++;
                        continue;}}} }
        catch (Exception e) { System.out.println("BD Exception"); }

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
                            ("INSERT into clients(client_id,FIO,Phone,Passport,Address,Gender,Mail,Birthdate" +
                                    ") VALUES(?,?,?,?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setString(2, fioField.getText());
                        statement.setString(3, phoneField.getText());
                        statement.setString(4, passportField.getText());
                        statement.setString(5, addressField.getText());
                        statement.setString(6, genderField.getText());
                        statement.setString(7, mailField.getText());
                        statement.setString(8, birthField.getText());
                        statement.executeUpdate();
                        try { UsersController.changeScene();
                        } catch (IOException e) { e.printStackTrace(); }
                    }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionField.setVisible(true);});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}