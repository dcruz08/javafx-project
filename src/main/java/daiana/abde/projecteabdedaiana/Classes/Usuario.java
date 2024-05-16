package daiana.abde.projecteabdedaiana.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Serializable {
    static final private String nomDir=".dat";
    static final Fitxers f=new Fitxers();

    //<editor-fold desc="Variables">
    private int id;
    private String nomUsuari;
    private String contrasena;
    private String nombre;
    private String apellido;
    private TipoUsuario tipoUsuario;
    //</editor-fold>

    //<editor-fold desc="Construct">
    public Usuario() {
    }

    public Usuario(String nomUsuari,String nombre, String apellido ,String contrasena, TipoUsuario tipoUsuario) {
        this.id=System.identityHashCode(this);
        this.nomUsuari = nomUsuari;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }
    
    //</editor-fold>

    //<editor-fold desc="Getters ">
    public int getId() {
        return id;
    }
    
    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNomFitxer(){
        return nomDir + tipoUsuario.getNomArchivo();
    }
    
    //</editor-fold>

    //<editor-fold desc="Mètodes">
    public void guardarUsuario() throws IOException {
        if (!f.existeix(nomDir))
            f.creaDirectori(nomDir);
        String nombreArchivo = getNomFitxer();
        f.escriuobjecteFitxer(this, nombreArchivo, true);
    }

    public static List<Usuario> converteixAUsuario(List<Object> contingut){
        List<Usuario> lUsuario=new ArrayList<>();
        for (Object o: contingut){
            lUsuario.add((Usuario) o);
        }
        return lUsuario;

    }
    public static List<Usuario> retornaUsuario(String nombreArchivo) {
        return converteixAUsuario(f.retornaFitxerObjecteEnLlista(nombreArchivo));
    }
    //</editor-fold>

}
