package Formularios;

import Clases.clsConexion;
import Clases.clsUsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Formulario 1: Inicio de sesión
public class frmLogin extends javax.swing.JFrame {

    public frmLogin() {
        initComponents();
        setLocationRelativeTo(null);
    }

    //Variables globales
    private final clsConexion objCon = new clsConexion();
    private Connection cn;

    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biblioteca - Inicio de sesión");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setText("Control de Inventario - Biblioteca");

        jLabel1.setText("Usuario:");
        jLabel2.setText("Contraseña:");

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        // Enter en la contraseña = clic en Ingresar
        getRootPane().setDefaultButton(btnIngresar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(lblTitulo)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnIngresar)
                .addComponent(btnSalir))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(lblTitulo)
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnIngresar)
                .addComponent(btnSalir))
        );

        pack();
    }

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
            return;
        }
        if (cn == null) {
            cn = objCon.conectar();
            if (cn == null) {
                return; // clsConexion ya mostró el error
            }
        }
        try {
            clsUsuarioDAO dao = new clsUsuarioDAO(cn);
            if (dao.validar(usuario, clave)) {
                new frmInventario(objCon, cn).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                txtClave.setText("");
                txtClave.grabFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al validar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        objCon.desconectar();
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsuario;
}
