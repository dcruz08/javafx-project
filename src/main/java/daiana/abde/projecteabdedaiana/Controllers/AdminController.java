package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    public TextArea TextAreaUsuarios;
    public RadioButton RBtnAdministrador;
    public RadioButton RBtnTodos;
    public RadioButton RBtnUsuario;
    public ToggleGroup TipoDeUsuario;
    public Button BtnMostrar;
    @FXML
    private  TextField UsuarioField;
    @FXML
    private  TextField ContrasenaField;
    @FXML
    private Button BtnCrear;
    @FXML
    private  RadioButton RBtnAdmin;
    @FXML
    private RadioButton RBtnUsuarioNormal;

    public void initialize(){

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
        boolean admin = esAdmin();
        Usuarios user = new Usuarios(usuario, contrasena,admin);
        return user;
    }

    @FXML
    private void altaUsuario(ActionEvent event) {
        Usuarios usuario = CrearUsuario();
        String tipoUsuario = RBtnAdmin.isSelected() ? "administrador" : "usuario normal";

        try {
            usuario.guardarUsuario(tipoUsuario);
            System.err.println("Usuario guardado correctamente");
        } catch (Exception e) {
            System.err.println("Error al guardar el usuario");
        }
    }


    public  boolean esAdmin(){
        return RBtnAdmin.isSelected();
    }

    @FXML
    public void mostrarUsuarios() {

     List<Usuarios> persones = User.retornaUsuarios(getUsuario());;
        String text = "";

        int i = 0;

        for (Usuarios p : persones) {
            if (i % 5 == 0)
                text = text + "\n";

            text = text + p.getNomUsuari() + " " + p.getContrasena() + "\n";

            i++;
        }
        // Establecer el texto en el TextAreaUsuarios
        TextAreaUsuarios.setText(text);



    }


    public void mostrarUsuarioss(ActionEvent actionEvent) {
        mostrarUsuarios();

    }
}
