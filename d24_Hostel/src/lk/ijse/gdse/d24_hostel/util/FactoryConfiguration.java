package lk.ijse.gdse.d24_hostel.util;

import lk.ijse.gdse.d24_hostel.entity.Reservation;
import lk.ijse.gdse.d24_hostel.entity.Room;
import lk.ijse.gdse.d24_hostel.entity.Student;
import lk.ijse.gdse.d24_hostel.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {

        Properties properties = new Properties();

        Configuration configuration = new Configuration();

        configuration.setProperties(properties).addAnnotatedClass(Room.class).
                addAnnotatedClass(Student.class).
                addAnnotatedClass(Reservation.class).
                addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {

        return factoryConfiguration == null ? (factoryConfiguration = new FactoryConfiguration()) : factoryConfiguration;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }
}
