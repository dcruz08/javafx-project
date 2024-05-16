package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.TipoUsuario;
import daiana.abde.projecteabdedaiana.Classes.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vicent.Bellver.MissatgesJavaSwing;

import java.util.List;
import java.util.Locale;

public class AdminController {
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();
    static final String nomFitxerAdmin = TipoUsuario.ADMINISTRADOR.getNomArchivo();
    static final String nomFitxerUsuari = TipoUsuario.USUARIO_NORMAL.getNomArchivo();
    @FXML
    public TableView<Usuario> tbUsuarios;
    @FXML
    public TableColumn<Usuario, TipoUsuario> ColumnaTipoUsuario;
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
    // private final String[] tipoUsuarioChoice = {"Administrador", "Normal"};

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


    public String tipoUsuarioSeleccionado() {
        String usuario = cboTipoUsuari2.getValue();
        if (usuario.equals("Administrador")) {
            return nomFitxerAdmin;
        }

        return nomFitxerUsuari;
    }
    private Usuario crearUsuario() {
        String usuario = UsuarioField.getText();
        String contrasena = ContrasenaField.getText();
        String nombre = NombreField.getText();
        String apellido = ApellidoField.getText();
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(cboTipoUsuari.getValue().toUpperCase());

        if (usuario.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || tipoUsuario == null) {
            ms.missatgeError("Rellene todos los campos.");
            return null;
        }

        return new Usuario(usuario, nombre, apellido, contrasena, tipoUsuario);
    }

    public void guardarUsuario() {
        Usuario usuario = crearUsuario();

        if(usuario == null) return;

        try {
            usuario.guardarUsuario();
            ms.missatgeInfo("El usuario se ha guardado correctamente.");
        } catch (Exception e) {
            ms.missatgeError("Error al guardar el usuario.");
        }
    }

    public boolean esAdmin() {
        if (cboTipoUsuari.getValue().equals("Administrador")) {
            return true;

        }
        return false;
    }

    public void filtrarPorTipoUsuario() {
        try {
            rellenarTabla(Usuario.retornaUsuario(tipoUsuarioSeleccionado()));
        } catch (Exception e) {
            ms.missatgeError("Seleccione un usuario");
        }
    }

    public void listarUsuarios(){
        List<Usuario> listaAdmins = Usuario.retornaUsuario(nomFitxerAdmin);
        List<Usuario> listaUsuariosNormales = Usuario.retornaUsuario(nomFitxerUsuari);
        listaAdmins.addAll(listaUsuariosNormales);
        rellenarTabla(listaAdmins);
    }

    public void rellenarTabla(List<Usuario> usuarios) {
        ObservableList<Usuario> Usuario = FXCollections.observableArrayList(usuarios);

        ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("nomUsuari"));
        ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        tbUsuarios.setItems(Usuario);
    }

}
