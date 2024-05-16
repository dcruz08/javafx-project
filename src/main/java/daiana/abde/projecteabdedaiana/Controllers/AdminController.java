package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.Usuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vicent.Bellver.MissatgesJavaSwing;

import java.util.List;

/**
 * La clase AdminController.
 * Controlador para la gestión de funciones administrativas relacionadas con la gestión de usuarios.
 */
public class AdminController {
    /**
     * El Usuario.
     * Una instancia de la clase Usuarios.
     */
    static Usuarios User = new Usuarios();
    /**
     * Los mensajes.
     * Una instancia de MissatgesJavaSwing para mostrar mensajes.
     */
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    /**
     * El nombre del archivo de admin.
     * El nombre del archivo para almacenar datos de usuarios administradores.
     */
    static final String nomFitxerAdmin = Usuarios.nomFitxerAdmin;
    /**
     * El nombre del archivo de usuario.
     * El nombre del archivo para almacenar datos de usuarios normales.
     */
    static final String nomFitxerUsuari = Usuarios.nomFitxerUsuari;
    /**
     * La lista de usuarios.
     * Una lista de instancias de Usuarios.
     */
    static List<Usuarios> llistaUsuarios = User.retornaUsuarios(nomFitxerAdmin);

    /**
     * La tabla para mostrar usuarios.
     * TableView para mostrar usuarios.
     */
    @FXML
    public TableView<Usuarios> TableMostrarUsuarios;

    /**
     * El botón para mostrar usuarios.
     * Botón para mostrar usuarios.
     */
    @FXML
    public Button BtnMostrar;

    /**
     * La columna tipo de usuario.
     * TableColumn para mostrar los tipos de usuario.
     */
    @FXML
    public TableColumn<Usuarios, String> ColumnaTipoUsuario;

    @FXML
    public TableColumn<Usuarios, String> ColumnaApellido;

    @FXML
    public TableColumn<Usuarios, String> ColumnaNombre;

    @FXML
    public TableColumn<Usuarios, String> ColumnaContrasena;

    @FXML
    public TableColumn<Usuarios, String> ColumnaUsuario;

    /**
     * El cuadro de selección de tipo de usuario.
     * ComboBox para seleccionar tipos de usuario.
     */
    public ComboBox<String> tipoUsuarioChoiceBox;
    private final String[] tipoUsuarioChoice = {"Administrador", "Normal"};
    @FXML
    public ComboBox<String> tipoUsuarioChoiceBox2;

    /**
     * El campo de apellido.
     * TextField para ingresar el apellido del usuario.
     */
    @FXML
    private TextField ApellidoField;

    /**
     * El campo de nombre.
     * TextField para ingresar el nombre del usuario.
     */
    @FXML
    private TextField NombreField;

    /**
     * El campo de usuario.
     * TextField para ingresar el nombre de usuario.
     */
    @FXML
    private TextField UsuarioField;

    /**
     * El campo de contraseña.
     * TextField para ingresar la contraseña.
     */
    @FXML
    private TextField ContrasenaField;

    /**
     * El botón para crear.
     * Botón para crear un nuevo usuario.
     */
    @FXML
    private Button BtnCrear;


    /**
     * Inicializar.
     * Inicializa la clase del controlador y llena los cuadros de selección.
     */
    @FXML
    public void initialize() {
        tipoUsuarioChoiceBox.getItems().addAll(tipoUsuarioChoice);
        tipoUsuarioChoiceBox2.getItems().addAll(tipoUsuarioChoice);
        mostrarUsuariosInicio();
    }

    /**
     * Usuario seleccionado string.
     * Retorna el nombre del archivo basado en el tipo de usuario seleccionado en tipoUsuarioChoiceBox2.
     *
     * @return el nombre del archivo como string.
     */
    public String UsuarioSeleccionado() {
        String usuario = tipoUsuarioChoiceBox2.getValue();
        System.out.println(usuario);
        if (usuario.equals("Administrador")) {
            return Usuarios.nomFitxerAdmin;
        } else if (usuario.equals("Normal")) {
            return Usuarios.nomFitxerUsuari;
        } else {
            return Usuarios.nomFitxerUsuari;
        }
    }

    /**
     * Crear usuario.
     * Crea un nuevo usuario basado en los campos de entrada.
     *
     * @return la nueva instancia de Usuarios o null si algún campo está vacío.
     */
    @FXML
    private Usuarios CrearUsuario() {
        // Obtener los valores de los campos
        String usuario = UsuarioField.getText().trim();
        String contrasena = ContrasenaField.getText().trim();
        String nombre = NombreField.getText().trim();
        String apellido = ApellidoField.getText().trim();
        String tipoUsuario = tipoUsuarioChoiceBox.getValue();

        // Verificar si algún campo está vacío
        if (usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || tipoUsuario == null) {
            ms.missatgeError("¡Todos los campos son obligatorios!");
            return null;
        }

        return new Usuarios(usuario, nombre, apellido, contrasena, tipoUsuario);
    }

    /**
     * Alta usuario.
     * Maneja la acción de crear y guardar un nuevo usuario.
     *
     * @param event el evento de acción activado por el usuario.
     */
    @FXML
    private void altaUsuario(ActionEvent event) {
        Usuarios usuario = CrearUsuario();

        if (usuario != null) {
            try {
                usuario.guardarUsuario(esAdmin());
                ms.missatgeInfo("Usuario guardado correctamente");
            } catch (Exception e) {
                ms.missatgeError("Error al guardar el usuario");
            }
        }
    }

    /**
     * Es admin boolean.
     * Determina si el tipo de usuario seleccionado es administrador.
     *
     * @return true si es administrador, false en caso contrario.
     */
    public boolean esAdmin() {
        if (tipoUsuarioChoiceBox.getValue().equals("Administrador")) {
            return true;
        }
        return false;
    }

    /**
     * Mostrar usuarios.
     * Muestra los usuarios en la tabla según el tipo de usuario seleccionado.
     */
    @FXML
    public void mostrarUsuarios() {
        try {
            ObservableList<Usuarios> usuarios = FXCollections.observableArrayList(User.retornaUsuarios(UsuarioSeleccionado()));
            configurarColumnasTabla();


            TableMostrarUsuarios.setItems(usuarios);
        } catch (Exception e) {
            ms.missatgeError("Seleccione un usuario");
        }
    }

    /**
     * Mostrar usuarios inicio.
     * Muestrar todos los usuarios al inicio, combinando las listas de administradores y usuarios normales.
     */
    public void mostrarUsuariosInicio() {
        // Obtener la lista de usuarios del archivo correspondiente
        List<Usuarios> listaUsuariosAdmin = User.retornaUsuarios(Usuarios.nomFitxerAdmin);
        List<Usuarios> listaUsuariosUsuario = User.retornaUsuarios(Usuarios.nomFitxerUsuari);

        // Combinar las listas
        listaUsuariosAdmin.addAll(listaUsuariosUsuario);

        // Crear una ObservableList con la lista combinada
        ObservableList<Usuarios> usuarios = FXCollections.observableArrayList(listaUsuariosAdmin);

        // Configurar las celdas de la tabla
        configurarColumnasTabla();


        // Establecer los datos en la tabla
        TableMostrarUsuarios.setItems(usuarios);
    }


    /**
     * Configurar columnas de la tabla.
     * Configura las columnas de la tabla para mostrar los usuarios.
     */
    private void configurarColumnasTabla() {
        ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("NomUsuari"));
        ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("TipoUsuario"));
    }

}
