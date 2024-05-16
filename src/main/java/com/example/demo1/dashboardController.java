package com.example.demo1;

//import de.jensd.*;jensd
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


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
    private TableView<employeeData> addEmployee_tableView;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_date_merber;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_employeeID;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_firstName;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_gender;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_lastName;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_phone;

    @FXML
    private TableColumn<employeeData,String > addEmployee_col_position;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;
    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private ComboBox<?> addEmployee_gender;


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

    private  Connection connect;
    private Statement statement;
    private  PreparedStatement prepare ;
    private  ResultSet result;
    private Image image;

    public void addEmployeeInsertImage()
    {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if(file != null)
        {

            getData.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 144, 147, false, true);
            addEmployee_image.setImage(image);
        }
    }


    private String[] positionList = {"Marketer Coordinator", "Web Developer (Back End)", "Web Developer (Front End)", "App Developer"};

    public void addEmployeePositionList() {
        List<String> listP = new ArrayList<>();

        for (String data : positionList) {
            listP.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        addEmployee_position.setItems(listData);
    }

    private String[] listGender = {"Male", "Female", "Others"};

    public void addEmployeeGendernList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        addEmployee_gender.setItems(listData);
    }

    public ObservableList<employeeData>addEmployeeListData(){
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        connect = Database.connectionDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            employeeData employeed;

            while(result.next()){
                employeed = new employeeData(
                        result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("phoneNum"),
                        result.getString("position"),
                        result.getString("image"),
                        result.getDate("date")

                );
                listData.add(employeed);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  listData;
    }
    private ObservableList<employeeData> addEmployeeList;
    public void addEmployeeShowListData()
    {
        addEmployeeList = addEmployeeListData();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date_merber.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_tableView.setItems(addEmployeeList);

    }

    public void addemployeeAdd()
    {
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "INSERT INTO employee "
                + "(employee_id,firstName,lastName,gender,phoneNum,position,image,date) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = Database.connectionDb();

        try {
            Alert alert;
            if (addEmployee_employeeID.getText().isEmpty()
                    || addEmployee_firstName.getText().isEmpty()
                    || addEmployee_lastName.getText().isEmpty()
                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_phn.getText().isEmpty()
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else
            {

                String check = "SELECT employee_id FROM employee WHERE employee_id = '"
                        + addEmployee_employeeID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + addEmployee_employeeID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addEmployee_employeeID.getText());
                    prepare.setString(2, addEmployee_firstName.getText());
                    prepare.setString(3, addEmployee_lastName.getText());
                    prepare.setString(4, (String) addEmployee_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addEmployee_phn.getText());
                    prepare.setString(6, (String) addEmployee_position.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);
                    prepare.setString(8, String.valueOf(sqlDate));
                    prepare.executeUpdate();

//                    String insertInfo = "INSERT INTO employee "
//                            + "(employee_id,firstName,lastName,position,salary,date) "
//                            + "VALUES(?,?,?,?,?,?)";
//
//                    prepare = connect.prepareStatement(insertInfo);
//                    prepare.setString(1, addEmployee_employeeID.getText());
//                    prepare.setString(2, addEmployee_firstName.getText());
//                    prepare.setString(3, addEmployee_lastName.getText());
//                    prepare.setString(4, (String) addEmployee_position.getSelectionModel().getSelectedItem());
//                    prepare.setString(5, "0.0");
//                    prepare.setString(6, String.valueOf(sqlDate));
//                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    System.out.println("Successfully Added");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        addEmployeeShowListData();

    }

    public void addEmployeeReset() {
        addEmployee_employeeID.setText("");
        addEmployee_firstName.setText("");
        addEmployee_lastName.setText("");
        addEmployee_gender.getSelectionModel().clearSelection();
        addEmployee_position.getSelectionModel().clearSelection();
        addEmployee_phn.setText("");
        addEmployee_image.setImage(null);
        getData.path = "";
    }
    public void addEmployeeDelete() {

        String sql = "DELETE FROM employee WHERE employee_id = '"
                + addEmployee_employeeID.getText() + "'";

        connect = Database.connectionDb();

        try {

            Alert alert;
//            if (addEmployee_employeeID.getText().isEmpty()
//                    || addEmployee_firstName.getText().isEmpty()
//                    || addEmployee_lastName.getText().isEmpty()
//                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
//                    || addEmployee_phn.getText().isEmpty()
//                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
//                    || getData.path == null || getData.path == "") {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Please fill all blank fields");
//                alert.showAndWait();
//            } else
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + addEmployee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM employee WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    try{
                        addEmployeeShowListData();
                        System.out.println("Showed!");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeUpdate() {

        String uri = getData.path;
        System.out.println(uri);
        employeeData employeeD = addEmployee_tableView.getSelectionModel().getSelectedItem();


        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "UPDATE employee SET firstName = '"
                + addEmployee_firstName.getText() + "', lastName = '"
                + addEmployee_lastName.getText() + "', gender = '"
                + addEmployee_gender.getSelectionModel().getSelectedItem() + "', phoneNum = '"
                + addEmployee_phn.getText() + "', position = '"
                + addEmployee_position.getSelectionModel().getSelectedItem() + "', image = '"
                + employeeD.getImage() + "', date = '" + sqlDate + "' WHERE employee_id ='"
                + addEmployee_employeeID.getText() + "'";

        System.out.println("Inside Update");
        System.out.println(addEmployee_image);

        connect = Database.connectionDb();

        try {
            Alert alert;
//            if (addEmployee_employeeID.getText().isEmpty()
//                    || addEmployee_firstName.getText().isEmpty()
//                    || addEmployee_lastName.getText().isEmpty()
//                    || addEmployee_gender.getSelectionModel().getSelectedItem() == null
//                    || addEmployee_phn.getText().isEmpty()
//                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
//                    || getData.path == null || getData.path == "")

            if (
                    addEmployee_gender.getSelectionModel().getSelectedItem() == null
                    || addEmployee_position.getSelectionModel().getSelectedItem() == null
                    )

            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else
            {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Employee ID: " + addEmployee_employeeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    double salary = 0;

                    String checkData = "SELECT * FROM employee WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

//                    while (result.next()) {
//                        salary = result.getDouble("salary");
//                    }

                    String updateInfo = "UPDATE employee SET firstName = '"
                            + addEmployee_firstName.getText() + "', lastName = '"
                            + addEmployee_lastName.getText() + "', position = '"
                            + addEmployee_position.getSelectionModel().getSelectedItem()
                            + "' WHERE employee_id = '"
                            + addEmployee_employeeID.getText() + "'";

                    prepare = connect.prepareStatement(updateInfo);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addEmployeeSelect()
    {
        employeeData employeeD = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1) return;

        addEmployee_employeeID.setText(String.valueOf(employeeD.getEmployeeId()));
        addEmployee_firstName.setText(employeeD.getFirstName());
        addEmployee_lastName.setText(employeeD.getLastName());
        addEmployee_phn.setText(employeeD.getPhoneNum());

        String uri = "file:" + employeeD.getImage();
        System.out.println(uri);

        image = new Image(uri ,144, 147, false, true);
        addEmployee_image.setImage(image);

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

    public void displayUsername()
    {
        username.setText(getData.userName);
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

            addEmployeeGendernList();
            addEmployeePositionList();
            addEmployeeShowListData();

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
//    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        addEmployeeShowListData();
        addEmployeePositionList();
        addEmployeeGendernList();
        addEmployeeShowListData();

    }

}
