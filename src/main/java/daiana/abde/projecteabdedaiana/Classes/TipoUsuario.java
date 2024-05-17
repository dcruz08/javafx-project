package daiana.abde.projecteabdedaiana.Classes;

public enum TipoUsuario {
    ADMINISTRADOR("Administrador", "/administradors.dat"),
    USUARIO_NORMAL("Usuario Normal", "/usuaris.dat");

    private final String value;
    private final String nomArchivo;

    TipoUsuario(String value, String nomArchivo) {
        this.value = value;
        this.nomArchivo = nomArchivo;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue()
    {
        return this.value;
    }
    public String getNomArchivo()
    {
        return ".data" + this.nomArchivo;
    }
}
