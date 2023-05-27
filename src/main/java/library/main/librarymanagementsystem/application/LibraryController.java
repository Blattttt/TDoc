package library.main.librarymanagementsystem.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.main.librarymanagementsystem.HelloApplication;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import java.util.Date;
import java.time.*;

public class LibraryController implements Initializable {
    public int n;
    public int client;
    public int worker;
    public int document;
    public String documentName;
    public String newDocumentName;
    public String clientName;
    public String clientDocumentName;
    @FXML Label documentsLabel;
    @FXML Label workersLabel;
    @FXML Label clientsLabel;
    @FXML Label clientsLabel1;
    @FXML Label clientsLabel11;
    @FXML Label clientsLabel2;
    @FXML private Button documentsButton;
    @FXML private Button workersButton;
    @FXML private Button clientsButton;
    @FXML private Button createButton;
    @FXML private Button exitButton;
    @FXML private Button historyButton;
    @FXML private ComboBox<String> documentsList;
    @FXML private ComboBox<String> clientsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();
        initDocuments();
        initClients();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        documentsButton.setVisible(!Get.getUserType().equals("buh"));
        documentsLabel.setVisible(!Get.getUserType().equals("buh"));
        workersButton.setVisible(!Get.getUserType().equals("buh"));
        workersLabel.setVisible(!Get.getUserType().equals("buh"));

        documentsList.setOnAction(e -> {
            documentName = documentsList.getValue();
            int index = documentName.indexOf("_");
            newDocumentName = documentName.substring(0, index);});

        clientsList.setOnAction(e -> {
            clientName = clientsList.getValue();
            String[] cl = clientName.split(" ");
            clientDocumentName = cl[0];});

        documentsButton.setOnAction(event -> {
            try { DocumentsController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        workersButton.setOnAction(event -> {
            try { WorkersController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        exitButton.setOnAction(event -> {
            try { authorizationController.changeScene();
            } catch (IOException e) { e.printStackTrace(); }});

        clientsButton.setOnAction(event -> {
            try { UsersController.changeScene(); }
            catch (IOException e) { e.printStackTrace(); }});

        historyButton.setOnAction(event -> {
            try { HistController.changeScene(); }
            catch (IOException e) { e.printStackTrace(); }});

        createButton.setOnAction(event -> {

            Map<Integer, String> Month = new HashMap<>();
            Month.put(1, "января");
            Month.put(2, "февраля");
            Month.put(3, "марта");
            Month.put(4, "апреля");
            Month.put(5, "мая");
            Month.put(6, "июня");
            Month.put(7, "июля");
            Month.put(8, "августа");
            Month.put(9, "сентября");
            Month.put(10, "октября");
            Month.put(11, "ноября");
            Month.put(12, "декабря");

            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE FIO = \""+clientsList.getValue() +"\"");
                    while (resultSet.next()) {
                        Get.setFIOS(resultSet.getString("FIO"));
                        Get.setPhoneS(resultSet.getString("Phone"));
                        Get.setPassportS(resultSet.getString("Passport"));
                        Get.setAddressS(resultSet.getString("Address"));
                        Get.setGenderS(resultSet.getString("Gender"));
                        Get.setMailS(resultSet.getString("Mail"));
                        Get.setBirthS(resultSet.getString("Birthdate"));
                    }}}
            catch (Exception e) { System.out.println("Ошибка в БД" + e); }
            
            String dir = new File(".").getAbsoluteFile().getParentFile().getAbsolutePath()
                    + System.getProperty("file.separator");
            HWPFDocument doc = null;
            try (FileInputStream fis = new FileInputStream(dir + documentName)) {
                doc = new HWPFDocument(fis);
            } catch (Exception ex) {System.err.println("Error template!");}
            try { doc.getRange().replaceText("$фио", Get.getFIOS());
                doc.getRange().replaceText("$день", localDate.getDayOfMonth() + "");
                doc.getRange().replaceText("$месяц", Month.get(localDate.getMonthValue()));
                doc.getRange().replaceText("$год", localDate.getYear() + "");
                doc.getRange().replaceText("$паспорт", Get.getPassportS());
                doc.getRange().replaceText("$адрес", Get.getAddressS());
                doc.getRange().replaceText("$телефон", Get.getPhoneS());
                doc.getRange().replaceText("$почта", Get.getMailS());
                doc.getRange().replaceText("$рождение", Get.getBirthS());
            } catch (Exception ex) {System.err.println("Error replaceText!");}
            try (FileOutputStream fos = new FileOutputStream(dir +newDocumentName+"_"+clientDocumentName+".doc")) {
                doc.write(fos);
            } catch (Exception ex) { System.err.println("Error getDesktop!");}



            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { Statement statement2 = conn.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM history");
                    n = 1;
                    while (resultSet2.next()) { if(resultSet2.getInt("history_id") > 0){ n++;
                            continue;}}}} catch (Exception e) { System.out.println("BD Exception"); }

            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { Statement statement2 = conn.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT client_id FROM clients where FIO=\"" +clientName+ "\"");
                    while (resultSet2.next()) { client = resultSet2.getInt("client_id");
                        }}} catch (Exception e) { System.out.println("BD Exception"); }

            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { Statement statement2 = conn.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT worker_id FROM worker where fio=\"" +Get.getUserName()+ "\"");
                    while (resultSet2.next()) { worker = resultSet2.getInt("worker_id");
                    }}} catch (Exception e) { System.out.println("BD Exception"); }

            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")) { Statement statement2 = conn.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT documents_id FROM documents where document_name=\"" +documentName+ "\"");
                    while (resultSet2.next()) { document = resultSet2.getInt("documents_id");
                    }}} catch (Exception e) { System.out.println("BD Exception"); }

            try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                        "root", "1234")){ PreparedStatement statement = conn.prepareStatement
                        ("INSERT into history(history_id,document_id,worker_id,main_client_id,document_name,date" +
                                ") VALUES(?,?,?,?,?,?)");
                    statement.setInt(1, n);
                    statement.setInt(2, document);
                    statement.setInt(3, worker);
                    statement.setInt(4, client);
                    statement.setString(5, newDocumentName+"_"+clientDocumentName+".doc");
                    statement.setString(6, localDate.getDayOfMonth()+"."+
                            localDate.getMonthValue()+"."+localDate.getYear()+" "+localDateTime.getHour()+":"
                            +localDateTime.getMinute()+":"+localDateTime.getSecond());
                    statement.executeUpdate();
                }}
            catch (Exception e) { e.printStackTrace(); }
        });



    }

    private void initDocuments() {
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM documents");
                while (resultSet.next()) {
                    documentsList.getItems().add(resultSet.getString("document_name"));}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }}

    private void initClients() {
        try { Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/globus",
                    "root", "1234")) { Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
                while (resultSet.next()) {
                    clientsList.getItems().add(resultSet.getString("FIO"));}}}
        catch (Exception e) { System.out.println("Ошибка в БД"); }}

    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("library.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = HelloApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("TDoc");
        stage.setScene(scene);
        stage.show();
    }
}
