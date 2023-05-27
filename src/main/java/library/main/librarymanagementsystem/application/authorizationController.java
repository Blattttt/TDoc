package library.main.librarymanagementsystem.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class authorizationController implements Initializable {

    public String user;
    public String userName;
    @FXML Label clientsLabel1;
    @FXML Label clientsLabel11;
    @FXML Label clientsLabel111;
    @FXML Label exceptionField;
    @FXML private Button doButton;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        loginField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        passwordField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        doButton.setOnAction(event -> {
            try { String login = loginField.getText();
                String password = passwordField.getText();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { System.out.println("Connected to BD");
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM worker");
                    while (resultSet.next()) {
                        if (resultSet.getString("login").equals(login) &&
                                resultSet.getString("password").equals(password) &&
                                resultSet.getString("type").equals("buh")) {
                            System.out.println("Success worker enter");
                            user = resultSet.getString("type");
                            Get.setUserType(user);
                            userName = resultSet.getString("fio");
                            Get.setUserName(userName);
                            try { LibraryController.changeScene(); }
                            catch (IOException e) { e.printStackTrace(); }}
                        else if (resultSet.getString("login").equals(login) &&
                                resultSet.getString("password").equals(password) &&
                                resultSet.getString("type").equals("adm")) {
                            System.out.println("Success accountant enter");
                            user = resultSet.getString("type");
                            Get.setUserType(user);
                            userName = resultSet.getString("fio");
                            Get.setUserName(userName);
                            try { LibraryController.changeScene(); }
                            catch (IOException e) { e.printStackTrace(); }}
                        else exceptionField.setVisible(true);}}}
            catch (Exception ex) { System.out.println("Error BD"); }});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}