package acceso.dam.hibernatecrud_acilleruelosinovas.Controller;

import acceso.dam.hibernatecrud_acilleruelosinovas.DAO.CocheDAO;
import acceso.dam.hibernatecrud_acilleruelosinovas.DAO.MultaDAO;
import acceso.dam.hibernatecrud_acilleruelosinovas.Utils.HibernateUtil;
import acceso.dam.hibernatecrud_acilleruelosinovas.domain.Coche;
import acceso.dam.hibernatecrud_acilleruelosinovas.domain.Multa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.time.LocalDate;

import static acceso.dam.hibernatecrud_acilleruelosinovas.AppConfig.ejemplo;

public class MultasController {
    @FXML
    private Button nuevoButton, guardarButton, modificarButton, eliminarButton, limpiarButton;
    @FXML
    private DatePicker fechaField;
    @FXML
    private TextField idField, matriculaField, precioField;
    @FXML
    private TableView<Multa> tablaMultas;
    @FXML
    private TableColumn<Multa, Integer> colMulta;
    @FXML
    private TableColumn<Multa, Double> colPrecio;
    @FXML
    private TableColumn<Multa, LocalDate> colFecha;

    SessionFactory factory = HibernateUtil.getSessionFactory();
    Session session = HibernateUtil.getSession();
    private final MultaDAO multaDAO;

    public MultasController() {
        multaDAO = new MultaDAO();
    }

    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarMultas();
    }

    private void configurarColumnas() {
        colMulta.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));
        colPrecio.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));
        colFecha.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));

        colMulta.setCellValueFactory(new PropertyValueFactory<>("idMulta"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    public void cargarMultas() {
        try {
            session.clear();
            ObservableList<Multa> multas = FXCollections.observableArrayList(multaDAO.obtenerTodasLasMultas(session));
            if (multas.isEmpty() && ejemplo) {
                ejemplo = false;
                generarDatosMultas();
                cargarMultas();
            } else {
                tablaMultas.setItems(multas);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los coches: " + e.getMessage());
        }
    }

    @FXML
    void actualizarCambios(ActionEvent event) {

    }

    @FXML
    void crearNuevo(ActionEvent event) {

    }

    @FXML
    void eliminarMulta(ActionEvent event) {

    }

    @FXML
    void guardarMulta(ActionEvent event) {

    }

    @FXML
    void limpiarDatos(ActionEvent event) {

    }

    @FXML
    void seleccionarCoche(MouseEvent event) {

    }

    public void generarDatosMultas() {
        try {
            Multa multa1 = new Multa(100.0, Date.valueOf("2024-01-10").toLocalDate(), "1122BBC");
            Multa multa2 = new Multa(150.0, Date.valueOf("2024-02-12").toLocalDate(), "2233DDB");
            Multa multa3 = new Multa(200.0, Date.valueOf("2024-03-15").toLocalDate(), "3344FFC");
            Multa multa4 = new Multa(120.0, Date.valueOf("2024-04-18").toLocalDate(), "4455BBZ");
            Multa multa5 = new Multa(175.0, Date.valueOf("2024-05-20").toLocalDate(), "5566CCR");
            Multa multa6 = new Multa(90.0, Date.valueOf("2024-06-22").toLocalDate(), "6677FFD");
            Multa multa7 = new Multa(130.0, Date.valueOf("2024-07-25").toLocalDate(), "7788JJZ");
            Multa multa8 = new Multa(160.0, Date.valueOf("2024-08-27").toLocalDate(), "8899BBV");
            Multa multa9 = new Multa(140.0, Date.valueOf("2024-09-30").toLocalDate(), "9911FFG");
            Multa multa10 = new Multa(110.0, Date.valueOf("2024-10-05").toLocalDate(), "8855GFR");

            multaDAO.insertMulta(multa1, session);
            multaDAO.insertMulta(multa2, session);
            multaDAO.insertMulta(multa3, session);
            multaDAO.insertMulta(multa4, session);
            multaDAO.insertMulta(multa5, session);
            multaDAO.insertMulta(multa6, session);
            multaDAO.insertMulta(multa7, session);
            multaDAO.insertMulta(multa8, session);
            multaDAO.insertMulta(multa9, session);
            multaDAO.insertMulta(multa10, session);
        } catch (Exception e) {
            System.err.println("Error al insertar las multas: " + e.getMessage());
        }
    }

}
