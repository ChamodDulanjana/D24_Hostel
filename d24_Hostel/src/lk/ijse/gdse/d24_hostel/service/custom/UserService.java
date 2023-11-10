package lk.ijse.gdse.d24_hostel.service.custom;

import lk.ijse.gdse.d24_hostel.dto.UserDTO;
import lk.ijse.gdse.d24_hostel.service.exception.DuplicateException;

import java.util.List;

public interface UserService {

    UserDTO getUserDetails(String ID);

    List<UserDTO> findAll();

    boolean isExistById(String ID) throws DuplicateException;

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

}
