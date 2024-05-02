package daiana.abde.projecteabdedaiana.Classes;

public class Usuarios {

    //<editor-fold desc="Variables">
    private int id;
    private String contrasena;
    private boolean esAdmin;

    //</editor-fold>

    //<editor-fold desc="Construct">
    public Usuarios() {
    }

    public Usuarios(int id, String contrasena, boolean esAdmin) {
        this.id = id;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }


    //</editor-fold>

    //<editor-fold desc="toString">


    //</editor-fold>

}
