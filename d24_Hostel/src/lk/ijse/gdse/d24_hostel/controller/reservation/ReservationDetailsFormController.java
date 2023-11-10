package lk.ijse.gdse.d24_hostel.controller.reservation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.d24_hostel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hostel.dto.StudentDTO;
import lk.ijse.gdse.d24_hostel.service.ServiceFactory;
import lk.ijse.gdse.d24_hostel.service.ServiceType;
import lk.ijse.gdse.d24_hostel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hostel.util.Navigation;
import lk.ijse.gdse.d24_hostel.util.Routes;
import lk.ijse.gdse.d24_hostel.view.tm.ReservationTm;
import lk.ijse.gdse.d24_hostel.view.tm.StudentTm;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

public class ReservationDetailsFormController {
    public TableColumn clmStudentId;
    public JFXTextField txtSearch;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmContact;
    public TableColumn clmDob;
    public TableColumn clmOption;

    public ReservationService reservationService;
    public AnchorPane pane;
    public TableView tblReservationDetail;
    double x,y=0;

    public void initialize() {
        this.reservationService = ServiceFactory.getInstances().getService(ServiceType.RESERVATION);
        initializeTable();
        setTableData();


    }

    private void setTableData() {

        tblReservationDetail.setItems(FXCollections.observableArrayList(reservationService.getReservationList().stream().map(reservationDTO -> new StudentTm(reservationDTO.getStudentDTO().getStudentId(), reservationDTO.getStudentDTO().getName(), reservationDTO.getStudentDTO().getAddress(), reservationDTO.getStudentDTO().getContact(), reservationDTO.getStudentDTO().getDob(), reservationDTO.getStudentDTO().getGender(), getBtn(reservationDTO))).collect(Collectors.toList())));
    }

    private JFXButton getBtn(ReservationDTO reservationDTO) {

        JFXButton btn = new JFXButton("Details");
        btn.setStyle("-fx-max-width: 70px; -fx-background-color: #eb2f06; -fx-background-radius: 10; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
        btn.setOnAction(event -> {
            getDetail(reservationDTO.getStudentDTO());
        });
        return btn;
    }

    private void initializeTable() {

        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

    }

    private void getDetail(StudentDTO studentDTO) {

        try {
            URL resource = this.getClass().getResource("/lk/ijse/gdse/d24_hostel/resources/view/reservation/ReservationStudentDetailsForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent load = fxmlLoader.load();
            ReservationStudentDetailsFormController controller = fxmlLoader.getController();
            controller.init(controller, studentDTO);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.centerOnScreen();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            load.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            load.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {

    }
}
