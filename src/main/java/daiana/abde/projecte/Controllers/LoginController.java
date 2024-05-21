package daiana.abde.projecte.Controllers;

import abderrahim.ouabou.Fitxers;
import daiana.abde.projecte.Classes.TipoUsuario;
import daiana.abde.projecte.Classes.Usuario;
import daiana.abde.projecte.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vicent.Bellver.MissatgesJavaSwing;

import java.io.IOException;
import java.util.List;

/**
 * Controlador de Login.
 * Esta clase se encarga de gestionar la interfaz de inicio de sesión de la aplicación.
 * Permite la autenticación de usuarios y el cambio de vistas según el tipo de usuario.
 * @author Daiana Cruz
 * @author Abderrahim Ouabou
 * @version 1.0
 */
public class LoginController {
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();
    @FXML
    public Button btnLogin;
    @FXML
    public Button btnNoCredentials;
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

        Usuario usuari = existeUsuario(nombreUsuario);
        if (usuari == null) {
            ms.missatgeError("El usuario no existe.");
            return;
        }

        boolean esValido = comprobarCredenciales(usuari, contrasena);
        if (!esValido) {
            ms.missatgeError("La contraseña es incorrecta.");
            return;
        }

        if (usuari.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            cambiarVista("admin.fxml", "Ventana de Administrador | Grup 10 (Daiana i Abdel)");
        } else {
            cambiarVista("usuario-normal.fxml", "Ventana de Usuario | Grup 10 (Daiana i Abdel)");
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
    public void cambiarVista(String resource, String titulo) throws IOException {
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
    public Usuario existeUsuario(String nom) {
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
     * Compara las credenciales recuperadas del campo de texto y las credenciales almacenadas del usuario.
     *
     * @param usuario  objeto de la clase usuario.
     * @param contrasena la contraseña a verificar.
     * @return true si las credenciales son correctas; de lo contrario, false.
     */
    public Boolean comprobarCredenciales(Usuario usuario, String contrasena) {
        return usuario.getContrasena().equals(contrasena);
    }

    /**
     * Método que carga la vista de administrador cuando no existe ningún usuario administrador.
     */
    public void accederSinCredenciales(){
        try {
            cambiarVista("admin.fxml", "Administrador | Grup 10 (Daiana i Abdel)");
            Stage stage = (Stage) UserLogin.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            ms.missatgeError("Error al cargar la vista de administrador.");
        }
    }
    /**
     * Inicializa el controlador.
     * Este método se ejecuta automáticamente al cargar la vista. Comprueba si hay un usuario administrador y, si no
     * existe, muestra un mensaje de advertencia.
     */
    public void initialize() {
        if(!f.existeix(archivoAdmins) || !f.existeix(".data")){
            ms.missatgeInfo("Debe crear un usuario administrador.");
            btnNoCredentials.setVisible(true);
        } else {
            btnNoCredentials.setVisible(false);
        }
    }
}
