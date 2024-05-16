package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.TipoUsuario;
import daiana.abde.projecteabdedaiana.Classes.Usuario;
import daiana.abde.projecteabdedaiana.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vicent.Bellver.MissatgesJavaSwing;

import java.io.IOException;
import java.util.List;


public class LoginController {
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    @FXML
    private TextField UserLogin;
    @FXML
    private PasswordField PassLogin;
    @FXML
    private void iniciarSesion() throws IOException {
        String nombreUsuario = UserLogin.getText();
        String contrasena = PassLogin.getText();

        Usuario usuari = existeixUsuario(nombreUsuario);
        if (usuari == null) {
            ms.missatgeError("El usuario no existe.");
            return;
        }

        boolean res = comprobarUsuarioCredenciales(usuari, contrasena);
        if (!res) {
            ms.missatgeError("La contraseña es incorrecta.");
            return;
        }

        Stage newStage = new Stage();
        String titulo = "", resource = "";

        if (usuari.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            titulo = "Ventana de Administrador";
            resource = "admin.fxml";
        } else {
            resource = "usuario-normal.fxml";
            titulo = "Ventana de Usario Normal";
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        newStage.setTitle(titulo);
        newStage.setScene(scene);
        newStage.show();
    }

    private Usuario existeixUsuario(String nom) {
        List<Usuario> admins = Usuario.retornaUsuario(TipoUsuario.ADMINISTRADOR.getNomArchivo());
        for (Usuario admin : admins) {
            if (admin.getNomUsuari().equals(nom)) {
                return admin;
            }
        }

        List<Usuario> usuaris = Usuario.retornaUsuario(TipoUsuario.USUARIO_NORMAL.getNomArchivo());
        for (Usuario usuario : usuaris) {
            if (usuario.getNomUsuari().equals(nom)) {
                return usuario;
            }
        }
        return null;
    }

    private Boolean comprobarUsuarioCredenciales(Usuario u, String contrasena) {
        if (u.getContrasena().equals(contrasena)) {
            return true;
        }
        return false;
    }
}
