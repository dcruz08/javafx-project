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


public class AdminController {
    static Usuarios User = new Usuarios();
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    static final String nomFitxerAdmin = Usuarios.nomFitxerAdmin;
    static final String nomFitxerUsuari = Usuarios.nomFitxerUsuari;
    static List<Usuarios> llistaUsuarios = User.retornaUsuarios(nomFitxerAdmin);

    @FXML
    public MenuButton MenuBtnUsuarios;

    @FXML
    public TableView<Usuarios> TableMostrarUsuarios;

    @FXML
    public TextArea TextAreaUsuarios;

    @FXML
    public RadioButton RBtnAdministrador;

    @FXML
    public RadioButton RBtnTodos;

    @FXML
    public RadioButton RBtnUsuario;

    @FXML
    public ToggleGroup TipoDeUsuario;

    @FXML
    public Button BtnMostrar;

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

    @FXML
    private TextField ApellidoField;

    @FXML
    private TextField NombreField;

    @FXML
    private TextField UsuarioField;

    @FXML
    private TextField ContrasenaField;

    @FXML
    private Button BtnCrear;

    @FXML
    private RadioButton RBtnAdmin;

    @FXML
    private RadioButton RBtnUsuarioNormal;

    @FXML
    public void initialize() {
        RBtnAdmin.setSelected(true);
    }

    public String getUsuario() {
        RadioButton selectedRadioButton = (RadioButton) TipoDeUsuario.getSelectedToggle();
        String usuario = selectedRadioButton.getText();
        System.out.println(usuario);
        if (usuario.equals("Administrador")) {
            return Usuarios.nomFitxerAdmin;
        } else if (usuario.equals("Usuario")) {
            return Usuarios.nomFitxerUsuari;
        } else {
            return Usuarios.nomFitxerUsuari; // Otra lógica si no se selecciona ni Administrador ni Usuario
        }
    }

    @FXML
    private Usuarios CrearUsuario() {
        String usuario = UsuarioField.getText();
        String contrasena = ContrasenaField.getText();
        String nombre = NombreField.getText();
        String apellido = ApellidoField.getText();
        boolean admin = esAdmin();

        return new Usuarios(usuario, nombre, apellido, contrasena, admin);
    }

    @FXML
    private void altaUsuario(ActionEvent event) {
        Usuarios usuario = CrearUsuario();
        try {
            usuario.guardarUsuario(esAdmin()); // Guardar el usuario en el archivo correspondiente
            ms.missatgeInfo("Usuario guardado correctamente");
        } catch (Exception e) {
            ms.missatgeError("Error al guardar el usuario");
        }
    }

    public boolean esAdmin() {
        return RBtnAdmin.isSelected();
    }

    @FXML
    public void mostrarUsuarios() {
        ObservableList<Usuarios> usuarios = FXCollections.observableArrayList(User.retornaUsuarios(getUsuario()));

        ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("NomUsuari"));
        ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));

        ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("esAdmin"));

        TableMostrarUsuarios.setItems(usuarios);
    }

    @FXML
    public void handleMostrarUsuarios(ActionEvent event) {
        mostrarUsuarios();
    }
}
