package lk.ijse.gdse.d24_hostel.service.custom;

import lk.ijse.gdse.d24_hostel.dto.StudentDTO;
import lk.ijse.gdse.d24_hostel.service.exception.DuplicateException;

import java.util.List;

public interface StudentService {

    StudentDTO saveStudent(StudentDTO studentDTO) throws DuplicateException;

    StudentDTO updateStudent(StudentDTO studentDTO);

    void deleteStudent(StudentDTO studentDTO);

    StudentDTO getStudent(String ID);

    List<StudentDTO> findAllStudent();

    List<StudentDTO> searchStudentByText(String text);
}
