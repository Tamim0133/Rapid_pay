package com.example.demo1;

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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;



public class mobileRechargeController extends Home implements Initializable {
    @FXML
    private Button next;
    @FXML
    private Button back;

    private double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    TextField number;
    @FXML
    TextField money;
    @FXML
    TextField passwordField;


    public static String sendAmount;
    public static String num;
    public static String pass;
    public int amount;

    customerData cus = Home.cus;


    public void conditionCheck(MouseEvent mouseEvent) throws IOException, SQLException {
        num = number.getText();
        sendAmount = money.getText();
        pass = passwordField.getText();
        Integer integer = 0;
        try{
            integer = Integer.valueOf(sendAmount);
        }catch(NumberFormatException e){

        }
        amount = integer;
        boolean ok = true;
        if(num.length() == 0){
            showAlert("Invalid number!!!!");
        }else if(sendAmount.length() == 0){
            showAlert("Insufficient Balance!!!!");
        }else if(pass.length() == 0){
            showAlert("Invalid Password!!!!");
        }else{
//            showNotification();
        }

        if(!Objects.equals(cus.getPassword(), pass))
            ok = false;
        if(Objects.equals( cus.getPhoneNum(), num))
            ok = false;

        if(ok)
        {
            Connection connect = Database.connectionDb();
            String updateQuery = "UPDATE customer SET balance = ? WHERE phnNum = ?";
            PreparedStatement pstmt = connect.prepareStatement(updateQuery);

            pstmt.setInt(1, cus.getBalance() - amount);
            pstmt.setString(2, cus.getPhoneNum());

            int rowsAffected = pstmt.executeUpdate();

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Send  Money!\n Please Log In again !!!.");
            alert.showAndWait();

            next.getScene().getWindow().hide();

            //LINK YOUR LOGIN FORM
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
//
            BufferedWriter writer = new BufferedWriter(new FileWriter("cashOutInfo.txt", true)); // Append mode

            writer.write(cus.getPhoneNum());

            writer.write(" ");

            String strAmount = Integer.toString(amount);
            writer.write(strAmount);
            writer.write(" ");

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
            String formattedTime = currentDateTime.format(formatter);

            writer.write(formattedTime);
            writer.newLine();
            writer.close();





            //LINK YOUR LOGIN FORM

        }

    }

    public void showAlert(String s){

        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

//    public void showNotification() throws IOException{
//        try {
//
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Message");
//            alert.setHeaderText(null);
//            String amount = money.getText();
//            alert.setContentText("Are you sure? Tk. "+amount+" will be cut from your account balance!");
//
//            Optional<ButtonType> option = alert.showAndWait();
//
//            if (option.get().equals(ButtonType.OK)) {
//
//                //HIDE YOUR DASHBOARD FORM
////            logout.getScene().getWindow().hide();
//
//                //LINK YOUR LOGIN FORM
//                Parent root = null;
//                try {
//                    root = FXMLLoader.load(getClass().getResource("home.fxml"));
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//                Stage stage = new Stage();
//                Scene scene = new Scene(root);
//
//                root.setOnMousePressed((MouseEvent event) -> {
//                    x = event.getSceneX();
//                    y = event.getSceneY();
//                });
//
//                root.setOnMouseDragged((MouseEvent event) -> {
//                    stage.setX(event.getScreenX() - x);
//                    stage.setY(event.getScreenY() - y);
//
//                    stage.setOpacity(.8);
//                });
//
//                root.setOnMouseReleased((MouseEvent event) -> {
//                    stage.setOpacity(1);
//                });
//
//                stage.initStyle(StageStyle.TRANSPARENT);
//
//                stage.setScene(scene);
//                stage.show();
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void backToHomePage(MouseEvent mouseEvent) throws IOException {
        back.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        Home home = loader.getController();
        home.setHomeCustomer(cus);//

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }



}
