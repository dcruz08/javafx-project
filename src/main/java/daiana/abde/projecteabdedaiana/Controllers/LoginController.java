package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.Usuario;
import daiana.abde.projecteabdedaiana.HelloApplication;
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

import static daiana.abde.projecteabdedaiana.Classes.Usuario.nomFitxerAdmin;
import static daiana.abde.projecteabdedaiana.Classes.Usuario.nomFitxerUsuari;


public class LoginController {
    private Usuario usuario;
    static Usuario User = new Usuario();
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();


    @FXML
    private Button BtnLogin;
    @FXML
    private TextField UserLogin;
    @FXML
    private PasswordField PassLogin;




    // Método para manejar el evento de inicio de sesión
    @FXML
    private void iniciarSesion() throws IOException {

        boolean esAdmin = buscarUsuario(); // Lógica de autenticación simulada
        Stage newStage = new Stage(); // Inicializar el stage para la nueva ventana

        if (esAdmin) {
            // Cargar y mostrar la interfaz de administrador
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            newStage.setTitle("Interfaz de Administrador");
            newStage.setScene(scene);
            newStage.show();
            System.out.println("Usuario administrador");
        } else {
            // Cargar y mostrar la interfaz de usuario normal

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("usuario-normal.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            newStage.setTitle("Interfaz de Usario");
            newStage.setScene(scene);
            newStage.show();
            System.out.println("Usuario normal");

        }

        // Cerrar la ventana de inicio de sesión

    }

    private String existeixUsuario(String nom, String contrasena) {
        // Buscar en el archivo de administradores
        List<Usuario> admins = User.retornaUsuarios(nomFitxerAdmin);
        for (Usuario admin : admins) {
            if (admin.getNomUsuari().equals(nom) && admin.getContrasena().equals(contrasena)) {
                return "UsuarioAdministrador"; // Si se encuentra como administrador
            }
        }

        // Buscar en el archivo de usuarios normales
        List<Usuario> usuarios = User.retornaUsuarios(nomFitxerUsuari);
        for (Usuario usuario : usuarios) {
            if (usuario.getNomUsuari().equals(nom) && usuario.getContrasena().equals(contrasena)) {
                return "UsuarioNormal"; // Si se encuentra como usuario normal
            }
        }

        // Si no se encuentra en ninguno de los archivos, devolver un mensaje de error
        return "Usuario no encontrado";
    }

    private Boolean buscarUsuario() {
        String nombreUsuario = UserLogin.getText();
        String contrasena = PassLogin.getText();

        // Verificar si es administrador o usuario normal
        String resultado = existeixUsuario(nombreUsuario, contrasena);

        // Manejar el resultado según el tipo de dato devuelto
        if (resultado.equals("UsuarioAdministrador")) {
            return true;
        } else if (resultado.equals("UsuarioNormal")) {
            return false;
        } else {
            // Si el resultado no es un booleano, mostrar el mensaje de error
            ms.missatgeError(resultado);
            return null; // Retornar null para indicar que no se pudo determinar el estado del usuario
        }
    }





}
