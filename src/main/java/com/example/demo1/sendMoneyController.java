package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class sendMoneyController extends Home implements Initializable {
    @FXML
    private Button next;
    @FXML
    private Button back;
    @FXML
    TextField number;
    @FXML
    TextField money;
    @FXML
    TextField passwordField;

    customerData cus = Home.cus;

    private double x, y;

    public static String sendAmount;
    public static String num;
    public static String pass;
    public int amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backTOHomePage(MouseEvent mouseEvent) throws IOException {
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        Home home = loader.getController();
        home.setHomeCustomer(cus); //

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

    }

    public static String getAmount() {
        return sendAmount;
    }

    @FXML
    void sendMoneyFunc(ActionEvent event) throws SQLException {

        int currBalance = cus.getBalance();
        String reciversPhnNum = number.getText();
        String enteredPassword = passwordField.getText();
        int toGiveAmount = Integer.parseInt(money.getText());
        System.out.println(reciversPhnNum);
        System.out.println(enteredPassword);
        System.out.println(toGiveAmount);
        boolean found_user = false;
        String query = "SELECT COUNT(*) FROM customer WHERE phnNum = ?";
        Connection connect = Database.connectionDb();
        try {
            PreparedStatement pstmt = connect.prepareStatement(query);
            pstmt.setString(1, reciversPhnNum);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    found_user = true;
                    System.out.println("Fount User");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception to find User !");
        }

        if (currBalance >= toGiveAmount && found_user && Objects.equals(cus.getPassword(), enteredPassword) && !Objects.equals(cus.getPhoneNum(), reciversPhnNum)) {
            System.out.println("Inside Send Money Function !");
            try {
                Alert alert;
                {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cofirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to Send Money Tk. " + toGiveAmount + " ? " );
                    Optional < ButtonType > option = alert.showAndWait();
                    connect = Database.connectionDb();
                    if (option.get().equals(ButtonType.OK)) {
                        String updateQuery = "UPDATE customer SET balance = ? WHERE phnNum = ?";
                        PreparedStatement pstmt = connect.prepareStatement(updateQuery);

                        pstmt.setInt(1, currBalance - toGiveAmount);
                        pstmt.setString(2, cus.getPhoneNum());

                        int rowsAffected = pstmt.executeUpdate();

                        BufferedWriter writer = new BufferedWriter(new FileWriter("cashOutInfo.txt", true)); // Append mode

                        writer.write(cus.getPhoneNum());

                        writer.write(" ");

                        String strAmount = Integer.toString(toGiveAmount);
                        writer.write(strAmount);
                        writer.write(" ");

                        LocalDateTime currentDateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
                        String formattedTime = currentDateTime.format(formatter);

                        writer.write(formattedTime);
                        writer.newLine();
                        writer.close();
                    }
                }

                {

                    connect = Database.connectionDb();

                    String updateQuery = "UPDATE customer SET balance = balance + ? WHERE phnNum = ?";
                    PreparedStatement pstmt = connect.prepareStatement(updateQuery);

                    pstmt.setInt(1, toGiveAmount);
                    pstmt.setString(2, reciversPhnNum);

                    int rowsAffected = pstmt.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Send  Money!\n Please Log In again !!!.");
                    alert.showAndWait();

                    BufferedWriter writer = new BufferedWriter(new FileWriter("transactionID.txt", true)); // Append mode

                    writer.write(cus.getPhoneNum());

                    writer.write(" ");

                    String strAmount = Integer.toString(toGiveAmount);
                    writer.write(strAmount);
                    writer.write(" ");

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
                    String formattedTime = currentDateTime.format(formatter);

                    writer.write(formattedTime);
                    writer.newLine();
                    writer.close();

                    next.getScene().getWindow().hide();

                    //LINK YOUR LOGIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (!cus.getPassword().equals(enteredPassword) ){
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unsuccessful! \n  Wrong Password  !");
            alert.showAndWait();

        } else if (cus.getBalance() < toGiveAmount) {
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unsuccessful! \n Insufficient Balance  ");
            alert.showAndWait();


        } else if (!found_user) {
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unsuccessful! \n  Make Sure that the given number is a user with rapid pay account ! ");
            alert.showAndWait();


        } else {
            System.out.println("None were Ful filled !");
        }
    }
}