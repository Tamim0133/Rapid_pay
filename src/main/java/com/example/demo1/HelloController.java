package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void loginAdmin() {
        String sqlAdmin = "SELECT * FROM admin WHERE username = ? and password = ?";
        connect = Database.connectionDb();

        boolean loggedIn = false;

        try {
            // Create a scrollable result set
            prepare = connect.prepareStatement(sqlAdmin, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blanks");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    getData.userName = username.getText();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in");
                    alert.showAndWait();
                    loggedIn = true;

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
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                    return; // Exit method if admin login is successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!loggedIn)
        {

            String sqlEmployee = "SELECT * FROM employee WHERE employee_id = ? and phoneNum = ?";
            connect = Database.connectionDb();



            try {
                // Create a scrollable result set
                prepare = connect.prepareStatement(sqlEmployee, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepare.setString(1, username.getText());
                prepare.setString(2, password.getText());

                result = prepare.executeQuery();

                Alert alert;
                if (username.getText().isEmpty() || password.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blanks");
                    alert.showAndWait();
                } else {
                    if (result.next()) {
                        getData.userName = username.getText();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully logged in");
                        alert.showAndWait();

                        loggedIn = true;

                        login_btn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("employeePage.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });
                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                        return; // Exit method if admin login is successful
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(!loggedIn)
        {
            String sqlCustomer = "SELECT * FROM customer WHERE phnNum = ? and password = ?";
            connect = Database.connectionDb();

            try {
                // Create a scrollable result set
                prepare = connect.prepareStatement(sqlCustomer, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepare.setString(1, username.getText()); // phoneNumber
                prepare.setString(2, password.getText()); // password

                result = prepare.executeQuery();

                Alert alert;
                if (username.getText().isEmpty() || password.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blanks");
                    alert.showAndWait();
                } else {
                    if (result.next()) {
                        getData.userName = username.getText();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully logged in");
                        alert.showAndWait();

                        loggedIn = true;

                        result.beforeFirst(); // Move to the first row before accessing data
                        if (result.next()) {
                            customerData customer = new customerData(
                                    result.getString("firstName"),
                                    result.getString("lastName"),
                                    result.getString("gender"),
                                    result.getString("nid"),
                                    result.getString("dateOfBirth"),
                                    result.getString("phnNum"),
                                    result.getString("email"),
                                    result.getString("password"),
                                    result.getInt("balance")
                            );




                            login_btn.getScene().getWindow().hide();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                            Parent root = loader.load();

                            Home home = loader.getController();
                            home.setHomeCustomer(customer);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);

                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.show();
                        }
                    } else {
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



    }

    public void close() {
        System.exit(0);
    }

    public void switchToRegistration() throws IOException {
        try {
            System.out.println("Sign Up Clicked!");
            Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
