module daiana.abde.projecteabdedaiana {
    requires javafx.controls;
    requires javafx.fxml;


    opens daiana.abde.projecteabdedaiana to javafx.fxml;
    exports daiana.abde.projecteabdedaiana;
    opens daiana.abde.projecteabdedaiana.Controllers to javafx.fxml;
    exports daiana.abde.projecteabdedaiana.Controllers;
}