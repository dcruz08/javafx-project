package daiana.abde.projecteabdedaiana.Controllers;

import com.google.zxing.BarcodeFormat;
import javafx.scene.control.*;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCAWriter;
import daiana.abde.projecteabdedaiana.Classes.Producto;
import daiana.abde.projecteabdedaiana.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import vicent.Bellver.MissatgesJavaSwing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Usuario normal controlador.
 * Esta clase se encarga de gestionar la interfaz de usuario normal de la aplicación.
 * Permite crear, guardar, listar y eliminar productos.
 * @author Daiana Cruz
 * @version 1.0
 */
public class UsuarioNormalController {
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

    /**
     * Eliminar producto.
     * Este método se ejecuta cuando el usuario hace clic en el botón de eliminar producto.
     * Elimina el producto seleccionado de la tabla y del archivo.
     * Si el usuario confirma la acción, se elimina el producto.
     * @see Producto#eliminarProducto()
     */
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

    /**
     * Rellenar tabla productos.
     * Este método se encarga de rellenar la tabla de productos con los datos de los productos almacenados en el archivo.
     * @throws IOException si hay un error al cargar los datos del archivo.
     */
    public void rellenarTablaProductos() throws IOException, InterruptedException, ClassNotFoundException {
        tbProductos.setEditable(false);
        List<Producto> productos = Producto.listarProductos();

        ObservableList<Producto> lProductos = FXCollections.observableList(productos);
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        fechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));

        tbProductos.setItems(lProductos);
    }

    /**
     * Crear producto.
     * Este método se ejecuta cuando el usuario hace clic en el botón de crear producto.
     * Crea un nuevo producto con los datos introducidos en el formulario y lo guarda en el archivo .dat.
     */
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

    /**
     * Generar codigo barras.
     * Este método genera una imagen del código de barras con el número introducido en el campo de texto.
     * @param codigoBarras el número del código de barras a generar.
     */
    public void generarCodigoBarras(String codigoBarras) {
        UPCAWriter upcAWriter = new UPCAWriter();
        BitMatrix bitmap = upcAWriter.encode(codigoBarras, BarcodeFormat.UPC_A, 80, 90);
        Image imgCodigo = convertirAImage(MatrixToImageWriter.toBufferedImage(bitmap));
        imgCodigoBarras.setImage(imgCodigo);
    }

    /**
     * Validar codigo barras.
     * Este método se ejecuta cuando el usuario hace clic en el botón de validar código de barras.
     * Comprueba si el código de barras introducido tiene 11 dígitos.
     * Si es correcto, genera la imagen del código de barras y habilita el formulario de producto.
     */
    public void validarCodigoBarras() {
        String codigo = numCodigoBarras.getText();
        if (codigo.length() != 11) {
            ms.missatgeError("Codigo de barras incorrecto. Debe ser de 11 dígitos.");
        } else {
            generarCodigoBarras(codigo);
            dadesProductoPane.setDisable(false);
        }
    }

    /**
     * Resetear formulario producto.
     * Este método resetea los campos del formulario de producto y deshabilita el formulario de los datos del producto.
     */
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

    /**
     * Initialize.
     * Este método se ejecuta al cargar la vista de usuario normal.
     * Rellena la tabla de productos con los datos almacenados en el archivo.
     * @throws IOException si hay un error al cargar los datos del archivo.
     */
    public void initialize() throws IOException, InterruptedException, ClassNotFoundException {
        dadesProductoPane.setDisable(true);
        rellenarTablaProductos();
    }

}
