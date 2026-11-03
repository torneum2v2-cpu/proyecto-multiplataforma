package Formularios;

import Clases.clsLibro;
import Clases.clsRecurso;
import Clases.clsRecursoDAO;
import Clases.clsRevista;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Formulario 3 (secundario): registrar nuevos ingresos o editar un recurso existente
public class frmRegistro extends javax.swing.JFrame {

    // recurso == null  -> modo NUEVO INGRESO
    // recurso != null  -> modo EDICIÓN de ese recurso
    public frmRegistro(frmInventario inventario, clsRecursoDAO dao, clsRecurso recurso) {
        this.inventario = inventario;
        this.dao = dao;
        this.recurso = recurso;
        initComponents();
        if (recurso == null) {
            limpiar();
        } else {
            cargarDatos();
        }
    }

    //Variables globales
    private final frmInventario inventario;
    private final clsRecursoDAO dao;
    private final clsRecurso recurso;

    //Funciones
    public void limpiar() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtExistencias.setText("");
        txtPaginas.setText("");
        txtIsbn.setText("");
        txtEdicion.setText("");
        cboMes.setSelectedIndex(0);
        activarCampos();
        txtCodigo.grabFocus();
    }

    // Habilita solo los campos que corresponden al tipo elegido
    private void activarCampos() {
        boolean esLibro = cboTipo.getSelectedIndex() == 0;
        txtPaginas.setEnabled(esLibro);
        txtIsbn.setEnabled(esLibro);
        txtEdicion.setEnabled(!esLibro);
        cboMes.setEnabled(!esLibro);
    }

    // Pasa los datos del recurso seleccionado a las cajas de texto
    private void cargarDatos() {
        setTitle("Biblioteca - Editar recurso");
        lblTitulo.setText("Editar recurso");
        btnGuardar.setText("Guardar cambios");
        btnLimpiar.setVisible(false);

        txtCodigo.setText(recurso.getCodigo());
        txtTitulo.setText(recurso.getTitulo());
        txtAutor.setText(recurso.getAutor());
        txtExistencias.setText(String.valueOf(recurso.getExistencias()));

        if (recurso instanceof clsLibro lib) {
            cboTipo.setSelectedIndex(0);
            txtPaginas.setText(String.valueOf(lib.getNumPaginas()));
            txtIsbn.setText(lib.getIsbn());
        } else if (recurso instanceof clsRevista rev) {
            cboTipo.setSelectedIndex(1);
            txtEdicion.setText(String.valueOf(rev.getNumEdicion()));
            cboMes.setSelectedItem(rev.getMesPublicacion());
        }
        // El código y el tipo no se cambian al editar
        txtCodigo.setEnabled(false);
        cboTipo.setEnabled(false);
        activarCampos();
    }

    // Convierte el texto a entero; si no es válido muestra el mensaje y devuelve -1
    private int leerEntero(javax.swing.JTextField txt, String campo, int minimo) {
        try {
            int valor = Integer.parseInt(txt.getText().trim());
            if (valor >= minimo) {
                return valor;
            }
        } catch (NumberFormatException ex) {
            // se avisa abajo
        }
        JOptionPane.showMessageDialog(this, "El campo \"" + campo + "\" debe ser un número entero mayor o igual a " + minimo + ".");
        txt.grabFocus();
        return -1;
    }

    // Crea un clsLibro o clsRevista con los datos del formulario (null si hay errores)
    private clsRecurso leerFormulario() {
        String codigo = txtCodigo.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();

        if (codigo.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete código, título y autor.");
            return null;
        }
        int existencias = leerEntero(txtExistencias, "Existencias", 0);
        if (existencias < 0) {
            return null;
        }

        if (cboTipo.getSelectedIndex() == 0) {
            int paginas = leerEntero(txtPaginas, "Nº de páginas", 1);
            if (paginas < 0) {
                return null;
            }
            String isbn = txtIsbn.getText().trim();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese el ISBN.");
                return null;
            }
            return new clsLibro(codigo, titulo, autor, existencias, paginas, isbn);
        } else {
            int edicion = leerEntero(txtEdicion, "Nº de edición", 1);
            if (edicion < 0) {
                return null;
            }
            return new clsRevista(codigo, titulo, autor, existencias, edicion, cboMes.getSelectedItem().toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        txtExistencias = new javax.swing.JTextField();
        txtPaginas = new javax.swing.JTextField();
        txtIsbn = new javax.swing.JTextField();
        txtEdicion = new javax.swing.JTextField();
        cboMes = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Biblioteca - Nuevo ingreso");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Registrar nuevo ingreso");

        jLabel1.setText("Tipo:");

        jLabel2.setText("Código:");

        jLabel3.setText("Título:");

        jLabel4.setText("Autor:");

        jLabel5.setText("Existencias:");

        jLabel6.setText("Nº de páginas:");

        jLabel7.setText("ISBN:");

        jLabel8.setText("Nº de edición:");

        jLabel9.setText("Mes de publicación:");

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libro", "Revista" }));
        cboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoActionPerformed(evt);
            }
        });

        cboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitulo)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(60, 60, 60)
                            .addComponent(btnGuardar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLimpiar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCancelar)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitulo)
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(cboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(15, 15, 15)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnLimpiar)
                        .addComponent(btnCancelar))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoActionPerformed
        activarCampos();
    }//GEN-LAST:event_cboTipoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        clsRecurso rec = leerFormulario();
        if (rec == null) {
            return;
        }
        try {
            if (recurso == null) {
                dao.registrar(rec);
                JOptionPane.showMessageDialog(this, rec.getTipo() + " registrado con éxito.");
            } else {
                dao.modificar(rec);
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
            }
            inventario.mostrar(); // refresca el JTable del inventario
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cboMes;
    private javax.swing.JComboBox<String> cboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEdicion;
    private javax.swing.JTextField txtExistencias;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtPaginas;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
