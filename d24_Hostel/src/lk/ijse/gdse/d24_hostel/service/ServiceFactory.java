package lk.ijse.gdse.d24_hostel.service;

import lk.ijse.gdse.d24_hostel.service.custom.impl.ReservationServiceImpl;
import lk.ijse.gdse.d24_hostel.service.custom.impl.RoomServiceImpl;
import lk.ijse.gdse.d24_hostel.service.custom.impl.StudentServiceImpl;
import lk.ijse.gdse.d24_hostel.service.custom.impl.UserServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstances(){
        return serviceFactory==null ? (serviceFactory=new ServiceFactory()) : serviceFactory;
    }

    public  <T>T getService(ServiceType serviceType){
        switch (serviceType){
            case STUDENT:
                return (T) new StudentServiceImpl();
            case ROOM:
                return (T) new RoomServiceImpl();
            case RESERVATION:
                return (T) new ReservationServiceImpl();
            case USER:
                return (T) new UserServiceImpl();
            default:
                return null;
        }
    }
}
