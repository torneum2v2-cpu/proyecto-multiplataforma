package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class clsUsuarioDAO {
    private final Connection cn;

    public clsUsuarioDAO(Connection cn) {
        this.cn = cn;
    }

    // Devuelve true si el usuario y la clave existen en tblusuario
    public boolean validar(String usuario, String clave) throws SQLException {
        String sql = "SELECT idUsuario FROM tblusuario WHERE Usuario=? AND Clave=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
