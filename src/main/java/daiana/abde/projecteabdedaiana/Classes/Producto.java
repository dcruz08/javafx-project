package daiana.abde.projecteabdedaiana.Classes;


import pkgFitxers.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Serializable {
    static final String dir = ".dat";
    static final String nomFitxer = dir + "/productos.dat";
    static final Fitxers f = new Fitxers();

    //<editor-fold desc="PROPIETATS">
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String fechaCaducidad;

    //</editor-fold>

    //<editor-fold desc="Constructor">

    public Producto() {}

    public Producto(String nombre, String descripcion, double precio, String fechaCaducidad) {
        this.id = System.identityHashCode(this);
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

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    //</editor-fold>

    //<editor-fold desc="Methods">

    public void guardarProducto() {
        try {
            if (!f.existeix(dir))
                f.creaDirectori(dir);
            f.escriuObjecteFitxer(this, nomFitxer, true);
        } catch (Exception e) {
            System.out.println("No s'ha pogut guardar el producte");
        }
    }
    private static List<Producto> convertirAProductos(List <Object> lObj){
        List<Producto> productos = new ArrayList<>();
        for (Object obj: lObj){
            productos.add((Producto) obj);
        }
        return productos;
    }
    public static List<Producto> listarProductos() throws IOException, InterruptedException, ClassNotFoundException {
        return convertirAProductos(f.retornaFitxerObjecteEnLlista(nomFitxer));
    }

    //</editor-fold>
}
