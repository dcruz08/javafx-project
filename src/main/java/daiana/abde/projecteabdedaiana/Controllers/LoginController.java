package daiana.abde.projecteabdedaiana.Controllers;

import daiana.abde.projecteabdedaiana.Classes.Usuarios;
import daiana.abde.projecteabdedaiana.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class LoginController {
    private Usuarios usuarios;


    @FXML
    private Button BtnLogin;
    @FXML
    private TextField UserLogin;
    @FXML
    private PasswordField PassLogin;




    // Método para manejar el evento de inicio de sesión
    @FXML
    private void iniciarSesion() throws IOException {
        String nombreUsuario = UserLogin.getText();
        String contrasena = PassLogin.getText();

        boolean esAdmin = false; // Lógica de autenticación simulada

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


}
