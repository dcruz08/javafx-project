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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import daiana.abde.projecteabdedaiana.Classes.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import pkgFitxers.Fitxers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioNormalController implements Initializable {
    @FXML
    public ImageView codigoBarras;
    @FXML
    private TextField numCodigoBarras;
    @FXML
    private TableView<Producto> tbProductos;
    @FXML
    private TableColumn<Producto, String> nombre;
    @FXML
    private TableColumn<Producto, Double> precio;
    @FXML
    private TableColumn<Producto, String> descripcion;
    @FXML
    private TableColumn<Producto, Date> fechaCaducidad;

    public void eliminarProducto() {

    }

    public void generarCodigoBarras() {
        try {
            String codigoBarras = numCodigoBarras.getText();
            String path = "src/main/resources/codigosBarras/" + numCodigoBarras.getText() + ".jpg";
            Code39Writer cbWriter = new Code39Writer();
            BitMatrix bitmap = cbWriter.encode(codigoBarras, BarcodeFormat.CODE_39, 150, 100);
            MatrixToImageWriter.writeToPath(bitmap, "jpg", Paths.get(path));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbProductos.setEditable(true);
        try {
            List<Producto> productos = Producto.listarProductos();
            System.out.println(productos);

            ObservableList<Producto> lProductos = FXCollections.observableList(productos);

            nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            fechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));

            tbProductos.setItems(lProductos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
