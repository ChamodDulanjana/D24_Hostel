package lk.ijse.gdse.d24_hostel.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import lk.ijse.gdse.d24_hostel.dto.UserDTO;
import lk.ijse.gdse.d24_hostel.service.ServiceFactory;
import lk.ijse.gdse.d24_hostel.service.ServiceType;
import lk.ijse.gdse.d24_hostel.service.custom.UserService;
import lk.ijse.gdse.d24_hostel.util.Navigation;
import lk.ijse.gdse.d24_hostel.util.Routes;
import lk.ijse.gdse.d24_hostel.util.UserHolder;

import java.io.IOException;
import java.net.URL;

public class SignInFormController {
    public JFXTextField txtUserId;
    public UserService userService;
    public AnchorPane pane;
    public ImageView imgHide;
    public ImageView imgShow;
    public JFXTextField txtShowPassword;
    public JFXPasswordField txtHidePassword;

    public void initialize(){

        this.userService= ServiceFactory.getInstances().getService(ServiceType.USER);
        txtShowPassword.setVisible(false);
        imgHide.setVisible(false);
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {

        if (txtUserId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User ID is empty", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtShowPassword.isVisible() && txtShowPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtShowPassword.setFocusColor(Paint.valueOf("Red"));
            txtShowPassword.requestFocus();
            return;
        } else if (txtHidePassword.isVisible() && txtHidePassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtHidePassword.setFocusColor(Paint.valueOf("Red"));
            txtHidePassword.requestFocus();
            return;
        }

        UserDTO user = userService.getUserDetails(txtUserId.getText());

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, " Invalid User ID", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtShowPassword.isVisible() && !user.getPassword().equalsIgnoreCase(txtShowPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid password", ButtonType.OK).show();
            txtShowPassword.setFocusColor(Paint.valueOf("Red"));
            txtShowPassword.requestFocus();
            return;
        } else if (txtHidePassword.isVisible() && !user.getPassword().equalsIgnoreCase(txtHidePassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid password", ButtonType.OK).show();
            txtHidePassword.setFocusColor(Paint.valueOf("Red"));
            txtHidePassword.requestFocus();
            return;
        } else {
            try {
                UserHolder.getInstance().setUserDTO(user);
                URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hostel/resources/view/NavigationForm.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(resource);
                Stage stage = new Stage();
                AnchorPane container = fxmlLoader.load(resource);
                AnchorPane pneContainer = (AnchorPane)container.lookup("#pane");
                stage.setScene(new Scene(container));
                stage.centerOnScreen();
                Stage window =(Stage) txtShowPassword.getScene().getWindow();
                window.hide();
                Navigation.navigate(Routes.STUDENT_FORM, pneContainer);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void signUpOnAction(MouseEvent mouseEvent) {
        Navigation.navigate(Routes.SIGN_UP_FORM, pane);
    }

    public void imgShowOnAction(MouseEvent mouseEvent) {
        imgShow.setVisible(false);
        imgHide.setVisible(true);
        txtHidePassword.setVisible(false);
        txtShowPassword.setVisible(true);
        txtShowPassword.setText(txtHidePassword.getText());

    }

    public void imgHideOnAction(MouseEvent mouseEvent) {
        imgHide.setVisible(false);
        imgShow.setVisible(true);
        txtShowPassword.setVisible(false);
        txtHidePassword.setVisible(true);
    }
}
