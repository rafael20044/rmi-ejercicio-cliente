package dev.rafael.propina.rmi;

import dev.rafael.propina.rmi.vistas.VentanaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para lanzar la interfaz gráfica del cliente.
 * 
 * @author Rafael Barragan Acevedo
 */
public class Principal {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal v = new VentanaPrincipal();
                v.setLocationRelativeTo(null);
                v.setVisible(true);
            }
        });
    }
}
