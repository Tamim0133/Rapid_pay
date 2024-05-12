package com.example.demo1;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.xml.transform.Result;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloController {
    @FXML
    private Button close;

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    // Database Tools
    private Connection connect;
    private  PreparedStatement prepare ;
    private  ResultSet result;

    private  double x = 0;
    private double y = 0;
    public void loginAdmin()
    {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = Database.connectionDb();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            Alert alert;
            if(username.getText().isEmpty() || password.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill all blank");
                alert.showAndWait();
            }
            else
            {
                if(result.next())
                {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    login_btn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });
                    root.setOnMouseDragged((MouseEvent event) -> {

                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

//                        stage.setOpacity(.8);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username / Password");
                    alert.showAndWait();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close()
    {
        System.exit(0);
    }







}