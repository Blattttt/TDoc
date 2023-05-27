package library.main.librarymanagementsystem.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HistController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button editButton;
    @FXML private TableView<BDhist> userTable;
    @FXML private TableColumn<BDhist, String> Id;
    @FXML private TableColumn<BDhist, String> Document;
    @FXML private TableColumn<BDhist, String> Worker;
    @FXML private TableColumn<BDhist, String> Client;
    @FXML private TableColumn<BDhist, String> Name;
    @FXML private TableColumn<BDhist, String> Data;
    private final ObservableList<BDhist> userListData = FXCollections.observableArrayList();
    private BDhist selectedTable1 = new BDhist();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            FXMLLoader loader = new FXMLLoader();

            editButton.setVisible(!Get.getUserType().equals("buh"));

            exitButton.setOnAction(event -> {
                try { LibraryController.changeScene();
                } catch (IOException e) { e.printStackTrace(); }});


            Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            Document.setCellValueFactory(new PropertyValueFactory<>("Document"));
            Worker.setCellValueFactory(new PropertyValueFactory<>("Worker"));
            Client.setCellValueFactory(new PropertyValueFactory<>("Client"));
            Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            Data.setCellValueFactory(new PropertyValueFactory<>("Data"));
            tablerefresh();


            editButton.setOnAction(event -> {
                selectedTable1 = userTable.getSelectionModel().getSelectedItem();
                if(!selectedTable1.getName().isEmpty()){
                    Get.setNumberS(selectedTable1.getPrCode());
                    Get.setDocumentS(selectedTable1.getDocument());
                    Get.setWorkerS(selectedTable1.getWorker());
                    Get.setClientS(selectedTable1.getClient());
                    Get.setNameS(selectedTable1.getName());
                    Get.setDataS(selectedTable1.getData());
                    try { UpdateHist.changeScene();
                    } catch (IOException e) { e.printStackTrace(); }
                }
            });
        }
    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hist.fxml"));
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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM history");
                while(resultSet.next()){ userListData.add(new BDhist(
                        resultSet.getInt("history_id"),
                        resultSet.getInt("document_id"),
                        resultSet.getInt("worker_id"),
                        resultSet.getInt("main_client_id"),
                        resultSet.getString("document_name"),
                        resultSet.getString("date")));}}
            if (!userListData.isEmpty()){ userTable.setItems(userListData); }}
        catch (Exception e){ System.out.println("BD Exception"); }}
}
