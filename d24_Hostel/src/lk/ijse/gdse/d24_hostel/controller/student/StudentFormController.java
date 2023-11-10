package lk.ijse.gdse.d24_hostel.controller.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.d24_hostel.dto.StudentDTO;
import lk.ijse.gdse.d24_hostel.service.ServiceFactory;
import lk.ijse.gdse.d24_hostel.service.ServiceType;
import lk.ijse.gdse.d24_hostel.service.custom.StudentService;
import lk.ijse.gdse.d24_hostel.service.exception.DuplicateException;
import lk.ijse.gdse.d24_hostel.view.tm.StudentTm;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentFormController {
    public TableView tblStudent;
    public TableColumn clmStudentId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmContact;
    public TableColumn clmDob;
    public TableColumn clmGender;
    public TableColumn clmOption;
    public JFXTextField txtStudentId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtDob;
    public JFXComboBox cmbGender;
    public JFXButton btnAddUpdate;
    public JFXTextField txtSearch;
    public JFXButton btnClear;

    public StudentService studentService;

    public void initialize(){
        this.studentService= ServiceFactory.getInstances().getService(ServiceType.STUDENT);
        initializeTable();
        setTableData();
    }

    private void initializeTable() {

        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue){
                setData((StudentTm)newValue);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #8c7ae6; -fx-background-radius: 10px");
            }
        });

        cmbGender.setItems(FXCollections.observableArrayList("Male","Female"));
    }

    private void setTableData() {

        tblStudent.setItems(FXCollections.observableArrayList(studentService.findAllStudent().stream().map(studentDTO -> new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender(), getBtn(studentDTO))).collect(Collectors.toList())));
    }

    private JFXButton getBtn(StudentDTO studentDTO) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-max-width: 80px; -fx-background-color: #e84118; -fx-background-radius: 10; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        btn.setOnAction(event -> {
            deleteStudent(studentDTO);
        });
        return btn;
    }

    private void deleteStudent(StudentDTO studentDTO) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            studentService.deleteStudent(studentDTO);

            new Alert(Alert.AlertType.CONFIRMATION, "Student deleted successfully !", ButtonType.OK).show();
            clear();

            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchStudent();
                return;
            }
            setTableData();
            clear();
        }
    }

    private void searchStudent() {

        tblStudent.setItems(FXCollections.observableArrayList(studentService.searchStudentByText(txtSearch.getText()).stream().map(studentDTO -> new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender(), getBtn(studentDTO))).collect(Collectors.toList())));
    }


    private void setData(StudentTm studentTm) {

        txtStudentId.setEditable(false);
        txtStudentId.setText(studentTm.getStudentId());
        txtName.setText(studentTm.getName());
        txtAddress.setText(studentTm.getAddress());
        txtContact.setText(studentTm.getContact());
        txtDob.setText(String.valueOf(studentTm.getDob()));
        cmbGender.setValue(studentTm.getGender());
    }

    public void btnAddUpdateOnAction(ActionEvent actionEvent) {

        if(txtStudentId.getText().isEmpty() || !txtStudentId.getText().matches("^(SI)([0-9]{4,}$)")){
            new Alert(Alert.AlertType.ERROR, "Student ID is Invalid or empty", ButtonType.OK).show();
            txtStudentId.setFocusColor(Paint.valueOf("Red"));
            txtStudentId.requestFocus();
            return;
        }else if (txtName.getText().isEmpty() || !txtName.getText().matches("([\\w ]{4,})")) {
            new Alert(Alert.AlertType.ERROR, "Name text Invalid or empty", ButtonType.OK).show();
            txtName.setFocusColor(Paint.valueOf("Red"));
            txtName.requestFocus();
            return;
        }else if (txtAddress.getText().isEmpty() || !txtAddress.getText().matches("^[0-9a-zA-Z]{5,}")) {
            new Alert(Alert.AlertType.ERROR, "Address text Invalid or empty",ButtonType.OK).show();
            txtAddress.setFocusColor(Paint.valueOf("Red"));
            txtAddress.requestFocus();
            return;
        }else if (txtContact.getText().isEmpty() || !txtContact.getText().matches("^(075|077|071|074|078|076|070|072)([0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Contact number Invalid or empty", ButtonType.OK).show();
            txtContact.setFocusColor(Paint.valueOf("Red"));
            txtContact.requestFocus();
            return;
        }else if(txtDob.getText().isEmpty() || !txtDob.getText().matches("^([0-9]{4})(-)([0-9]{2})(-)([0-9]{2}$)")){
            new Alert(Alert.AlertType.ERROR, "Please input DOB Correctly (2000-12-18)", ButtonType.OK).show();
            txtDob.setFocusColor(Paint.valueOf("Red"));
            txtDob.requestFocus();
            return;
        }else if (cmbGender.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select thr gender !",ButtonType.OK).show();
            cmbGender.setFocusColor(Paint.valueOf("Red"));
            cmbGender.requestFocus();
            return;
        }else if (btnAddUpdate.getText().equalsIgnoreCase("Add")) {
            try {
                if(studentService.saveStudent(new StudentDTO(txtStudentId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(),
                        Date.valueOf(txtDob.getText()), cmbGender.getValue().toString()))==null){
                    new Alert(Alert.AlertType.ERROR,"Student update failed !").show();
                    return;
                }
                new Alert(Alert.AlertType.CONFIRMATION, "Student update successfully !", ButtonType.OK).show();
                setTableData();
                clear();
                return;
            }catch (DuplicateException e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                txtStudentId.setFocusColor(Paint.valueOf("Red"));
                txtStudentId.requestFocus();
            }
        } else {
            if(studentService.updateStudent(new StudentDTO(txtStudentId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(),
                    Date.valueOf(txtDob.getText()), cmbGender.getValue().toString()))==null){
                new Alert(Alert.AlertType.ERROR,"Student update failed !").show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Student update successfully !", ButtonType.OK).show();
            setTableData();
            clear();
            return;
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    private void clear() {

        txtStudentId.setEditable(true);
        txtStudentId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtDob.clear();
        cmbGender.getSelectionModel().clearSelection();
        btnAddUpdate.setText("Add");
        btnAddUpdate.setStyle("-fx-background-color: #273c75; -fx-background-radius: 10px");
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {
        searchStudent();
    }
}
