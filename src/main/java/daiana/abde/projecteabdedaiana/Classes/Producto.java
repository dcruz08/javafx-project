package daiana.abde.projecteabdedaiana.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Producto implements Serializable {

    static final String dir = ".dat";
    static final String nomFitxer = dir + "/productos.dat";
    static final Fitxers f = new Fitxers();

    //<editor-fold desc="PROPIETATS">
    private int id;


    private String NombreProducto;
    private String Descripcion;
    private double Precio;
     private String FechaCaducidad;
     private int CodBarra;
    //</editor-fold>

    //<editor-fold desc="Constructor">

    public Producto() {

    }


    public Producto(String nombreProducto, String descripcion, double precio, String fechaCaducidad, int codBarra) {
        this.id = System.identityHashCode(this);
        NombreProducto = nombreProducto;
        Descripcion = descripcion;
        Precio = precio;
        FechaCaducidad = fechaCaducidad;
        CodBarra = codBarra;
    }

    //</editor-fold>


    //<editor-fold desc="Getters ">

    public int getId() {
        return id;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public String getFechaCaducidad() {
        return FechaCaducidad;
    }

    public int getCodBarra() {return CodBarra;}

    public String getNomFitxer(){
        return nomFitxer;
    }

    //</editor-fold>

    //<editor-fold desc="Methods">

    public void guardaProcduto() {
        try {
            if (!f.existeix(dir))
                f.creaDirectori(dir);

            f.escriuobjecteFitxer(this,nomFitxer, true);
        } catch (Exception e) {
            System.out.println("No s'ha pogut guardar el producte");
        }

    }
    private static List<Producto> converteixAProducte(List <Object> lObj){
        List<Producto> productos=new ArrayList<>();
        for (Object o: lObj){
            productos.add((Producto) o);
        }
        return productos;

    }
    public List<Producto> retornaProductos() {
        return converteixAProducte(f.retornaFitxerObjecteEnLlista(getNomFitxer()));
    }




    //</editor-fold>
}
