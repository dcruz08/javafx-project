package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.TipoUsuario;
import daiana.abde.projecteabdedaiana.Classes.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vicent.Bellver.MissatgesJavaSwing;

import java.util.List;

/**
 * Controlador de Administrador.
 * Esta clase se encarga de gestionar la interfaz administrativa de la aplicación.
 * Permite la creación, almacenamiento y listado de datos de usuario, así como la filtración de usuarios por su tipo.
 * @author Abderrahim Ouabou
 * @author Daiana Cruz
 * @version 1.0
 */
public class AdminController {

    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    static final String nomFitxerAdmin = TipoUsuario.ADMINISTRADOR.getNomArchivo();

    static final String nomFitxerUsuari = TipoUsuario.USUARIO_NORMAL.getNomArchivo();
    
    @FXML
    public TableView<Usuario> tbUsuarios;
    @FXML
    public TableColumn<Usuario, String> ColumnaTipoUsuario;
    @FXML
    public TableColumn<Usuario, String> ColumnaApellido;
    @FXML
    public TableColumn<Usuario, String> ColumnaNombre;
    @FXML
    public TableColumn<Usuario, String> ColumnaContrasena;
    @FXML
    public TableColumn<Usuario, String> ColumnaUsuario;
    @FXML
    public ComboBox<String> cboTipoUsuari;
    @FXML
    public ComboBox<String> cboTipoUsuari2;
    @FXML
    private TextField ApellidoField;
    @FXML
    private TextField NombreField;
    @FXML
    private TextField UsuarioField;
    @FXML
    private TextField ContrasenaField;

    /**
     * Initialize.
     * Este método se ejecuta al iniciar la aplicación y llena los ComboBox con los tipos de usuario disponibles.
     * También lista todos los usuarios.
     */
    @FXML
    public void initialize() {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            cboTipoUsuari.getItems().add(tipo.getValue());
            cboTipoUsuari2.getItems().add(tipo.getValue());
        }
        cboTipoUsuari.getSelectionModel().select(0);
        cboTipoUsuari2.getSelectionModel().select(0);
        listarUsuarios();
    }

    /**
     * Filtra por el tipo de usuario seleccionado.
     *
     * @return el nombre del archivo asociado con el tipo de usuario seleccionado.
     */
    public String obtenerTipoSeleccionado() {
        if (cboTipoUsuari2.getValue().equals("Administrador")) {
            return nomFitxerAdmin;
        }
        return nomFitxerUsuari;
    }
    /**
     * Crea un nuevo usuario.
     *
     * @return el nuevo usuario si todos los campos están correctamente llenados; de lo contrario, null.
     *
     * @since 1.0
     */
    private Usuario crearUsuario() {
        String usuario = UsuarioField.getText();
        String contrasena = ContrasenaField.getText();
        String nombre = NombreField.getText();
        String apellido = ApellidoField.getText();
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(cboTipoUsuari.getValue().toUpperCase().replace(" ", "_"));

        if (usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || tipoUsuario == null) {
            ms.missatgeError("Rellene todos los campos.");
            return null;
        }

        return new Usuario(usuario, nombre, apellido, contrasena, tipoUsuario);
    }

    /**
     * Guarda el nuevo usuario.
     * Este método crea un nuevo usuario e intenta guardarlo. Si tiene éxito, la lista de usuarios se actualiza.
     */
    public void guardarUsuario() {
        Usuario usuario = crearUsuario();

        if(usuario == null) return;

        try {
            usuario.guardarUsuario();
            ms.missatgeInfo("El usuario se ha guardado correctamente.");
            listarUsuarios();
        } catch (Exception e) {
            ms.missatgeError("Error al guardar el usuario.");
        }
    }

    /**
     * Filtra los usuarios por tipo seleccionado.
     * Este método actualiza la tabla de usuarios para mostrar solo los usuarios del tipo seleccionado.
     */
    public void filtrarPorTipoUsuario() {
        try {
            rellenarTabla(Usuario.retornaUsuario(obtenerTipoSeleccionado()));
        } catch (Exception e) {
            ms.missatgeError("Seleccione un usuario");
        }
    }


    /**
     * Lista todos los usuarios.
     * Este método recupera todos los usuarios (administradores y usuarios normales) y llena la tabla.
     */
    public void listarUsuarios(){
        List<Usuario> listaAdmins = Usuario.retornaUsuario(nomFitxerAdmin);
        List<Usuario> listaUsuariosNormales = Usuario.retornaUsuario(nomFitxerUsuari);
        System.out.println("listando usuarios");
        System.out.println(listaAdmins);
        System.out.println(listaUsuariosNormales);
        listaAdmins.addAll(listaUsuariosNormales);
        rellenarTabla(listaAdmins);
    }

    /**
     * Rellena la tabla de usuarios.
     * Este método se encarga de rellenar la tabla de usuarios con los datos de los usuarios almacenados en el archivo.
     *
     * @param usuarios la lista de usuarios a mostrar en la tabla.
     */
    public void rellenarTabla(List<Usuario> usuarios) {
        ObservableList<Usuario> listUsuarios = FXCollections.observableArrayList(usuarios);

        ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("nomUsuari"));
        ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ColumnaTipoUsuario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoUsuario().getValue()));

        tbUsuarios.setItems(listUsuarios);
    }
}
