package lk.ijse.gdse.d24_hostel.service.custom;

import lk.ijse.gdse.d24_hostel.dto.RoomDTO;
import lk.ijse.gdse.d24_hostel.service.exception.DuplicateException;

import java.util.List;

public interface RoomService {

    RoomDTO saveRoom(RoomDTO roomDTO) throws DuplicateException;

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(RoomDTO roomDTO);

    RoomDTO getRoom(String ID);

    List<RoomDTO> findAllRoom();

    List<RoomDTO> searchRoomByText(String text);
}
