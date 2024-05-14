package com.example.demo1;

//import de.jensd.*;jensd
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class dashboardController {
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane home_form;
    @FXML
    private AnchorPane addEmployee_form;
    @FXML
    private AnchorPane employee_database_form;

    @FXML
    private Button addEmployeeBtn;

    @FXML
    private Button addEmployeeUpdateBtn;

    @FXML
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private TableColumn<?, ?> addEmployee_col_date_merber;

    @FXML
    private TableColumn<?, ?> addEmployee_col_employeeID;

    @FXML
    private TableColumn<?, ?> addEmployee_col_firstName;

    @FXML
    private TableColumn<?, ?> addEmployee_col_gender;

    @FXML
    private TableColumn<?, ?> addEmployee_col_lastName;

    @FXML
    private TableColumn<?, ?> addEmployee_col_phone;

    @FXML
    private TableColumn<?, ?> addEmployee_col_position;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private ComboBox<?> addEmployee_gender;

    @FXML
    private AnchorPane addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_phn;

    @FXML
    private ComboBox<?> addEmployee_position;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private AnchorPane home_activeEmployees;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_chart;

    @FXML
    private BarChart<?, ?> home_employeesDataChart;

    @FXML
    private AnchorPane home_employeesPresent;

    @FXML
    private AnchorPane home_inactiveEmployees;

    @FXML
    private FontAwesomeIcon logout;

    @FXML
    private Button salary_btm;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableColumn<?, ?> salary_col_employeeID;

    @FXML
    private TableColumn<?, ?> salary_col_firstName;

    @FXML
    private TableColumn<?, ?> salary_col_lastName;

    @FXML
    private TableColumn<?, ?> salary_col_position;

    @FXML
    private TableColumn<?, ?> salary_col_salary;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private Label salary_firstName;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_lastName;

    @FXML
    private Label salary_position;

    @FXML
    private TextField salary_salary;

    @FXML
    private Button salary_updateBtn;

    @FXML
    private Label username;
    private double x = 0;
    private double y = 0;
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
    public void close()
    {
        System.exit(0);
    }
    public void minimize()
    {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchForm(ActionEvent event)
    {
        if(event.getSource() == home_btn)
        {
            home_chart.setVisible(true);
            addEmployee_form.setVisible(false);
            employee_database_form.setVisible(false);


            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addEmployeeBtn.setStyle("-fx-background-color:transparent");
            salary_btm.setStyle("-fx-background-color:transparent");

        }
        else if(event.getSource() == addEmployeeBtn)
        {
            home_chart.setVisible(false);
            addEmployee_form.setVisible(true);
            employee_database_form.setVisible(false);


            addEmployeeBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            salary_btm.setStyle("-fx-background-color:transparent");

        }
        else if(event.getSource() == salary_btm)
        {
            home_chart.setVisible(false);
            addEmployee_form.setVisible(false);
            employee_database_form.setVisible(true);


            salary_btm.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addEmployeeBtn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");

        }
    }

}
