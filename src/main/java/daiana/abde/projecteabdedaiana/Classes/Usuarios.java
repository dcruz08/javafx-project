package daiana.abde.projecteabdedaiana.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuarios implements Serializable {

    static final private String nomDir=".dat";
    public static final String nomFitxerUsuari=nomDir+"/usuaris.dat";
    public static final String nomFitxerAdmin=nomDir+"/administradors.dat";
    static final Fitxers f=new Fitxers();

    //<editor-fold desc="Variables">
    private int id;
    private String NomUsuari;
    private String contrasena;
//    private boolean esAdmin;
    private String Nombre;
    private String Apellido;
    private String TipoUsuario;



    //</editor-fold>

    //<editor-fold desc="Construct">
    public Usuarios() {
    }

    public Usuarios(String NomUsuari, String Nombre, String Apellido , String contrasena, String TipoUsuario) {
        this.id=System.identityHashCode(this);
        this.NomUsuari = NomUsuari;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.contrasena = contrasena;
        this.TipoUsuario = TipoUsuario;
//        this.esAdmin = esAdmin;
    }




    //</editor-fold>

    //<editor-fold desc="Getters & Setters">

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNomUsuari() {
        return NomUsuari;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getContrasena() {
        return contrasena;
    }
    public String getTipoUsuario() {return TipoUsuario;}

//    public boolean isEsAdmin() {
//        return esAdmin;
//    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void setNom(String NomUsuari) {
        this.NomUsuari = NomUsuari;
    }

//    public void setEsAdmin(boolean esAdmin) {
//        this.esAdmin = esAdmin;
//    }



    public String getNomFitxer(String Fitxer){
        if(Objects.equals(Fitxer, "/administradors.dat")){
            return nomFitxerAdmin;

        }else{
            return nomFitxerUsuari;
        }

    }



    //</editor-fold>

    //<editor-fold desc="Mètodes">

    public void guardarUsuario(boolean esAdmin) throws IOException {
        if (!f.existeix(nomDir))
            f.creaDirectori(nomDir);

        String nombreArchivo;
        if (esAdmin) {
            nombreArchivo = nomFitxerAdmin;
        } else {
            nombreArchivo = nomFitxerUsuari;
        }
        f.escriuobjecteFitxer(this, nombreArchivo, true);
    }


    public  List<Usuarios> converteixAUsuario(List<Object> contingut){
        List<Usuarios> lUsuarios=new ArrayList<>();
        for (Object o: contingut){
            lUsuarios.add((Usuarios) o);
        }
        return lUsuarios;

    }
    public List<Usuarios> retornaUsuarios(String nombreArchivo) {
        return converteixAUsuario(f.retornaFitxerObjecteEnLlista(nombreArchivo));
    }

    //</editor-fold>

}
