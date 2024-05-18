package com.example.demo1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.nio.Buffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePage implements Initializable {

    @FXML
    private TableView<transactioID> transactions;

    @FXML
    private TableColumn<transactioID, String> phnCol;

    @FXML
    private TableColumn<transactioID, Integer> amountCol;

    @FXML
    private Button logout;

    @FXML
    private TextField inputPhn;

    @FXML
    private TextField inputAmount;

    @FXML
    private Button close;

    private  double x , y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phnCol.setCellValueFactory(new PropertyValueFactory<transactioID, String>("phnNum"));
        amountCol.setCellValueFactory(new PropertyValueFactory<transactioID, Integer>("amount"));
        try {
            setupTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void close(ActionEvent event) {
            System.exit(0);
    }
    @FXML
    void submit(ActionEvent event) {
        ObservableList<transactioID> currentTableData = transactions.getItems();
        String currentPhnNum = (inputPhn.getText());
        int currentAmount = Integer.parseInt(inputAmount.getText());

        for (transactioID animal : currentTableData) {
            if(animal.getPhnNum() == currentPhnNum) {
                animal.setAmount(Integer.parseInt(inputAmount.getText()));
                animal.setPhnNum(inputPhn.getText());

                transactions.setItems(currentTableData);
                transactions.refresh();
                break;
            }
        }

        String sql = "DELETE FROM transaction WHERE phnNum = '"
                + currentPhnNum + "'";

        Connection connect = Database.connectionDb();

        try {

            Alert alert;
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Permit Employee ID: " + currentPhnNum + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String updateQuery = "UPDATE customer SET balance = balance + ? WHERE phnNum = ?";
                    PreparedStatement pstmt = connect.prepareStatement(updateQuery);
                    System.out.println("Current Balance : " + currentAmount );
                    pstmt.setInt(1, currentAmount);
                    pstmt.setString(2, currentPhnNum);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Balance updated successfully.");
                        System.out.println("Current Amount");
                    } else {
                        System.out.println("Customer not found.");
                    }

                    Statement statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM transaction WHERE phnNum = '"
                            + currentPhnNum + "'";

                    PreparedStatement prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Permitted!");
                    alert.showAndWait();

                    setupTable();

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("transactionID.txt", true)); // Append mode
                        System.out.println("Current Phone Number : " + currentPhnNum);
                        System.out.println("Current Amount Number : " + currentAmount);

                        // Write currentPhnNum to file
                        writer.write(currentPhnNum);

                        // Insert a space between currentPhnNum and currentAmount
                        writer.write(" ");

                        // Convert currentAmount to string and write to file
                        String strAmount = Integer.toString(currentAmount);
                        writer.write(strAmount);
                        writer.write(" ");

                        LocalDateTime currentDateTime = LocalDateTime.now();

                        // Define a custom formatter
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");

                        // Convert the date and time to a string
                        String formattedTime = currentDateTime.format(formatter);

                        writer.write(formattedTime);
                        writer.newLine(); // Start a new line

                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void rowClicked(MouseEvent event) {
        transactioID clickedTransaction = transactions.getSelectionModel().getSelectedItem();
        inputPhn.setText(String.valueOf(clickedTransaction.getPhnNum()));
        inputAmount.setText(String.valueOf(clickedTransaction.getAmount()));
    }
    @FXML
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
//                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupTable() throws SQLException {

        Connection connect = Database.connectionDb();
        String check = "SELECT phnNum, amount FROM transaction";
        Statement statement = connect.createStatement();
        ResultSet result = statement.executeQuery(check);

        transactions.getItems().removeAll();
        transactions.getItems().clear();
        inputAmount.clear();
        inputPhn.clear();

            while (result.next()) {
                String phn = result.getString("phnNum");
                int ama = result.getInt("amount");
                transactions.getItems().add(new transactioID(phn, ama));
            }
        }


}

