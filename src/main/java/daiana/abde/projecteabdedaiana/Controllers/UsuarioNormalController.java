package daiana.abde.projecteabdedaiana.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCAWriter;
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
import vicent.Bellver.MissatgesJavaSwing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
    private TableColumn<Producto, LocalDate> fechaCaducidad;
    static final MissatgesJavaSwing ms = new MissatgesJavaSwing();

    public void eliminarProducto() {
        Producto productoSeleccionado = tbProductos.getSelectionModel().getSelectedItem();

        if(ms.pregunta("¿Seguro que quieres borrar este producto?") == 0) {
            try {
                productoSeleccionado.eliminarProducto();
                tbProductos.getItems().remove(productoSeleccionado);
                ms.missatgeInfo("Se ha borrado el producto.");
            } catch (Exception e) {
               ms.missatgeError("Error al borrar el producto. Intente de nuevo.");
            }
        }
    }

    public void rellenarTablaProductos() {
        tbProductos.setEditable(false);
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
            resetearFormularioProducto();
            rellenarTablaProductos();
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
        BitMatrix bitmap = upcAWriter.encode(codigoBarras, BarcodeFormat.UPC_A, 80, 100);
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
        Image img = null;
        try {
            img = new Image(HelloApplication.class.getResource("images/logo.png").toURI().toURL().toExternalForm());
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen del logo.");
        }

        imgCodigoBarras.setImage(img);
        numCodigoBarras.setText("");

        nomProducto.setText("");
        descProducto.setText("");
        precioProducto.setText("");
        caducidadProducto.setValue(null);
        dadesProductoPane.setDisable(true);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dadesProductoPane.setDisable(true);
        rellenarTablaProductos();
    }

}
