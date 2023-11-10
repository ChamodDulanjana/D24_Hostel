package lk.ijse.gdse.d24_hostel.service.util;

import lk.ijse.gdse.d24_hostel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hostel.dto.RoomDTO;
import lk.ijse.gdse.d24_hostel.dto.StudentDTO;
import lk.ijse.gdse.d24_hostel.dto.UserDTO;
import lk.ijse.gdse.d24_hostel.entity.Reservation;
import lk.ijse.gdse.d24_hostel.entity.Room;
import lk.ijse.gdse.d24_hostel.entity.Student;
import lk.ijse.gdse.d24_hostel.entity.User;

import java.util.ArrayList;

public class Convertor {

    public StudentDTO fromStudent(Student student){
        return new StudentDTO(student.getStudent_id(), student.getName(), student.getAddress(), student.getContact(), student.getDob(), student.getGender());
    }

    public Student toStudent(StudentDTO studentDTO){
        return new Student(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContact(), studentDTO.getDob(), studentDTO.getGender(), new ArrayList<>());
    }

    public RoomDTO fromRoom(Room room){
        return new RoomDTO(room.getRoomId(), room.getType(), room.getKey_money(), room.getQty(), room.getAvailableRooms());
    }

    public Room toRoom(RoomDTO roomDTO){
        return new Room(roomDTO.getRoomId(), roomDTO.getType(), roomDTO.getKeyMoney(), roomDTO.getQty(), roomDTO.getAvailableRooms(), new ArrayList<>());
    }

    public ReservationDTO fromReservation(Reservation reservation){
        return new ReservationDTO(reservation.getRes_id(), reservation.getDate(), reservation.getStatus(), reservation.getKeyMoneyStatus(), fromStudent(reservation.getStudent()), fromRoom(reservation.getRoom()));
    }

    public Reservation toReservation(ReservationDTO reservationDTO){
        return new Reservation(reservationDTO.getRes_id(), reservationDTO.getDate(), reservationDTO.getStatus(), reservationDTO.getKeyMoneyStatus(), toStudent(reservationDTO.getStudentDTO()), toRoom(reservationDTO.getRoomDTO()));
    }

    public UserDTO fromUser(User user){
        return new UserDTO(user.getUserId(), user.getPassword());
    }

    public User toUser(UserDTO userDTO){
        return new User(userDTO.getUserId(), userDTO.getPassword());
    }
}
