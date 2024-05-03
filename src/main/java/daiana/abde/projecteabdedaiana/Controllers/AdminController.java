package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.Usuarios;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class AdminController {

    static Usuarios User = new Usuarios();

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


    public void initialize(){

        RBtnAdmin.setSelected(true);

    }

    @FXML
    private  Usuarios CrearUsuario()  {
        String usuario = UsuarioField.getText();
        String contrasena = ContrasenaField.getText();
        boolean Admin;
        Admin=esAdmin();
        Usuarios User=new Usuarios(usuario,contrasena,Admin);
        System.out.println("Usuario: " + usuario+"contrasena: " + contrasena+"Admin: " + Admin);
        return User;
    }


    private boolean esAdmin(){
        return RBtnAdmin.isSelected();
    }

    private static void altaUsuario() {
    Usuarios usuario = new CrearUsuario();

        try {
            User.guardarUsuari();
            System.err.println("Llibre guardat correctament");
        } catch (Exception e) {
            System.err.println("Error al guardar el llibre");
        }

                            // si guardem, actualitzem la llista
    }


}
