package library.main.librarymanagementsystem.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;


public class UpdateHist implements Initializable {

    public String user;
    @FXML Label exceptionField;
    @FXML private Button updateButton;
    @FXML private Button exitButton;
    @FXML private TextField documentField;
    @FXML private TextField workerField;
    @FXML private TextField clientField;
    @FXML private TextField nameField;
    @FXML private TextField dateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        documentField.setText(String.valueOf(Get.getDocumentS()));
        workerField.setText(String.valueOf(Get.getWorkerS()));
        clientField.setText(String.valueOf(Get.getClientS()));
        nameField.setText(Get.getNameS());
        dateField.setText(Get.getDataS());

        exceptionField.setVisible(false);
        documentField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        workerField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        clientField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        nameField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));
        dateField.textProperty().addListener((observableValue, s, t1) -> exceptionField.setVisible(false));

        exitButton.setOnAction(event -> {
            try { HistController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        updateButton.setOnAction(event -> {
            if (!(documentField.getText().isEmpty() || workerField.getText().isEmpty() || clientField.getText().isEmpty()
                    || nameField.getText().isEmpty() || dateField.getText().isEmpty() || documentField.getText().matches("^[A-Za-z ]+$")
                    || workerField.getText().matches("^[A-Za-z ]+$") || clientField.getText().matches("^[A-Za-z ]+$")
                    || dateField.getText().matches("^[A-Za-z ]+$"))) {
                try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                            "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                            ("UPDATE history set document_id=" + documentField.getText() +
                                    " ,worker_id=" + workerField.getText() + " ,main_client_id=" + clientField.getText() +
                                    " ,document_name=" + "'" + nameField.getText() + "'" + " ,date=" + "'" + dateField.getText()
                                    + "'" + " where history_id=" + Get.getNumberS());
                        statement.executeUpdate();
                        try { HistController.changeScene();
                        } catch (IOException e) { e.printStackTrace(); }
                    }}
                catch (Exception e) { e.printStackTrace(); }}
            else exceptionField.setVisible(true);});
    }

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateHist.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}