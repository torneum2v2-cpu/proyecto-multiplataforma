package Clases;

// Clase hija: Libro hereda codigo, titulo, autor y existencias de clsRecurso
public class clsLibro extends clsRecurso {

    private int numPaginas;
    private String isbn;

    public clsLibro(String codigo, String titulo, String autor, int existencias,
                    int numPaginas, String isbn) {
        super(codigo, titulo, autor, existencias);
        this.numPaginas = numPaginas;
        this.isbn = isbn;
    }

    public int getNumPaginas() { return numPaginas; }
    public void setNumPaginas(int numPaginas) { this.numPaginas = numPaginas; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public String getTipo() {
        return "Libro";
    }

    @Override
    public String toString() {
        return super.toString() + " - ISBN: " + isbn;
    }
}
