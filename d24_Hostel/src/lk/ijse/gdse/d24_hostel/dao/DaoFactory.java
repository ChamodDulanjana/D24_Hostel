package lk.ijse.gdse.d24_hostel.dao;

import lk.ijse.gdse.d24_hostel.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.gdse.d24_hostel.dao.custom.impl.RoomDAOImpl;
import lk.ijse.gdse.d24_hostel.dao.custom.impl.StudentDAOImpl;
import lk.ijse.gdse.d24_hostel.dao.custom.impl.UserDAOImpl;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public static DaoFactory getInstance(){

        return daoFactory ==null ? (daoFactory =new DaoFactory()) : daoFactory;
    }

    public <T extends SuperDAO>T getDAO(DaoType daoType){
        switch (daoType){
            case STUDENT:
                return (T) new StudentDAOImpl();
            case ROOM:
                return (T) new RoomDAOImpl();
            case RESERVATION:
                return (T) new ReservationDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            default:
                return null;
        }
    }
}
