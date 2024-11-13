package acceso.dam.hibernatecrud_acilleruelosinovas.Controller;

import acceso.dam.hibernatecrud_acilleruelosinovas.DAO.CocheDAO;
import acceso.dam.hibernatecrud_acilleruelosinovas.DAO.MultaDAO;
import acceso.dam.hibernatecrud_acilleruelosinovas.Utils.AlertUtils;
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
import java.util.Optional;

/**
 * Controlador de la vista de multas, maneja la lógica de la interfaz de usuario relacionada con la gestión de multas.
 * Permite crear, modificar, eliminar y visualizar multas asociadas a un coche específico.
 */
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
    private final Coche coche;
    private Multa multaSeleccionada;

    public MultasController(Coche coche) {
        this.coche = coche;
        multaDAO = new MultaDAO();
    }

    private boolean editando = false;
    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    /**
     * Inicializa la vista de multas, configura las columnas de la tabla y carga las multas asociadas al coche.
     */
    @FXML
    public void initialize() {
        modoEdicion(editando);
        configurarColumnas();
        cargarMultas();
    }

    /**
     * Configura las columnas de la tabla de multas.
     * Define qué propiedades de la entidad Multa se mostrarán en cada columna.
     */
    private void configurarColumnas() {
        colMulta.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));
        colPrecio.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));
        colFecha.prefWidthProperty().bind(tablaMultas.widthProperty().multiply(0.33));

        colMulta.setCellValueFactory(new PropertyValueFactory<>("idMulta"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    }

    /**
     * Carga las multas asociadas al coche seleccionado o todas las multas si el coche es null.
     */
    public void cargarMultas() {
        try {
            ObservableList<Multa> multas = null;
            if (coche != null) {
                multas =  FXCollections.observableArrayList(multaDAO.obtenerMultas(coche));
            } else {
                multas =  FXCollections.observableArrayList(multaDAO.obtenerTodasLasMultas(session));
            }
            tablaMultas.setItems(multas);
        } catch (Exception e) {
            System.err.println("Error al cargar las multas: " + e.getMessage());
        }
    }

    /**
     * Cambia el modo de edición entre nuevo y modificar para la interfaz de usuario.
     *
     * @param event Evento de acción.
     */
    @FXML
    void actualizarCambios(ActionEvent event) {
        accion = Accion.MODIFICAR;
        modoEdicion(!editando);
        editando = !editando;
    }

    /**
     * Inicia el proceso de creación de una nueva multa, limpiando los campos y cambiando el modo de edición.
     *
     * @param event Evento de acción.
     */
    @FXML
    void crearNuevo(ActionEvent event) {
        accion = Accion.NUEVO;
        limpiarDatos();
        modoEdicion(!editando);
        editando = !editando;
    }

    /**
     * Elimina la multa seleccionada después de confirmar la acción con el usuario.
     *
     * @param event Evento de acción.
     */
    @FXML
    void eliminarMulta(ActionEvent event) {
        Multa multa = tablaMultas.getSelectionModel().getSelectedItem();
        if (multa == null) {
            mostrarError("No hay multa seleccionada");
            return;
        }

        Optional<ButtonType> respuesta = pedirConfirmacion("Eliminar multa: ¿Estás seguro?");
        if (respuesta.isPresent()) {
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;
        }

        multaDAO.deleteMulta(multa.getIdMulta(), session);
        mostrarConfirmacion("Multa eliminada con éxito");
        cargarMultas();
        limpiarDatos();
    }

    /**
     * Guarda los cambios de la multa, ya sea para crear una nueva o modificar una existente.
     *
     * @param event Evento de acción.
     */
    @FXML
    void guardarMulta(ActionEvent event) {
        Integer id = multaSeleccionada != null && accion == Accion.MODIFICAR ? multaSeleccionada.getIdMulta() : null;

        String idCocheString = cocheField.getText();
        if (!validarTextoNoVacio(idCocheString)) {
            mostrarError("El ID del coche al que pertenece la multa es obligatorio.");
            return;
        }
        CocheDAO cocheDAO = new CocheDAO();
        Coche coche = cocheDAO.obtenerCoche(parseInt(idCocheString), session);
        if (coche == null) {
            mostrarError("No se encontró un coche con el ID proporcionado.");
            return;
        }

        String precio = precioField.getText();
        if (!validarTextoNoVacio(precio)) {
            mostrarError("La multa debe de tener un importe.");
            return;
        }

        String fechaString = fechaField.getValue().toString();
        if (!validarTextoNoVacio(fechaString)) {
            mostrarAviso("Se va a registrar la multa sin fecha.");
        }
        LocalDate fecha = fechaField.getValue();

        Multa multa = new Multa(id,parseDouble(precio), fecha, coche, coche.getId());

        try {
            switch (accion) {
                case NUEVO -> multaDAO.insertMulta(multa, session);
                case MODIFICAR -> multaDAO.updateMulta(multa, session);
            }
        } catch (Exception e) {
            AlertUtils.mostrarError("Error al guardar la multa: " + e.getMessage());
        }

        if (accion == Accion.MODIFICAR && !multaSeleccionada.getCoche().equals(multa.getCoche())) {
            multaSeleccionada.getCoche().getMultas().remove(multaSeleccionada);
            multa.getCoche().getMultas().add(multa);
        }

        modoEdicion(false);
        cargarMultas();
    }

    /**
     * Limpia los datos en los campos del formulario.
     */
    @FXML
    void limpiarDatos() {
        idField.clear();
        cocheField.clear();
        precioField.clear();
        fechaField.setValue(null);
    }

    /**
     * Carga los datos de una multa seleccionada en los campos del formulario.
     *
     * @param event Evento de acción.
     */
    @FXML
    void seleccionarMulta(MouseEvent event) {
        multaSeleccionada = tablaMultas.getSelectionModel().getSelectedItem();
        if (multaSeleccionada != null) {
            cargarMulta(multaSeleccionada);
        }
    }

    /**
     * Activa o desactiva el modo de edición en la interfaz de usuario.
     *
     * @param activar True para activar el modo de edición, false para desactivarlo.
     */
    private void modoEdicion(boolean activar) {
        if (!activar || accion == Accion.MODIFICAR) {
            nuevoButton.setDisable(activar);
        }
        if (!activar || accion == Accion.NUEVO) {
            modificarButton.setDisable(activar);
        }

        guardarButton.setDisable(!activar);
        eliminarButton.setDisable(activar);

        cocheField.setEditable(activar);
        precioField.setEditable(activar);
        fechaField.setEditable(activar);

        tablaMultas.setDisable(activar);
    }

    /**
     * Carga los datos de la multa seleccionada en los campos del formulario.
     *
     * @param multa La multa seleccionada.
     */
    void cargarMulta(Multa multa) {
        cocheField.setText(multaSeleccionada.getCocheID().toString());
        idField.setText(multaSeleccionada.getIdMulta().toString());
        precioField.setText(multaSeleccionada.getPrecio().toString());
        fechaField.setValue(multaSeleccionada.getFecha());
    }
}
