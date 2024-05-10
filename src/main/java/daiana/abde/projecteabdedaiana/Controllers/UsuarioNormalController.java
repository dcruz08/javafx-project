package daiana.abde.projecteabdedaiana.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;



import com.google.zxing.BarcodeFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vicent.Bellver.MissatgesJavaSwing;

import javax.swing.text.TableView;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UsuarioNormalController {

    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

//    @FXML
//    private AnchorPane numeroBarras;
//    @FXML
//    private ImageView ImagenCodigoBarras;
   @FXML
  private TextField TextCodigoBarras;
    //    @FXML
//    private TextField nomProducto;
//    @FXML
//    private TextField precioProducto;
//    @FXML
//    private TextArea descProducto;
//    @FXML
//    private DatePicker fechaProducto;
//    @FXML
//    private Button btnGuardar;
//    @FXML
//    private Button BtnEscanea;
//    @FXML
//    private TableView tbProductos;




    public void NumeroBarras() {
        try {
            String NumeroBarcode = TextCodigoBarras.getText();
            System.out.println(NumeroBarcode);


        } catch (NumberFormatException e) {
            ms.missatgeError("Por favor ingresa un número válido.");

        }
    }

}