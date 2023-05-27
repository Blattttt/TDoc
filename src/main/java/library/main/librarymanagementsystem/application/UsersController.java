package library.main.librarymanagementsystem.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button editButton;
    @FXML private Button newClientButton;
    @FXML private TableView<BD> userTable;
    @FXML private TableColumn<BD, String> Id;
    @FXML private TableColumn<BD, String> Fio;
    @FXML private TableColumn<BD, String> Phone;
    @FXML private TableColumn<BD, String> Passport;
    @FXML private TableColumn<BD, String> Address;
    @FXML private TableColumn<BD, String> Gender;
    @FXML private TableColumn<BD, String> Mail;
    @FXML private TableColumn<BD, String> Birth;
    private final ObservableList<BD> userListData = FXCollections.observableArrayList();
    private BD selectedTable1 = new BD();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            FXMLLoader loader = new FXMLLoader();

            editButton.setVisible(!Get.getUserType().equals("buh"));

            exitButton.setOnAction(event -> {
                try { LibraryController.changeScene();
                } catch (IOException e) { e.printStackTrace(); }});

            newClientButton.setOnAction(event -> {
                try { NewClient.changeScene();
                } catch (IOException e) { e.printStackTrace(); }});

            Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            Fio.setCellValueFactory(new PropertyValueFactory<>("Fio"));
            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            Passport.setCellValueFactory(new PropertyValueFactory<>("Passport"));
            Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
            Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
            Mail.setCellValueFactory(new PropertyValueFactory<>("Mail"));
            Birth.setCellValueFactory(new PropertyValueFactory<>("Birth"));
            tablerefresh();


            editButton.setOnAction(event -> {
                selectedTable1 = userTable.getSelectionModel().getSelectedItem();
                if(!selectedTable1.getFio().isEmpty()){
                    Get.setNumberS(selectedTable1.getPrCode());
                    Get.setFIOS(selectedTable1.getFio());
                    Get.setPhoneS(selectedTable1.getPhone());
                    Get.setPassportS(selectedTable1.getPassport());
                    Get.setAddressS(selectedTable1.getAddress());
                    Get.setGenderS(selectedTable1.getGender());
                    Get.setMailS(selectedTable1.getMail());
                    Get.setBirthS(selectedTable1.getBirth());
                    try { UpdateClient.changeScene();
                    } catch (IOException e) { e.printStackTrace(); }
                }
            });










        }
    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("users.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }

    private void tablerefresh(){
        userListData.clear();
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
                while(resultSet.next()){ userListData.add(new BD(
                        resultSet.getInt("client_id"),
                        resultSet.getString("FIO"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Passport"),
                        resultSet.getString("Address"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Mail"),
                        resultSet.getString("Birthdate")));}}
            if (!userListData.isEmpty()){ userTable.setItems(userListData); }}
        catch (Exception e){ System.out.println("BD Exception"); }}
}
