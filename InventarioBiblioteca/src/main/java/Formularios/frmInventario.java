package Formularios;

import Clases.clsConexion;
import Clases.clsLibro;
import Clases.clsRecurso;
import Clases.clsRecursoDAO;
import Clases.clsRevista;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// Formulario 2: Inventario con JTable (seleccionar para editar o borrar)
public class frmInventario extends javax.swing.JFrame {

    public frmInventario(clsConexion objCon, Connection cn) {
        this.objCon = objCon;
        this.dao = new clsRecursoDAO(cn);
        initComponents();
        jtRecursos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        getRootPane().setDefaultButton(btnBuscar); // Enter = Buscar
        mostrar();
    }

    //Variables globales
    private final clsConexion objCon;
    private final clsRecursoDAO dao;
    private List<clsRecurso> lista = new ArrayList<>(); // recursos que se ven en la tabla

    //Funciones
    // Llena el JTable con los recursos que coinciden con el texto (vacío = todos)
    public void mostrar(String texto) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // la edición se hace en el formulario de registro
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Tipo");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Existencias");
        modelo.addColumn("Páginas");
        modelo.addColumn("ISBN");
        modelo.addColumn("Edición");
        modelo.addColumn("Mes publicación");

        try {
            lista = dao.buscar(texto);
            for (clsRecurso r : lista) {
                Object[] fila = {r.getCodigo(), r.getTipo(), r.getTitulo(), r.getAutor(),
                                 r.getExistencias(), "", "", "", ""};
                if (r instanceof clsLibro lib) {
                    fila[5] = lib.getNumPaginas();
                    fila[6] = lib.getIsbn();
                } else if (r instanceof clsRevista rev) {
                    fila[7] = rev.getNumEdicion();
                    fila[8] = rev.getMesPublicacion();
                }
                modelo.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        jtRecursos.setModel(modelo);
        lblTotal.setText("Total de recursos: " + lista.size());
    }

    public void mostrar() {
        mostrar(txtBuscar.getText().trim());
    }

    // Devuelve el recurso de la fila seleccionada o null si no hay selección
    private clsRecurso seleccionado() {
        int fila = jtRecursos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un recurso de la tabla.");
            return null;
        }
        return lista.get(fila);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtRecursos = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biblioteca - Inventario de recursos");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Inventario de libros y revistas");

        jLabel1.setText("Buscar (código o título):");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnTodos.setText("Mostrar todos");
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jtRecursos);

        jtRecursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtRecursos.getTableHeader().setReorderingAllowed(false);
        jtRecursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtRecursosMouseClicked(evt);
            }
        });

        lblTotal.setText("Total de recursos: 0");

        btnNuevo.setText("Nuevo ingreso");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBuscar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnTodos))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTotal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitulo)
                    .addGap(12, 12, 12)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar)
                        .addComponent(btnTodos))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotal)
                        .addComponent(btnNuevo)
                        .addComponent(btnEditar)
                        .addComponent(btnEliminar)
                        .addComponent(btnSalir))
                    .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        mostrar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        txtBuscar.setText("");
        mostrar();
    }//GEN-LAST:event_btnTodosActionPerformed

    // Abre el formulario secundario vacío para registrar un nuevo ingreso
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        new frmRegistro(this, dao, null).setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    // Abre el formulario secundario con los datos del recurso seleccionado
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        clsRecurso rec = seleccionado();
        if (rec != null) {
            new frmRegistro(this, dao, rec).setVisible(true);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        clsRecurso rec = seleccionado();
        if (rec == null) {
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el " + rec.getTipo().toLowerCase() + " \"" + rec.getTitulo() + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(rec.getCodigo());
                JOptionPane.showMessageDialog(this, "Recurso eliminado.");
                mostrar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        objCon.desconectar();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    // Doble clic sobre una fila = Editar
    private void jtRecursosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtRecursosMouseClicked
        if (evt.getClickCount() == 2) {
            btnEditarActionPerformed(null);
        }
    }//GEN-LAST:event_jtRecursosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtRecursos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
