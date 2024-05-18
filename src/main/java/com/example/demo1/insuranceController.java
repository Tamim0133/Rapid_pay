package com.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class insuranceController extends Home implements Initializable {
    @FXML
    private Button back;
    @FXML
    private Button lifeb;
    @FXML
    private Button bikeb;
    @FXML
    private Button healthb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    ImageView myImageView;

    customerData cus = Home.cus;


    Image myImage = new Image(getClass().getResourceAsStream("logo112-removebg.png"));
    public  void displayImage()
    {
        myImageView.setImage(myImage);
    }

    public void switchToLifePage(MouseEvent mouseEvent) throws IOException {
        lifeb.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("life.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHealthPage(MouseEvent mouseEvent) throws IOException {
        healthb.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("health.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBikePage(MouseEvent mouseEvent) throws IOException {
        bikeb.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("bike.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);


        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

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
