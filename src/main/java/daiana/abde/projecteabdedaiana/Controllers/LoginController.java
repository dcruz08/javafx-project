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

/**
 * Controlador de Inicio de Sesión.
 * Esta clase se encarga de gestionar la interfaz de inicio de sesión de la aplicación.
 * Permite la autenticación de usuarios y el cambio de vistas según el tipo de usuario.
 * @author Daiana Cruz Poma Daiana
 * @author Abderrahim Ouabou
 * @version 1.1
 */
public class LoginController implements Initializable {
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();
    @FXML
    private TextField UserLogin;
    @FXML
    private PasswordField PassLogin;
    static final Fitxers f = new Fitxers();
    static String archivoAdmins = TipoUsuario.ADMINISTRADOR.getNomArchivo();
    static String archivoUsuarios = TipoUsuario.USUARIO_NORMAL.getNomArchivo();

    /**
     * Inicia sesión.
     * Este método se ejecuta cuando el usuario hace clic en el botón de inicio de sesión. Comprueba las credenciales
     * del usuario y cambia la vista según el tipo de usuario.
     *
     * @throws IOException si hay un error al cargar las vistas.
     */
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

    /**
     * Cambia la vista de la aplicación.
     *
     * @param resource el recurso FXML a cargar.
     * @param titulo   el título de la nueva ventana.
     * @throws IOException si hay un error al cargar el recurso.
     */
    private void cambiarVista(String resource, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();
        s.setTitle(titulo);
        s.setScene(scene);
        s.show();
    }

    /**
     * Comprueba si existe un usuario con el nombre dado.
     *
     * @param nom el nombre del usuario.
     * @return el usuario si existe; de lo contrario, null.
     */
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

    /**
     * Comprueba las credenciales del usuario.
     *
     * @param u          el usuario a comprobar.
     * @param contrasena la contraseña a verificar.
     * @return true si las credenciales son correctas; de lo contrario, false.
     */
    private Boolean comprobarUsuarioCredenciales(Usuario u, String contrasena) {
        return u.getContrasena().equals(contrasena);
    }

    /**
     * Inicializa el controlador.
     * Este método se llama automáticamente .
     *
     * @param location  la ubicación utilizada para resolver rutas relativas del archivo raíz o null si no se conoce la ubicación.
     * @param resources .
     */
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
