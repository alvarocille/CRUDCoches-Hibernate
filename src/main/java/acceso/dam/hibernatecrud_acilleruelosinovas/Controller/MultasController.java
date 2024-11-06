package acceso.dam.hibernatecrud_acilleruelosinovas.Controller;

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

import java.time.LocalDate;

public class MultasController {
    @FXML
    private Button nuevoButton, guardarButton, modificarButton, eliminarButton, limpiarButton;
    @FXML
    private DatePicker fechaField;
    @FXML
    private TextField idField, cocheField, precioField;
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
    private Coche coche;
    private Multa multaSeleccionada;

    public MultasController(Coche coche) {
        this.coche = coche;
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
            ObservableList<Multa> multas = null;
            if (coche != null) {
                multas =  FXCollections.observableArrayList(multaDAO.obtenerMultas(coche));
            } else {
                multas =  FXCollections.observableArrayList(multaDAO.obtenerTodasLasMultas(session));
            }
            tablaMultas.setItems(multas);
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
    void seleccionarMulta(MouseEvent event) {
        multaSeleccionada = tablaMultas.getSelectionModel().getSelectedItem();
        if (multaSeleccionada != null) {
            cargarMulta(multaSeleccionada);
        }
    }

    void cargarMulta(Multa multa) {

        cocheField.setText(multaSeleccionada.getCoche().toString());
        idField.setText(multaSeleccionada.getIdMulta().toString());
        precioField.setText(multaSeleccionada.getPrecio().toString());
        fechaField.setValue(multaSeleccionada.getFecha());
    }
}
