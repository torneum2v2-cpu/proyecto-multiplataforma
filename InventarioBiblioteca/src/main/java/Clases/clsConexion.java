package Clases;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.DriverManager;

public class clsConexion {
    private Connection cn;

    public Connection conectar() {
    String url = "jdbc:mysql://localhost:3306/inventario_biblioteca?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    String user = "root";
    String pass = "wilmer";
        
        try {
            cn = DriverManager.getConnection(url, user, pass);
             System.out.println("Conexión exitosa a la base de datos.");            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexión: " + ex.getMessage(), 
                "Error",   JOptionPane.ERROR_MESSAGE);
            cn = null;
        }
        return cn;     
    }
    
    public void desconectar() {
        try {
            if (cn != null && !cn.isClosed()) {
                cn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

}
