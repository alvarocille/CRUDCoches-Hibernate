package acceso.dam.hibernatecrud_acilleruelosinovas.DAO;

import acceso.dam.hibernatecrud_acilleruelosinovas.domain.Multa;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Clase Data Access Object (DAO) para gestionar la colección de multas
 * en la base de datos.
 *
 * <p>Esta clase proporciona métodos para realizar operaciones CRUD (Crear,
 * Leer, Actualizar, Eliminar) sobre los documentos de la colección "multas".</p>
 */
public class MultaDAO {

    /**
     * Obtiene todas las multas de la colección.
     *
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     * @return una lista de objetos {@link Multa} que representan
     *         todas las multas en la colección.
     */
    public List<Multa> obtenerTodasLasMultas(Session session) {
        Transaction transaction = null;
        List<Multa> multas = null;
        try {
            transaction = session.beginTransaction();
            multas = session.createQuery("from Multa", Multa.class).list();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al obtener todas las multas: " + e.getMessage());
        }
        return Objects.requireNonNullElse(multas, Collections.emptyList());
    }

    /**
     * Inserta una nueva multa en la colección.
     *
     * @param multa el objeto {@link Multa} a insertar en la colección.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void insertMulta(Multa multa, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(multa);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al insertar la multa: " + e.getMessage());
        }
    }

    /**
     * Actualiza una multa existente en la colección.
     *
     * @param multa el nuevo objeto {@link Multa} con los datos actualizados.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void updateMulta(Multa multa, Session session) {
        if (multa == null || session == null) {
            throw new IllegalArgumentException("La multa y la sesión no pueden ser nulos.");
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(multa);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar la multa: " + e.getMessage());
        }
    }

    /**
     * Elimina una multa de la colección.
     *
     * @param id el id de la multa {@link Multa} que se desea eliminar.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void deleteMulta(int id, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Multa multa = session.get(Multa.class, id);
            if (multa != null) {
                session.delete(multa);
                transaction.commit();
            } else {
                System.err.println("No se encontró una multa con el ID: " + id);
            }
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar la multa: " + e.getMessage());
        }
    }
}

