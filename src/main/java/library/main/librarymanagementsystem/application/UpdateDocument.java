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


public class UpdateDocument implements Initializable {

    public String user;
    @FXML Label exceptionField;
    @FXML private Button updateButton;
    @FXML private Button exitButton;
    @FXML private TextField docNameField;
    @FXML private TextField docTypeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        docNameField.setText(Get.getDocNameS());
        docTypeField.setText(Get.getDocTypeS());

        exceptionField.setVisible(false);
        docNameField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        docTypeField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        exitButton.setOnAction(event -> {
            try { DocumentsController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        updateButton.setOnAction(event -> {
            if (!(docNameField.getText().isEmpty() || docTypeField.getText().isEmpty())) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                            ("UPDATE documents set document_name=" + "'" + docNameField.getText() + "'" +
                                    " ,document_type=" + "'" + docTypeField.getText() + "'" +
                                    " where documents_id=" + Get.getNumberS());
                        statement.executeUpdate();
                        try { DocumentsController.changeScene();
                        } catch (IOException e) { e.printStackTrace(); }
                    }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionField.setVisible(true);});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateDocument.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}