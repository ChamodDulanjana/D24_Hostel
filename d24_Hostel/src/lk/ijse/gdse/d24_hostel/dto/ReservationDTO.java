package lk.ijse.gdse.d24_hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {

    private String res_id;
    private Date date;
    private String status;
    private String keyMoneyStatus;
    private StudentDTO studentDTO;
    private RoomDTO roomDTO;
}
