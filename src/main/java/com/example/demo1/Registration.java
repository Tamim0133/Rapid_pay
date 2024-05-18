package com.example.demo1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Registration implements  Initializable
{

    @FXML
    private AnchorPane left_form;

    @FXML
    private DatePicker registration_DOB;

    @FXML
    private TextField registration_NID;

    @FXML
    private CheckBox registration_checkbox;

    @FXML
    private TextField registration_email;

    @FXML
    private TextField registration_firstName;

    @FXML
    private ComboBox<?> registration_gender;

    @FXML
    private TextField registration_lastName;

    @FXML
    private TextField registration_phn;


    @FXML
    private PasswordField registration_password;

    @FXML
    private AnchorPane right_form;

    @FXML
    private Button registrationBtn;

    private String firstName , lastName , gender, dateOfBirth, nidNum,phoneNo, emailNo, password;

    @FXML
    void getDate(ActionEvent event) {

    }
    public void close()
    {
        System.exit(0);
    }


    private String[] listGender = {"Male", "Female", "Others"};

    public void addEmployeeGendernList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        registration_gender.setItems(listData);
    }

    @FXML
    void registerBtn(ActionEvent event) throws IOException {

        firstName = registration_firstName.getText();
        lastName = registration_lastName.getText();
        nidNum = registration_NID.getText();
        phoneNo = registration_phn.getText();
        emailNo = registration_email.getText();
        gender = (String) registration_gender.getSelectionModel().getSelectedItem();
        password = registration_password.getText();


        Alert alert;
        if (
                firstName.isEmpty()
                        || lastName.isEmpty()
                        || gender.isEmpty()
                        || nidNum.isEmpty()
                        || phoneNo.isEmpty()
                        || emailNo.isEmpty()
                        || password.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else
        {

            LocalDate myDate = registration_DOB.getValue();
            dateOfBirth = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
            System.out.println(dateOfBirth);

            System.out.println(firstName);
            System.out.println(lastName);
            System.out.println(nidNum);
            System.out.println(phoneNo);
            System.out.println(emailNo);

            addemployeeAdd();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmployeeGendernList();
    }



    private double x = 0;
    private double y = 0;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare ;
    private ResultSet result;
    private Image image;




    public void addemployeeAdd()
    {

        String sql = "INSERT INTO customer "
                + "(firstName,lastName,gender,nid,dateOfBirth,phnNum,email,password) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = Database.connectionDb();

        try {
            Alert alert;
            if (
                       firstName.isEmpty()
                    || lastName.isEmpty()
                    || gender.isEmpty()
                    || nidNum.isEmpty()
                    || dateOfBirth.isEmpty()
                    || phoneNo.isEmpty()
                    || emailNo.isEmpty()
                    || password.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else if(password.length() < 8)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password Length Must be greater than or equal to 8");
                alert.showAndWait();
            }else
            {

                String check = "SELECT phnNum FROM customer WHERE phnNum = '"
                        + phoneNo + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Phone Number " + phoneNo + " already exist!");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, firstName);
                    prepare.setString(2, lastName);
                    prepare.setString(3, gender);
                    prepare.setString(4, nidNum);
                    prepare.setString(5, dateOfBirth);
                    prepare.setString(6, phoneNo);
                    prepare.setString(7, emailNo);
                    prepare.setString(8, password);
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    System.out.println("Successfully Added");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    customerData Customer = new customerData( firstName,  lastName,  gender, nidNum, dateOfBirth,  phoneNo,  emailNo,    password,  0 );
                    registrationBtn.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                    Parent root = loader.load();

                    Home home = loader.getController();
                    home.setHomeCustomer(Customer);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


