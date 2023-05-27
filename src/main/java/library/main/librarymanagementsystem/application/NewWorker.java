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


public class NewWorker implements Initializable {
    public int n;
    public String user;
    @FXML Label exceptionField;
    @FXML private Button updateButton;
    @FXML private Button exitButton;
    @FXML private TextField fioField;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;
    @FXML private TextField typeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                    "root", "1234")) { Statement statement2 = conn.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM worker");
                n = 1;
                while (resultSet2.next()) {
                    if(resultSet2.getInt("worker_id") > 0){
                        n++;
                        continue;}}} }
        catch (Exception e) { System.out.println("BD Exception"); }

        exceptionField.setVisible(false);
        fioField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        loginField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        passwordField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        typeField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        exitButton.setOnAction(event -> {
            try { WorkersController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        updateButton.setOnAction(event -> {
            if (!(fioField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || typeField.getText().isEmpty() || fioField.getText().matches("^[0-9]+$")
                    || typeField.getText().matches("^[0-9]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                            ("INSERT into worker(worker_id,fio,login,password,type) VALUES(?,?,?,?,?)");
                        statement.setInt(1, n);
                        statement.setString(2, fioField.getText());
                        statement.setString(3, loginField.getText());
                        statement.setString(4, passwordField.getText());
                        statement.setString(5, typeField.getText());
                        statement.executeUpdate();
                        try { WorkersController.changeScene();
                        } catch (IOException e) { e.printStackTrace(); }
                    }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionField.setVisible(true);});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newWorker.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}