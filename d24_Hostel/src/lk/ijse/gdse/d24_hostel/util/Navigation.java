package lk.ijse.gdse.d24_hostel.util;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Navigation {

    private static AnchorPane anchorPane;

    public static void navigate(Routes route, AnchorPane anchorPane){
        Navigation.anchorPane=anchorPane;
        Navigation.anchorPane.getChildren().clear();
        Stage window = (Stage) Navigation.anchorPane.getScene().getWindow();

        switch (route){
            case SIGN_IN_FORM:
                window.setTitle("SignIn Form");
                initUI("SignInForm",false);
                break;
            case SIGN_UP_FORM:
                window.setTitle("SignUp Form");
                initUI("SignUpForm",false);
                break;
            case Navigation_FORM:
                window.setTitle("Navigation Form");
                initUI("NavigationForm",true);
                break;
            case STUDENT_FORM:
                window.setTitle("Student Management Form");
                initUI("student/StudentForm",true);
                break;
            case ROOM_FORM:
                window.setTitle("Room Management Form");
                initUI("room/RoomForm",true);
                break;
            case RESERVATION_FORM:
                window.setTitle("Reservation Management Form");
                initUI("reservation/ReservationForm",true);
                break;
            case RESERVATION_DETAIL_FORM:
                window.setTitle("Reservation Details Form");
                initUI("reservation/ReservationDetailsForm",true);
                break;
            case USER_DETAIL_UPDATE:
                window.setTitle("User detail update Form");
                initUI("UserDetailUpdateForm",true);
                break;
            case RESERVATION_STUDENT_DETAIL_FORM:
                window.setTitle("Reservation Student Details Form");
                initUI("ReservationStudentDetailsForm",true);
                break;
        }
    }
    public static void initUI(String location,boolean flag){
        try {
            if(flag) {
                Parent root= FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/d24_hostel/resources/view/" +location+".fxml"));
                Navigation.anchorPane.getChildren().add(root);
                root.translateXProperty().set(Navigation.anchorPane.getWidth());

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    Navigation.anchorPane.getChildren().remove(anchorPane);
                });
                timeline.play();
            }else{
                Stage stage = (Stage) Navigation.anchorPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/gdse/d24_hostel/resources/view/" +location+".fxml"))));
                stage.centerOnScreen();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
