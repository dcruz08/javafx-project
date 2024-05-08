module daiana.abde.projecteabdedaiana {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires Fitxers;
    requires missatges.i.alertes;



    opens daiana.abde.projecteabdedaiana to javafx.fxml;
    exports daiana.abde.projecteabdedaiana;

    opens daiana.abde.projecteabdedaiana.Controllers to javafx.fxml;
    exports daiana.abde.projecteabdedaiana.Controllers;

    exports daiana.abde.projecteabdedaiana.Classes;
    opens daiana.abde.projecteabdedaiana.Classes to javafx.fxml;
}