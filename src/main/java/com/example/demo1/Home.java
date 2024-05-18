package com.example.demo1;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class Home implements Initializable {

    public Button close;
    @FXML
    private Label welcome_firstName;

    @FXML
    private Button addEmployeeUpdateBtn;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private Button cashInBtn;

    @FXML
    private AnchorPane cashIn_page;

    @FXML
    private Button cashOutBtn;

    @FXML
    private AnchorPane cash_out_page;

    @FXML
    private Label dateOfBirth_label;

    @FXML
    private Label email_label;

    @FXML
    private Label email_label1;

    @FXML
    private Label firstName;

    @FXML
    private Label firstName_label;
    @FXML
    private Label firstName_label2;
    @FXML
    private Label lastName_label2;

    @FXML
    private Label gender_label;

    @FXML
    private Button historyBtn;

    @FXML
    private AnchorPane history_page;

    @FXML
    private AnchorPane homeBtn_page;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_page;

    @FXML
    private Label lastName_label;

    @FXML
    private FontAwesomeIcon logout;

    @FXML
    private AnchorPane navbar;

    @FXML
    private Label nid_label;

    @FXML
    private AnchorPane parent;

    @FXML
    private Label password_label;

    @FXML
    private Button personalInfoBtn;

    @FXML
    private AnchorPane personalInfo_page;

    @FXML
    private Label show_dateofBirth;

    @FXML
    private Label show_email;

    @FXML
    private Label show_firstName;

    @FXML
    private Label show_gender;

    @FXML
    private Label show_lastName;

    @FXML
    private Label show_nid;

    @FXML
    private Label show_password;

    @FXML
    private Label show_phone;

    @FXML
    private AnchorPane updateImgBtn;

    @FXML
    private Button updateInfoBtn;

    @FXML
    private Label currentBalance;

    @FXML
    private TextField cashInAmount;

    @FXML
    private TableView<cashInType> transactionsTable;

    @FXML
    private TableColumn<cashInType, Integer> cashInCol;

    @FXML
    private TableColumn<cashInType, String> timeCol;


    @FXML
    private TableView<cashInType> transactionsTable1;

    @FXML
    private TableColumn<cashInType, Integer> cashInCol1;

    @FXML
    private TableColumn<cashInType, String> timeCol1;

    @FXML
    private TextField cashOutAmount;



    private currentLoggedInCustomer c;

    private double x = 0;
    private double y = 0;

    private  Connection connect;
    private Statement statement;
    private PreparedStatement prepare ;
    private ResultSet result;
    private Image image;

    private  Connection connect2;
    private Statement statement2;
    private PreparedStatement prepare2 ;
    private ResultSet result2;
    private Image image2;


    customerData cus;

    public void switchToSendMoneyPage(MouseEvent mouseEvent)throws Exception{

    }

    public void setHomeCustomer(customerData customer)
    {

        cus = customer;
        customer.printInfo();

        try{
            System.out.println("First Name : ");
            System.out.println(cus.getFirstName());
            welcome_firstName.setText(cus.getFirstName());
            int b = cus.getBalance();

            currentBalance.setText(String.valueOf(b));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
        personalInfoBtn.setStyle("-fx-background-color:transparent");
        cashInBtn.setStyle("-fx-background-color:transparent");
        cashOutBtn.setStyle("-fx-background-color:transparent");
        historyBtn.setStyle("-fx-background-color:transparent");

        timeCol.setCellValueFactory(new PropertyValueFactory<cashInType, String>("time"));
        cashInCol.setCellValueFactory(new PropertyValueFactory<cashInType, Integer>("amount"));
        timeCol1.setCellValueFactory(new PropertyValueFactory<cashInType, String>("time"));
        cashInCol1.setCellValueFactory(new PropertyValueFactory<cashInType, Integer>("amount"));

    }

    private void setupTable() {
        {
            transactionsTable.getItems().removeAll();
            transactionsTable.getItems().clear();


            try (BufferedReader reader = new BufferedReader(new FileReader("transactionID.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    // Split line into phone number, amount, and time
                    System.out.println("Inside while loop !");
                    String[] parts = line.split(" ");
                    for (String part : parts)
                        System.out.println(part);
                    System.out.println(line);
                    if (parts.length == 7) {
                        String phoneNumber = parts[0];
                        int amount = Integer.parseInt(parts[1]);
                        String time = (parts[2] + " " + parts[3] + " " + parts[4] + " " + parts[5] + " " + parts[6]);

                        System.out.println("---------------------------");
                        System.out.println("-------Phone Number : " + phoneNumber);
                        System.out.println("Amount ---- : " + amount);
                        System.out.println("time ====== : " + time);

                        System.out.println("----User Phone : " + cus.getPhoneNum());


                        // Add transaction to table (replace this with your own logic)
                        if (phoneNumber.equals(cus.getPhoneNum())) { // Check if it matches desired phone number
                            System.out.println("Inside Equal !");
                            try {
                                transactionsTable.getItems().add(new cashInType(time, amount));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            transactionsTable1.getItems().removeAll();
            transactionsTable1.getItems().clear();


            try (BufferedReader reader = new BufferedReader(new FileReader("cashOutInfo.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    // Split line into phone number, amount, and time
                    System.out.println("Inside while loop!");
                    String[] parts = line.split(" ");
                    for (String part : parts)
                        System.out.println(part);
                    System.out.println(line);
                    if (parts.length == 7) {
                        String phoneNumber = parts[0];
                        int amount = Integer.parseInt(parts[1]);
                        String time = (parts[2] + " " + parts[3] + " " + parts[4] + " " + parts[5] + " " + parts[6]);

                        System.out.println("--------------2-------------");
                        System.out.println("-------Phone Number : " + phoneNumber);
                        System.out.println("Amount ---- : " + amount);
                        System.out.println("time ====== : " + time);

                        System.out.println("----User Phone : " + cus.getPhoneNum());


                        // Add transaction to table (replace this with your own logic)
                        if (phoneNumber.equals(cus.getPhoneNum())) { // Check if it matches desired phone number
                            System.out.println("Inside Equal 2 !");
                            try {
                                transactionsTable1.getItems().add(new cashInType(time, amount));
                                System.out.println("Added 2 !");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void switchForm(ActionEvent event) throws SQLException {
        if(event.getSource() == home_btn)
        {
            homeBtn_page.setVisible(true);
            personalInfo_page.setVisible(false);
            cashIn_page.setVisible(false);
            cash_out_page.setVisible(false);
            history_page.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            personalInfoBtn.setStyle("-fx-background-color:transparent");
            cashInBtn.setStyle("-fx-background-color:transparent");
            cashOutBtn.setStyle("-fx-background-color:transparent");
            historyBtn.setStyle("-fx-background-color:transparent");
        }
        else if(event.getSource() == personalInfoBtn)
        {
            homeBtn_page.setVisible(false);
            personalInfo_page.setVisible(true);
            cashIn_page.setVisible(false);
            cash_out_page.setVisible(false);
            history_page.setVisible(false);

            if(cus.getImgUri() == "") {
                addEmployee_importBtn.setVisible(false);
                addEmployeeUpdateBtn.setVisible(false);
            }

            personalInfoBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            cashInBtn.setStyle("-fx-background-color:transparent");
            cashOutBtn.setStyle("-fx-background-color:transparent");
            historyBtn.setStyle("-fx-background-color:transparent");

            show_firstName.setText(cus.getFirstName());
            show_lastName.setText(cus.getLastName());
            show_gender.setText(cus.getGender());
            show_dateofBirth.setText(cus.getDob());
            show_email.setText(cus.getEmail());
            show_nid.setText(cus.getNid());
            show_phone.setText(cus.getPhoneNum());
            show_password.setText(cus.getPassword());

            try{
                firstName_label2.setText(cus.getFirstName());

                lastName_label2.setText(cus.getLastName());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try {
                connect2 = Database.connectionDb();

                String check = "SELECT phnNum, img FROM image WHERE phnNum = '" + cus.getPhoneNum() + "'";
                System.out.println("Executing query: " + check);

                statement2 = connect2.createStatement();
                result2 = statement2.executeQuery(check);

                if (result2.next()) {
                    System.out.println("Found Image!");
                    System.out.println("phnNum: " + result2.getString("phnNum"));
                    System.out.println("img: " + result2.getString("img"));

                    String uri = "file:" + result2.getString("img");
                    System.out.println("Image URI: " + uri);

                    image2 = new Image(uri, 226, 230, false, true);
                    addEmployee_image.setImage(image2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(event.getSource() == cashInBtn)
        {
            homeBtn_page.setVisible(false);
            personalInfo_page.setVisible(false);
            cashIn_page.setVisible(true);
            cash_out_page.setVisible(false);
            history_page.setVisible(false);

            cashInBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            personalInfoBtn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            cashOutBtn.setStyle("-fx-background-color:transparent");
            historyBtn.setStyle("-fx-background-color:transparent");
        }
        else if(event.getSource() == cashOutBtn)
        {
            homeBtn_page.setVisible(false);
            personalInfo_page.setVisible(false);
            cashIn_page.setVisible(false);
            cash_out_page.setVisible(true);
            history_page.setVisible(false);

            cashOutBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            personalInfoBtn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            cashInBtn.setStyle("-fx-background-color:transparent");
            historyBtn.setStyle("-fx-background-color:transparent");
        }
        else if(event.getSource() == historyBtn)
        {
            homeBtn_page.setVisible(false);
            personalInfo_page.setVisible(false);
            cashIn_page.setVisible(false);
            cash_out_page.setVisible(false);
            history_page.setVisible(true);

            historyBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            personalInfoBtn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            cashInBtn.setStyle("-fx-background-color:transparent");
            cashOutBtn.setStyle("-fx-background-color:transparent");

            setupTable();

        }
    }
    /*-------------------------------------------------
                       Cash In Function
     ----------------------------------------------*/
    public void cashInFunction() throws IOException {

            int amountToCashIn = Integer.parseInt(cashInAmount.getText());
            System.out.println("Enter Amount : " + amountToCashIn);

            boolean success = false;

        String sql = "INSERT INTO transaction "
                + "(phnNum,amount) "
                + "VALUES(?,?)";

        connect = Database.connectionDb();

        try {
            Alert alert;
            if (amountToCashIn == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else
            {

                String check = "SELECT phnNum FROM transaction WHERE phnNum = '"
                        + cus.getPhoneNum() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("You already have a CASH IN pending ! ");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, cus.getPhoneNum());
                    prepare.setInt(2, amountToCashIn);

                    prepare.executeUpdate();


                    success = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(success)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Send the Request to our Employee. Your acount will be updated in a moment!");
            alert.showAndWait();



            homeBtn_page.setVisible(true);
            personalInfo_page.setVisible(false);
            cashIn_page.setVisible(false);
            cash_out_page.setVisible(false);
            history_page.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            personalInfoBtn.setStyle("-fx-background-color:transparent");
            cashInBtn.setStyle("-fx-background-color:transparent");
            cashOutBtn.setStyle("-fx-background-color:transparent");
            historyBtn.setStyle("-fx-background-color:transparent");
        }
    }

    /*-------------------------------------------------
                      Cash Out Function
    ----------------------------------------------*/
    public void cashOutFunction() throws IOException {
        int cashOutAmnt = Integer.parseInt(cashOutAmount.getText());
        int balance = cus.getBalance();

        System.out.println("Current Balance : " + balance);


        if(balance >= cashOutAmnt) {
            try {
                Alert alert;
                {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cofirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to Cash Out ?");
                    Optional<ButtonType> option = alert.showAndWait();
                    connect = Database.connectionDb();
                    if (option.get().equals(ButtonType.OK)) {
                        String updateQuery = "UPDATE customer SET balance = ? WHERE phnNum = ?";
                        PreparedStatement pstmt = connect.prepareStatement(updateQuery);

                        pstmt.setInt(1, balance-cashOutAmnt);
                        pstmt.setString(2, cus.getPhoneNum());

                        int rowsAffected = pstmt.executeUpdate();


                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Cashed Out! Please Log In again .");
                        alert.showAndWait();

                        BufferedWriter writer = new BufferedWriter(new FileWriter("cashOutInfo.txt", true)); // Append mode

                        writer.write(cus.getPhoneNum());

                        writer.write(" ");

                        String strAmount = Integer.toString(cashOutAmnt);
                        writer.write(strAmount);
                        writer.write(" ");

                        LocalDateTime currentDateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
                        String formattedTime = currentDateTime.format(formatter);

                        writer.write(formattedTime);
                        writer.newLine();
                        writer.close();

                        logout.getScene().getWindow().hide();

                        //LINK YOUR LOGIN FORM
                        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
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
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLogout(){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
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

            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) home_page.getScene().getWindow();
        stage.setIconified(true);
    }

    public void addEmployeeInsertImage()
    {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(home_page.getScene().getWindow());

        if(file != null)
        {

            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 226, 230, false, true);
            addEmployee_image.setImage(image);
        }
    }
    @FXML
    void updateImage() {
        System.out.println("Inside Update Image !");
        String sql = "INSERT INTO image "
                + "(phnNum,img) "
                + "VALUES(?,?)";

        connect = Database.connectionDb();

        try{
            Alert alert;

            if(getData.path == null || getData.path == "")
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                String check = "SELECT phnNum FROM image WHERE phnNum = '"
                        + cus.getPhoneNum()+ "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("User Alredy: " + cus.getFirstName() + " was already exist!");
                    alert.showAndWait();
                }else {

                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, cus.getPhoneNum());

                    String uri = getData.path;
                    cus.setImgUri(uri);
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(2, uri);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    System.out.println("Successfully Added");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void updateInfo(ActionEvent event) {

    }

    public void rowClicked(MouseEvent mouseEvent) {
    }


}
