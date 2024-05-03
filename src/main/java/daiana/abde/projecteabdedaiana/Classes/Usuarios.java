package daiana.abde.projecteabdedaiana.Classes;

import abderrahim.ouabou.Fitxers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuarios implements Serializable {

    static final private String nomDir=".dat";
    static final private String nomFitxerUsuari=nomDir+"/usuaris.dat";
    static final private String nomFitxerAdmin=nomDir+"/administradors.dat";
    static final Fitxers f=new Fitxers();

    //<editor-fold desc="Variables">
    private int id;
    private String NomUsuari;
    private String contrasena;
    private boolean esAdmin;

    //</editor-fold>

    //<editor-fold desc="Construct">
    public Usuarios() {
    }

    public Usuarios(String NomUsuari,String contrasena, boolean esAdmin) {
        this.id = id;
        this.NomUsuari = NomUsuari;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void setNom(String NomUsuari) {
        this.NomUsuari = NomUsuari;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public String getNomFitxer(){
        if(isEsAdmin()){
            return nomFitxerUsuari;
        }else{
            return nomFitxerAdmin;
        }

    }

    //</editor-fold>

    //<editor-fold desc="Mètodes">

    public void guardarUsuari() throws IOException {
        if (!f.existeix(nomDir))
            f.creaDirectori(nomDir);
        f.escriuObjectFitxer(this,getNomFitxer(),true);
    }
    private static List<Usuarios> converteixALlibre(List <Object> lObj){
        List<Usuarios> llibres=new ArrayList<>();
        for (Object o: lObj){
            llibres.add((Usuarios) o);
        }
        return llibres;

    }
    public List<Usuarios> retornaLlibres() {
        return converteixALlibre(f.retornaFitxerObjecteEnLlista(getNomFitxer()));
    }

    //</editor-fold>

}
