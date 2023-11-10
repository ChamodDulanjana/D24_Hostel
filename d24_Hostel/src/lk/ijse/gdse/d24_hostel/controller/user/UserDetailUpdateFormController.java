package lk.ijse.gdse.d24_hostel.controller.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.gdse.d24_hostel.dto.UserDTO;
import lk.ijse.gdse.d24_hostel.service.ServiceFactory;
import lk.ijse.gdse.d24_hostel.service.ServiceType;
import lk.ijse.gdse.d24_hostel.service.custom.UserService;

public class UserDetailUpdateFormController {

    public JFXTextField txtPassword;
    public JFXTextField txtUsername;
    public JFXButton btnSave;
    public JFXButton btnEdit;

    public UserDTO userDTO;
    public UserService userService;


    public void init(UserDTO userDTO){

        this.userService= ServiceFactory.getInstances().getService(ServiceType.USER);
        this.userDTO=userDTO;
        txtUsername.setText(userDTO.getUserId());
        txtPassword.setText(userDTO.getPassword());
        txtPassword.setEditable(false);
        btnSave.setVisible(false);

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if(txtPassword.getText().isEmpty() || !txtPassword.getText().matches("^[a-zA-Z0-9_]{8,}$")){
            new Alert(Alert.AlertType.ERROR, "Short password or empty", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        }
        userDTO.setPassword(txtPassword.getText());

        if(userService.updateUser(userDTO) == null){
            new Alert(Alert.AlertType.ERROR,"Update failed !").show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!", ButtonType.OK).show();
        exist();
    }

    public void btnEditOnAction(ActionEvent actionEvent) {

        txtPassword.setEditable(true);
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        exist();
    }

    private void exist() {

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
