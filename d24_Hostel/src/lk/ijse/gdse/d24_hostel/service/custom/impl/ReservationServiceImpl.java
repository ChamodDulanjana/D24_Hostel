package lk.ijse.gdse.d24_hostel.service.custom.impl;

import lk.ijse.gdse.d24_hostel.dto.ReservationDTO;
import lk.ijse.gdse.d24_hostel.entity.Reservation;
import lk.ijse.gdse.d24_hostel.dao.DaoFactory;
import lk.ijse.gdse.d24_hostel.dao.DaoType;
import lk.ijse.gdse.d24_hostel.dao.custom.ReservationDAO;
import lk.ijse.gdse.d24_hostel.dao.custom.RoomDAO;
import lk.ijse.gdse.d24_hostel.service.custom.ReservationService;
import lk.ijse.gdse.d24_hostel.service.exception.AllReadyBookedException;
import lk.ijse.gdse.d24_hostel.service.util.Convertor;
import lk.ijse.gdse.d24_hostel.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;
    private final RoomDAO roomDAO;
    private final Convertor convertor;

    public ReservationServiceImpl() {

        reservationDAO = DaoFactory.getInstance().getDAO(DaoType.RESERVATION);
        roomDAO = DaoFactory.getInstance().getDAO(DaoType.ROOM);
        convertor = new Convertor();

    }

    @Override
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) throws AllReadyBookedException {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (reservationDAO.isExistBook(session, reservationDTO.getStudentDTO().getStudentId()))
                throw new AllReadyBookedException("Student has all ready booked the  one the room ! Do you want to closed existing reservation ?");

            if (reservationDAO.save(session, convertor.toReservation(reservationDTO)) == null)
                throw new RuntimeException("failed to save reservation !");

            if (!roomDAO.updateAvailableRooms(session, reservationDTO.getRoomDTO().getRoomId(), false))
                throw new RuntimeException("Failed to update available room count !");

            transaction.commit();
            return reservationDTO;

        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            return getNext(reservationDAO.getNextReservationId(session));

        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateReservationStatus(String studentId) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            Reservation reservation = reservationDAO.getBeforeUpdateReservation(session, studentId);

            if (!reservationDAO.updateReservationStatus(session, studentId))
                throw new RuntimeException("Failed to update reservation status !");

            if (!roomDAO.updateAvailableRooms(session, reservation.getRoom().getRoomId(), true))
                throw new RuntimeException("Failed to update available rooms !");

            transaction.commit();
            return true;

        } finally {
            session.close();
        }
    }

    @Override
    public List<ReservationDTO> getReservationList() {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return reservationDAO.getAllGroupByStudentId(session).stream().map(reservation -> convertor.fromReservation(reservation)).collect(Collectors.toList());
        } finally {
            session.close();
        }
    }

    @Override
    public List<ReservationDTO> getReservationListByStudentId(String studentId) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return reservationDAO.getReservationByStudentId(session, studentId).stream().map(reservation -> convertor.fromReservation(reservation)).collect(Collectors.toList());
        } finally {
            session.close();
        }
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            if (reservationDAO.update(session, convertor.toReservation(reservationDTO)) == null)
                throw new RuntimeException("Failed to update reservation !");

            transaction.commit();
            return reservationDTO;

        } finally {
            session.close();
        }
    }

    private String getNext(String oldId) {

        if (oldId != null) {
            String[] split = oldId.split("[-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("RE-%07d", lastDigits);
            return newRoomId;
        } else {
            return "RE-0000001";
        }
    }
}
