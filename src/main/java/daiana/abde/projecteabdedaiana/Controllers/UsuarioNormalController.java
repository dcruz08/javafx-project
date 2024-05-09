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
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import daiana.abde.projecteabdedaiana.Classes.Producto;
import daiana.abde.projecteabdedaiana.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import pkgFitxers.Fitxers;
import vicent.Bellver.MissatgesJavaSwing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsuarioNormalController implements Initializable {
    @FXML
    private ImageView imgCodigoBarras;
    @FXML
    private TextField nomProducto;
    @FXML
    private TextField precioProducto;
    @FXML
    private TextArea descProducto;
    @FXML
    private DatePicker caducidadProducto;
    @FXML
    private Pane dadesProductoPane;
    @FXML
    private TextField numCodigoBarras;
    @FXML
    private TableView<Producto> tbProductos;
    @FXML
    private TableColumn<Producto, String> codigo;
    @FXML
    private TableColumn<Producto, String> nombre;
    @FXML
    private TableColumn<Producto, Double> precio;
    @FXML
    private TableColumn<Producto, String> descripcion;
    @FXML
    private TableColumn<Producto, Date> fechaCaducidad;
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    public void eliminarProducto() {
        Producto productoSeleccionado = tbProductos.getSelectionModel().getSelectedItem();

        if(ms.pregunta("¿Seguro que quieres borrar este producto?") == 0) {
            tbProductos.getItems().remove(productoSeleccionado);
            ms.missatgeInfo("Se ha borrado el producto.");
        }
    }


    public void rellenarTablaProductos() {
        tbProductos.setEditable(true);
        try {
            List<Producto> productos = Producto.listarProductos();
            System.out.println(productos);

            ObservableList<Producto> lProductos = FXCollections.observableList(productos);

            codigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
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

    public void crearProducto() {
        String codigo = numCodigoBarras.getText();
        String nom = nomProducto.getText();
        String desc = descProducto.getText();
        double precio = Double.parseDouble(precioProducto.getText());
        LocalDate fechaCaducidad = caducidadProducto.getValue();

        if (nom.isEmpty() || desc.isEmpty() || precio == 0.0 || fechaCaducidad == null) {
            ms.missatgeError("Los campos no pueden estar vacios.");
            return;
        }

        Producto p = new Producto(codigo,nom,desc,precio,fechaCaducidad);
        try {
            p.guardarProducto();
            ms.missatgeInfo("Se ha guardado el producto.");
        } catch (Exception e) {
            ms.missatgeError("No se podido guardar el producto.");
        }
    }

    private Image convertirAImage(BufferedImage bufferedImage) {
        WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        PixelWriter pw = writableImage.getPixelWriter();
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                pw.setArgb(x, y, bufferedImage.getRGB(x, y));
            }
        }
        return writableImage;
    }

    public void generarCodigoBarras() {
        String codigoBarras = numCodigoBarras.getText();
        UPCAWriter upcAWriter = new UPCAWriter();
        BitMatrix bitmap = upcAWriter.encode(codigoBarras, BarcodeFormat.UPC_A, 100, 100);
        Image imgCodigo = convertirAImage(MatrixToImageWriter.toBufferedImage(bitmap));
        imgCodigoBarras.setImage(imgCodigo);
    }

    public void validarCodigoBarras() {
        String codigo = numCodigoBarras.getText();
        if (codigo.length() != 11) {
            ms.missatgeError("Codigo de barras incorrecto. Debe ser de 11 dígitos.");
        } else {
            generarCodigoBarras();
            dadesProductoPane.setDisable(false);
        }
    }

    public void resetearFormularioProducto() {
        //HelloApplication.class.getResource("images/codigobarras.png")
        imgCodigoBarras.setImage(null);
        nomProducto.setText("");
        descProducto.setText("");
        precioProducto.setText("");
        caducidadProducto.setValue(null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dadesProductoPane.setDisable(true);
        rellenarTablaProductos();
    }

}
