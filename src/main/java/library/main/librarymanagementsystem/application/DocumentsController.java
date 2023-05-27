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

public class DocumentsController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button editButton;
    @FXML private Button newClientButton;
    @FXML private TableView<BDdocument> userTable;
    @FXML private TableColumn<BDdocument, String> Id;
    @FXML private TableColumn<BDdocument, String> DocName;
    @FXML private TableColumn<BDdocument, String> DocType;
    private final ObservableList<BDdocument> userListData = FXCollections.observableArrayList();
    private BDdocument selectedTable1 = new BDdocument();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();

        exitButton.setOnAction(event -> {
            try { LibraryController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        newClientButton.setOnAction(event -> {
            try { NewDocument.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        DocName.setCellValueFactory(new PropertyValueFactory<>("DocName"));
        DocType.setCellValueFactory(new PropertyValueFactory<>("DocType"));
        tablerefresh();


        editButton.setOnAction(event -> {
            selectedTable1 = userTable.getSelectionModel().getSelectedItem();
            if(!selectedTable1.getDocName().isEmpty()){
                Get.setNumberS(selectedTable1.getPrCode());
                Get.setDocNameS(selectedTable1.getDocName());
                Get.setDocTypeS(selectedTable1.getDocType());
                try { UpdateDocument.changeScene();
                } catch (IOException e) { e.printStackTrace(); }
            }
        });

    }
    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("documents.fxml"));
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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM documents");
                while(resultSet.next()){ userListData.add(new BDdocument(
                        resultSet.getInt("documents_id"),
                        resultSet.getString("document_name"),
                        resultSet.getString("document_type")));}}
            if (!userListData.isEmpty()){ userTable.setItems(userListData); }}
        catch (Exception e){ System.out.println("BD Exception"); }}
}
