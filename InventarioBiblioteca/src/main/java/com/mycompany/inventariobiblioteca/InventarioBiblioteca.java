package com.mycompany.inventariobiblioteca;

import Formularios.frmLogin;

// Punto de inicio del programa: abre el formulario de Login
public class InventarioBiblioteca {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }
}
