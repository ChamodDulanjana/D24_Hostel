package lk.ijse.gdse.d24_hostel.service.custom;

import lk.ijse.gdse.d24_hostel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hostel.dto.StudentDTO;
import lk.ijse.gdse.d24_hostel.service.exception.AllReadyBookedException;

import java.util.List;

public interface ReservationService {

    ReservationDTO saveReservation(ReservationDTO reservationDTO) throws AllReadyBookedException;

    String getNextId();

    boolean updateReservationStatus(String studentId);

    List<ReservationDTO> getReservationList();

    List<ReservationDTO> getReservationListByStudentId(String studentId);

    ReservationDTO updateReservation(ReservationDTO reservationDTO);

}
