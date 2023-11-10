package lk.ijse.gdse.d24_hostel.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse.d24_hostel.dto.UserDTO;
import lk.ijse.gdse.d24_hostel.service.ServiceFactory;
import lk.ijse.gdse.d24_hostel.service.ServiceType;
import lk.ijse.gdse.d24_hostel.service.custom.UserService;
import lk.ijse.gdse.d24_hostel.util.Navigation;
import lk.ijse.gdse.d24_hostel.util.Routes;

import java.util.List;

public class SignUpFormController {
    public AnchorPane pane;

    public Label lblAlert;



    public UserService userService;
    public List<UserDTO> userDTOList;
    public JFXPasswordField txtPassword;
    public Button btnSignUp;
    public JFXTextField txtUsername;
    public JFXPasswordField txtConfirm;

    public void initialize(){

        this.userService= ServiceFactory.getInstances().getService(ServiceType.USER);
        btnSignUp.setDisable(false);
        userDTOList=userService.findAll();
        txtUsername.requestFocus();

        txtUsername.setOnKeyReleased(event -> {

            if(isExist()){
                btnSignUp.setDisable(false);
                lblAlert.setText("User ID all ready exist !");
                txtUsername.setFocusColor(Paint.valueOf("Red"));
                txtUsername.requestFocus();
                return;
            }
            lblAlert.setText("");
            btnSignUp.setDisable(false);
        });
    }

    private boolean isExist(){

        for (UserDTO userDTO : userDTOList) {
            if (userDTO.getUserId().equalsIgnoreCase(txtUsername.getText())) {
                return true;
            }
        }
        return false;
    }
    public void btnSignUpOnAction(ActionEvent actionEvent) {

        if (txtUsername.getText().isEmpty() || !txtUsername.getText().matches("^[a-zA-Z0-9@]{4,}$")) {
            new Alert(Alert.AlertType.ERROR, "User ID invalid or empty", ButtonType.OK).show();
            txtUsername.setFocusColor(Paint.valueOf("Red"));
            txtUsername.requestFocus();
            return;
        } else if (txtPassword.getText().isEmpty() || !txtPassword.getText().matches("^[a-zA-Z0-9_]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Password invalid or empty", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtConfirm.getText().isEmpty() || !txtPassword.getText().equalsIgnoreCase(txtConfirm.getText())) {
            new Alert(Alert.AlertType.ERROR, "Confirmation dose not match or empty", ButtonType.OK).show();
            txtConfirm.setFocusColor(Paint.valueOf("Red"));
            txtConfirm.requestFocus();
            return;
        } else {
            if (userService.saveUser(new UserDTO(txtUsername.getText(), txtPassword.getText())) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the user !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "User added successfully !", ButtonType.OK).showAndWait();
            Navigation.navigate(Routes.SIGN_IN_FORM, pane);
        }
    }


    public void SignInOnAction(MouseEvent mouseEvent) {
        Navigation.navigate(Routes.SIGN_IN_FORM, pane);
    }
}
