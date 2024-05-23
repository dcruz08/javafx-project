package daiana.abde.projecte.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Producto.
 * Esta clase se encarga de gestionar los productos de la aplicación.
 * Permite guardar, listar y eliminar productos.
 * @autor Daiana Cruz
 * @version 1.0
 * @since 2021-05-17
 * @see Fitxers
 */
public class Producto implements Serializable {
    static final String dir = ".data";
    static final String nomFitxer = dir + "/productos.dat";
    static final Fitxers f = new Fitxers();

    //<editor-fold desc="PROPIETATS">
    private int id;
    private String codigoBarras;
    private String nombre;
    private String descripcion;
    private double precio;
    private LocalDate fechaCaducidad;

    //</editor-fold>

    //<editor-fold desc="Constructor">

    public Producto() {}

    public Producto(String codigoBarras, String nombre, String descripcion, double precio, LocalDate fechaCaducidad) {
        this.id = System.identityHashCode(this);
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaCaducidad = fechaCaducidad;
    }

    //</editor-fold>


    //<editor-fold desc="Getters ">

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    //</editor-fold>

    //<editor-fold desc="Methods">

    /**
     * Guardar producto.
     *
     * @throws Exception the exception
     */
    public void guardarProducto() throws Exception {
        try {
            if (!f.existeix(dir))
                f.creaDirectori(dir);
            f.escriuobjecteFitxer(this, nomFitxer, true);
        } catch (Exception e) {
            throw new Exception("Error al guardar el producto.");
        }
    }

    /**
     * Eliminar objeto de archivo.
     *
     * @param fitxer the fitxer
     * @param codigo the codigo
     * @throws IOException the io exception
     */
    public void eliminarObjetoDeArchivo(String fitxer, String codigo) throws IOException {
        List<Object> LObj = f.retornaFitxerObjecteEnLlista(fitxer);
        boolean primerRegistre = true;
        f.eliminarFitxerDirectori(fitxer);

        if (LObj.isEmpty()) {
            f.escriuobjecteFitxer(null, fitxer, false);
        }

        for (Object o : LObj) {
            Producto p = (Producto) o;
            if (!p.getCodigoBarras().equals(codigo)) {
                if (primerRegistre) {
                    f.escriuobjecteFitxer(o, fitxer, false);
                    primerRegistre = false;
                } else {
                    f.escriuobjecteFitxer(o, fitxer, true);
                }
            }
        }

    }

    /**
     * Eliminar producto.
     * Elimina el producto del archivo.
     * @throws Exception the exception
     */
    public void eliminarProducto() throws IOException {
        try {
            eliminarObjetoDeArchivo(nomFitxer, this.getCodigoBarras());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
    private static List<Producto> convertirAProductos(List <Object> lObj){
        List<Producto> productos = new ArrayList<>();
        for (Object obj: lObj){
            productos.add((Producto) obj);
        }
        return productos;
    }

    /**
     * Listar productos list.
     *
     * @return the list
     * @throws IOException            the io exception
     * @throws InterruptedException   the interrupted exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static List<Producto> listarProductos() throws IOException, InterruptedException, ClassNotFoundException {
        return convertirAProductos(f.retornaFitxerObjecteEnLlista(nomFitxer));
    }

    //</editor-fold>
}
