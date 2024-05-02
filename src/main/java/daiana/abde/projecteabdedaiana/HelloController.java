package daiana.abde.projecteabdedaiana;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class HelloController {
    @FXML
    private Menu welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");


    }


}