package acceso.dam.hibernatecrud_acilleruelosinovas.Utils;

import acceso.dam.hibernatecrud_acilleruelosinovas.domain.Coche;
import acceso.dam.hibernatecrud_acilleruelosinovas.domain.Multa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory factory;
    static {
        Configuration cfg = new Configuration();
        cfg.configure(R.getHibernate("hibernate.cfg.xml"));

        cfg.addAnnotatedClass(Coche.class);
        cfg.addAnnotatedClass(Multa.class);
        factory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }

}
