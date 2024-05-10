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
    public TableView<Usuarios> TableMostrarUsuarios;


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

    public ComboBox<String> tipoUsuarioChoiceBox;
    private final String[] tipoUsuarioChoice = {"Administrador", "Normal"};
    @FXML
    public ComboBox<String> tipoUsuarioChoiceBox2;
    public Button BtnEliminarUsuario;

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
    private RadioButton RBtnUsuarioNormal;

    @FXML
    public void initialize() {
        tipoUsuarioChoiceBox.getItems().addAll(tipoUsuarioChoice);

        tipoUsuarioChoiceBox2.getItems().addAll(tipoUsuarioChoice);

        mostrarUsuariosInicio();


    }

    public String UsuarioSeleccionado() {
//        RadioButton selectedRadioButton = (RadioButton) TipoDeUsuario.getSelectedToggle();

        String usuario = tipoUsuarioChoiceBox2.getValue();
        System.out.println(usuario);
        if (usuario.equals("Administrador")) {
            return Usuarios.nomFitxerAdmin;
        } else if (usuario.equals("Normal")) {
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
        String tipoUsuario = tipoUsuarioChoiceBox.getValue();
//        boolean admin = esAdmin();

        return new Usuarios(usuario, nombre, apellido, contrasena, tipoUsuario);
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

        if (tipoUsuarioChoiceBox.getValue().equals("Administrador")) {
            return true;

        }
        return false;
    }

    @FXML
    public void mostrarUsuarios() {
        try {


            ObservableList<Usuarios> usuarios = FXCollections.observableArrayList(User.retornaUsuarios(UsuarioSeleccionado()));

            ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("NomUsuari"));
            ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
            ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
            ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("TipoUsuario"));

            TableMostrarUsuarios.setItems(usuarios);
        } catch (Exception e) {
            ms.missatgeError("Seleccione un usuario");
        }
    }

    public void mostrarUsuariosInicio(){

        // Obtener la lista de usuarios del archivo correspondiente
        List<Usuarios> listaUsuariosAdmin = User.retornaUsuarios(Usuarios.nomFitxerAdmin);
        List<Usuarios> listaUsuariosUsuario = User.retornaUsuarios(Usuarios.nomFitxerUsuari);

        // Combinar las listas
        listaUsuariosAdmin.addAll(listaUsuariosUsuario);

        // Crear una ObservableList con la lista combinada
        ObservableList<Usuarios> usuarios = FXCollections.observableArrayList(listaUsuariosAdmin);

        // Configurar las celdas de la tabla
        ColumnaUsuario.setCellValueFactory(new PropertyValueFactory<>("NomUsuari"));
        ColumnaContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        ColumnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        ColumnaApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        ColumnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("TipoUsuario"));

        // Establecer los datos en la tabla
        TableMostrarUsuarios.setItems(usuarios);
    }


}
