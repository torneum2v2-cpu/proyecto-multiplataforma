package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clsRecursoDAO {
    private final Connection cn;

    public clsRecursoDAO(Connection cn) {
        this.cn = cn;
    }

    // Ejecuta INSERT/UPDATE/DELETE con los parámetros recibidos (evita repetir código)
    private void ejecutar(String sql, Object... parametros) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            ps.executeUpdate();
        }
    }

    // ADICIÓN: guarda la parte común en tblrecurso y la parte propia en tbllibro/tblrevista
    public void registrar(clsRecurso rec) throws SQLException {
        ejecutar("INSERT INTO tblrecurso (idRecurso, Titulo, Autor, Existencias, Tipo) VALUES (?, ?, ?, ?, ?)",
                rec.getCodigo(), rec.getTitulo(), rec.getAutor(), rec.getExistencias(), rec.getTipo());
        try {
            if (rec instanceof clsLibro lib) {
                ejecutar("INSERT INTO tbllibro (idRecurso, NumPaginas, ISBN) VALUES (?, ?, ?)",
                        lib.getCodigo(), lib.getNumPaginas(), lib.getIsbn());
            } else if (rec instanceof clsRevista rev) {
                ejecutar("INSERT INTO tblrevista (idRecurso, NumEdicion, MesPublicacion) VALUES (?, ?, ?)",
                        rev.getCodigo(), rev.getNumEdicion(), rev.getMesPublicacion());
            }
        } catch (SQLException ex) {
            eliminar(rec.getCodigo()); // si falla la segunda parte, no dejar el registro a medias
            throw ex;
        }
    }

    // EDICIÓN: actualiza los datos comunes y los propios del tipo
    public void modificar(clsRecurso rec) throws SQLException {
        ejecutar("UPDATE tblrecurso SET Titulo=?, Autor=?, Existencias=? WHERE idRecurso=?",
                rec.getTitulo(), rec.getAutor(), rec.getExistencias(), rec.getCodigo());
        if (rec instanceof clsLibro lib) {
            ejecutar("UPDATE tbllibro SET NumPaginas=?, ISBN=? WHERE idRecurso=?",
                    lib.getNumPaginas(), lib.getIsbn(), lib.getCodigo());
        } else if (rec instanceof clsRevista rev) {
            ejecutar("UPDATE tblrevista SET NumEdicion=?, MesPublicacion=? WHERE idRecurso=?",
                    rev.getNumEdicion(), rev.getMesPublicacion(), rev.getCodigo());
        }
    }

    // ELIMINACIÓN: al borrar de tblrecurso se borra también de tbllibro/tblrevista (ON DELETE CASCADE)
    public void eliminar(String codigo) throws SQLException {
        ejecutar("DELETE FROM tblrecurso WHERE idRecurso=?", codigo);
    }

    // CONSULTA: todos los recursos, o los que coincidan por código o título
    public List<clsRecurso> buscar(String texto) throws SQLException {
        List<clsRecurso> lista = new ArrayList<>();
        String sql = "SELECT r.idRecurso, r.Titulo, r.Autor, r.Existencias, r.Tipo, "
                   + "l.NumPaginas, l.ISBN, v.NumEdicion, v.MesPublicacion "
                   + "FROM tblrecurso r "
                   + "LEFT JOIN tbllibro l ON r.idRecurso = l.idRecurso "
                   + "LEFT JOIN tblrevista v ON r.idRecurso = v.idRecurso";
        boolean isSearching = (texto != null && !texto.isEmpty());
        if (isSearching) {
            sql += " WHERE r.idRecurso LIKE ? OR r.Titulo LIKE ?";
        }
        sql += " ORDER BY r.idRecurso";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            if (isSearching) {
                ps.setString(1, "%" + texto + "%");
                ps.setString(2, "%" + texto + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String codigo = rs.getString("idRecurso");
                    String titulo = rs.getString("Titulo");
                    String autor = rs.getString("Autor");
                    int existencias = rs.getInt("Existencias");

                    if ("Libro".equals(rs.getString("Tipo"))) {
                        lista.add(new clsLibro(codigo, titulo, autor, existencias,
                                rs.getInt("NumPaginas"), rs.getString("ISBN")));
                    } else {
                        lista.add(new clsRevista(codigo, titulo, autor, existencias,
                                rs.getInt("NumEdicion"), rs.getString("MesPublicacion")));
                    }
                }
            }
        }
        return lista;
    }
}
