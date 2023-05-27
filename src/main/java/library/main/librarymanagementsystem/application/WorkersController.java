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

public class WorkersController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button editButton;
    @FXML private Button newClientButton;
    @FXML private TableView<BDworkers> userTable;
    @FXML private TableColumn<BDworkers, String> Id;
    @FXML private TableColumn<BDworkers, String> WorkFio;
    @FXML private TableColumn<BDworkers, String> WorkLogin;
    @FXML private TableColumn<BDworkers, String> WorkPassword;
    @FXML private TableColumn<BDworkers, String> WorkType;
    private final ObservableList<BDworkers> userListData = FXCollections.observableArrayList();
    private BDworkers selectedTable1 = new BDworkers();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        editButton.setVisible(!Get.getUserType().equals("buh"));

        exitButton.setOnAction(event -> {
            try { LibraryController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        newClientButton.setOnAction(event -> {
            try { NewWorker.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        WorkFio.setCellValueFactory(new PropertyValueFactory<>("WorkFio"));
        WorkLogin.setCellValueFactory(new PropertyValueFactory<>("WorkLogin"));
        WorkPassword.setCellValueFactory(new PropertyValueFactory<>("WorkPassword"));
        WorkType.setCellValueFactory(new PropertyValueFactory<>("WorkType"));
        tablerefresh();


        editButton.setOnAction(event -> {
            selectedTable1 = userTable.getSelectionModel().getSelectedItem();
            if(!selectedTable1.getWorkFio().isEmpty()){
                Get.setNumberS(selectedTable1.getPrCode());
                Get.setWorkFioS(selectedTable1.getWorkFio());
                Get.setWorkLoginS(selectedTable1.getWorkLogin());
                Get.setWorkPasswordS(selectedTable1.getWorkPassword());
                Get.setWorkTypeS(selectedTable1.getWorkType());
                try { UpdateWorker.changeScene();
                } catch (IOException e) { e.printStackTrace(); }
            }
        });
    }
    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("workers.fxml"));
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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM worker");
                while(resultSet.next()){ userListData.add(new BDworkers(
                        resultSet.getInt("worker_id"),
                        resultSet.getString("fio"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("type")));}}
            if (!userListData.isEmpty()){ userTable.setItems(userListData); }}
        catch (Exception e){ System.out.println("BD Exception"); }}
}
