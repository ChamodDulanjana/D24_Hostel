package lk.ijse.gdse.d24_hostel.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.d24_hostel.util.Navigation;
import lk.ijse.gdse.d24_hostel.util.Routes;

public class LandingFormController {


    public AnchorPane pane01;

    public void btnStartOnAction(ActionEvent actionEvent) {
        Navigation.navigate(Routes.SIGN_IN_FORM, pane01);
    }
}
