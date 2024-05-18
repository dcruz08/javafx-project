package daiana.abde.projecteabdedaiana.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Usuario.
 * Esta clase se encarga de gestionar los usuarios de la aplicación.
 * Permite guardar, listar y eliminar usuarios.
 * @autor Abderrahim Ouabou
 * @version 1.0
 * @see Fitxers
 */
public class Usuario implements Serializable {
    static final private String nomDir=".data";
    static final Fitxers f = new Fitxers();

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
        return tipoUsuario.getNomArchivo();
    }
    
    //</editor-fold>

    /**
     * Guardar usuario.
     * Verifica si el directorio existe, si no lo crea.
     * Guarda un usuario en un archivo.
     * @throws IOException the io exception
     */
//<editor-fold desc="Mètodes">
    public void guardarUsuario() throws IOException {
        if (!f.existeix(nomDir))
            f.creaDirectori(nomDir);
        f.escriuobjecteFitxer(this, getNomFitxer(), true);
    }


    /**
     * Convertir a usuarios list.
     *
     * @param contingut una lista de objetos a convertir a la clase Usuario.
     * @return una lista de usuarios.
     */
    public static List<Usuario> convertirAUsuarios(List<Object> contingut){
        List<Usuario> lUsuario=new ArrayList<>();
        for (Object o: contingut){
            lUsuario.add((Usuario) o);
        }
        return lUsuario;

    }

    /**
     * Retorna usuario list.
     * Retorna una llista de usuaris.
     * @param nombreArchivo el nombre del archivo que contiene los objetos.
     * @return la lista de usuarios.
     */
    public static List<Usuario> retornaUsuario(String nombreArchivo) {
        return convertirAUsuarios(f.retornaFitxerObjecteEnLlista(nombreArchivo));
    }
    //</editor-fold>

}
