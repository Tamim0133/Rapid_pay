package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class savingsController implements Initializable {

    ObservableList<String> durationList = FXCollections.observableArrayList("2 years",
            "3 years", "5 years", "7 years");

    @FXML
    private Button next;
    @FXML
    private Button back;
    @FXML
    private ComboBox durationBox;
    @FXML
    TextField money;
    @FXML
    TextField passwordField;
    private double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        durationBox.setValue("2 years");
        durationBox.setItems(durationList);
    }
    @FXML
    ImageView myImageView;

    public static String sendAmount;
    public static String num;
    public static String pass;
    public int amount;


    public void conditionCheck(MouseEvent mouseEvent) throws  IOException{
        sendAmount = money.getText();
        pass = passwordField.getText();
        Integer integer = 0;
        try{
            integer = Integer.valueOf(sendAmount);
        }catch(NumberFormatException e){

        }
        amount = integer;



        // eta thik kora lagbe
        if(amount <500 ){
            showAlert("Amount should be greater than Tk. 500!!!");
        }
        else if(pass.length() == 0){
            showAlert("Invalid password!!!!!");
        }

        else{
            showNotification();
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
    public void showNotification() throws IOException{
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to start savings?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
//            logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("registration.fxml"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Image myImage = new Image(getClass().getResourceAsStream("logo112-removebg.png"));
    public  void displayImage()
    {
        myImageView.setImage(myImage);
    }

    public void backToHomePage(MouseEvent mouseEvent) throws IOException {
        back.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }




}
