package Clases;

// Clase hija: Revista hereda codigo, titulo, autor y existencias de clsRecurso
public class clsRevista extends clsRecurso {

    private int numEdicion;
    private String mesPublicacion;

    public clsRevista(String codigo, String titulo, String autor, int existencias,
                      int numEdicion, String mesPublicacion) {
        super(codigo, titulo, autor, existencias);
        this.numEdicion = numEdicion;
        this.mesPublicacion = mesPublicacion;
    }

    public int getNumEdicion() { return numEdicion; }
    public void setNumEdicion(int numEdicion) { this.numEdicion = numEdicion; }

    public String getMesPublicacion() { return mesPublicacion; }
    public void setMesPublicacion(String mesPublicacion) { this.mesPublicacion = mesPublicacion; }

    @Override
    public String getTipo() {
        return "Revista";
    }

    @Override
    public String toString() {
        return super.toString() + " - Edición " + numEdicion + " (" + mesPublicacion + ")";
    }
}
