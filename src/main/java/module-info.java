module daiana.abde.projecte {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires Fitxers;
    requires missatges.i.alertes;



    opens daiana.abde.projecte to javafx.fxml;
    exports daiana.abde.projecte;

    opens daiana.abde.projecte.Controllers to javafx.fxml;
    exports daiana.abde.projecte.Controllers;

    exports daiana.abde.projecte.Classes;
    opens daiana.abde.projecte.Classes to javafx.fxml;
}