package com.example.demo1;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.xml.transform.Result;
import java.sql.*;

public class HelloController {
    public TextArea text_label;
    public TextField text_field;
    public Button newName;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    public void addNewName(ActionEvent actionEvent) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_database", "root", "");
            // Inserting a new name into the database
            String name = text_field.getText();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO names (pname) VALUES (?)");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            // Displaying the newly added name in the label
            text_label.setText(name);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void showName(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javafx_database", "root", "");
            ResultSet resultset = con.createStatement().executeQuery("SELECT * FROM names");

            StringBuilder names = new StringBuilder();
            while (resultset.next()) {
                names.append(resultset.getString("pname")).append(", "); // Assuming pname is the column name
            }

            text_label.setText(names.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}