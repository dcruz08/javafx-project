package daiana.abde.projecteabdedaiana.Controllers;

import abderrahim.ouabou.Fitxers;
import daiana.abde.projecteabdedaiana.Classes.TipoUsuario;
import daiana.abde.projecteabdedaiana.Classes.Usuario;
import daiana.abde.projecteabdedaiana.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vicent.Bellver.MissatgesJavaSwing;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();
    @FXML
    private TextField UserLogin;
    @FXML
    private PasswordField PassLogin;
    static final Fitxers f = new Fitxers();
    static String archivoAdmins = TipoUsuario.ADMINISTRADOR.getNomArchivo();
    static String archivoUsuarios = TipoUsuario.USUARIO_NORMAL.getNomArchivo();
    @FXML
    private void iniciarSesion() throws IOException {
        String nombreUsuario = UserLogin.getText();
        String contrasena = PassLogin.getText();

        Stage stage = (Stage) UserLogin.getScene().getWindow();

        Usuario usuari = existeixUsuario(nombreUsuario);
        if (usuari == null) {
            ms.missatgeError("El usuario no existe.");
            return;
        }

        boolean esValido = comprobarUsuarioCredenciales(usuari, contrasena);
        if (!esValido) {
            ms.missatgeError("La contraseña es incorrecta.");
            return;
        }

        if (usuari.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            cambiarVista("admin.fxml", "Ventana de Administrador");
        } else {
            cambiarVista("usuario-normal.fxml", "Ventana de Usuario Normal");
        }

        stage.close();
    }

    private void cambiarVista(String resource, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle(titulo);
        s.setScene(scene);
        s.show();
    }

    private Usuario existeixUsuario(String nom) {
        List<Usuario> admins = Usuario.retornaUsuario(archivoAdmins);
        for (Usuario admin : admins) {
            if (admin.getNomUsuari().equals(nom)) {
                return admin;
            }
        }

        List<Usuario> usuaris = Usuario.retornaUsuario(archivoUsuarios);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!f.existeix(archivoAdmins) || !f.existeix(".data")){
            try {
                cambiarVista("admin.fxml", "Ventana de Administrador");
                ms.missatgeInfo("Debe crear un usuario administrador.");
            } catch (IOException e) {
                ms.missatgeError("Error al cargar la vista de administrador.");
            }
        }
    }
}
