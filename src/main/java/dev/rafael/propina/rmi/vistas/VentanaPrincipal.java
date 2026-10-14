package dev.rafael.propina.rmi.vistas;

import dev.rafael.propina.rmi.lib.DatosPropina;
import dev.rafael.propina.rmi.lib.IRemotaCalculoPropina;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 * Ventana principal del cliente para el cálculo de propina distribuido con LipeRMI.
 * 
 * @author Rafael Barragan Acevedo
 */
public class VentanaPrincipal extends JFrame {

    private CallHandler invocadorRemoto;
    private String ipServidor = "localhost";
    private int puerto = 9007;
    private IRemotaCalculoPropina calculoPropinaRemoto;
    private Client cliente;

    // Componentes de la interfaz
    private JLabel lblTitulo;
    private JTabbedPane jTabbedPane1;

    // Pestaña Conexión
    private JPanel panelConexion;
    private JLabel lblIp;
    private JTextField campoIPServidor;
    private JLabel lblPuerto;
    private JTextField campoPuertoServidor;
    private JLabel lblEstadoTexto;
    private JLabel txtEstado;
    private JButton btnIniciar;

    // Pestaña Calcular Propina
    private JPanel panelCalcular;
    private JLabel lblValorCuenta;
    private JTextField campoValorCuenta;
    private JLabel lblPorcentajePropina;
    private JTextField campoPorcentajePropina;
    private JButton btnCalcular;
    private JLabel lblPropinaTitulo;
    private JLabel txtPropina;
    private JLabel lblTotalTitulo;
    private JLabel txtTotalPagar;
    private JLabel txtMensaje;

    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cliente Calculadora de Propinas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 420);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
        contentPane.setBackground(new Color(245, 247, 250));

        // Título Superior
        lblTitulo = new JLabel("CLIENTE PROPINA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 43, 54));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        jTabbedPane1 = new JTabbedPane();
        jTabbedPane1.setFont(new Font("Tahoma", Font.PLAIN, 13));

        // -------------------------------------------------------------
        // Pestaña 1: CONEXIÓN
        // -------------------------------------------------------------
        panelConexion = new JPanel(new GridBagLayout());
        panelConexion.setBackground(Color.WHITE);
        panelConexion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblIp = new JLabel("DIRECCIÓN IP:");
        lblIp.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelConexion.add(lblIp, gbc);

        campoIPServidor = new JTextField("localhost", 15);
        campoIPServidor.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panelConexion.add(campoIPServidor, gbc);

        lblPuerto = new JLabel("PUERTO DE RED:");
        lblPuerto.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelConexion.add(lblPuerto, gbc);

        campoPuertoServidor = new JTextField("9007", 15);
        campoPuertoServidor.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        panelConexion.add(campoPuertoServidor, gbc);

        lblEstadoTexto = new JLabel("ESTADO:");
        lblEstadoTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelConexion.add(lblEstadoTexto, gbc);

        txtEstado = new JLabel("Desconectado");
        txtEstado.setFont(new Font("Tahoma", Font.BOLD, 13));
        txtEstado.setForeground(new Color(220, 53, 69)); // Rojo
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        panelConexion.add(txtEstado, gbc);

        btnIniciar = new JButton("Conectar");
        btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnIniciar.setForeground(new Color(0, 153, 51)); // Verde
        btnIniciar.setFocusPainted(false);
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelConexion.add(btnIniciar, gbc);

        jTabbedPane1.addTab("CONEXION", panelConexion);

        // -------------------------------------------------------------
        // Pestaña 2: CALCULAR PROPINA
        // -------------------------------------------------------------
        panelCalcular = new JPanel(new GridBagLayout());
        panelCalcular.setBackground(Color.WHITE);
        panelCalcular.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbcC = new GridBagConstraints();
        gbcC.insets = new Insets(8, 10, 8, 10);
        gbcC.fill = GridBagConstraints.HORIZONTAL;

        lblValorCuenta = new JLabel("VALOR CUENTA ($):");
        lblValorCuenta.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbcC.gridx = 0;
        gbcC.gridy = 0;
        gbcC.weightx = 0.4;
        panelCalcular.add(lblValorCuenta, gbcC);

        campoValorCuenta = new JTextField(12);
        campoValorCuenta.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gbcC.gridx = 1;
        gbcC.gridy = 0;
        gbcC.weightx = 0.6;
        panelCalcular.add(campoValorCuenta, gbcC);

        lblPorcentajePropina = new JLabel("PORCENTAJE PROPINA (%):");
        lblPorcentajePropina.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbcC.gridx = 0;
        gbcC.gridy = 1;
        gbcC.weightx = 0.4;
        panelCalcular.add(lblPorcentajePropina, gbcC);

        campoPorcentajePropina = new JTextField(12);
        campoPorcentajePropina.setFont(new Font("Tahoma", Font.PLAIN, 13));
        gbcC.gridx = 1;
        gbcC.gridy = 1;
        gbcC.weightx = 0.6;
        panelCalcular.add(campoPorcentajePropina, gbcC);

        btnCalcular = new JButton("CALCULAR");
        btnCalcular.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCalcular.setForeground(new Color(0, 153, 51));
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        gbcC.gridx = 0;
        gbcC.gridy = 2;
        gbcC.gridwidth = 2;
        gbcC.fill = GridBagConstraints.NONE;
        gbcC.anchor = GridBagConstraints.CENTER;
        panelCalcular.add(btnCalcular, gbcC);

        gbcC.gridwidth = 1;
        gbcC.anchor = GridBagConstraints.WEST;
        gbcC.fill = GridBagConstraints.HORIZONTAL;

        lblPropinaTitulo = new JLabel("PROPINA ($):");
        lblPropinaTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbcC.gridx = 0;
        gbcC.gridy = 3;
        gbcC.weightx = 0.4;
        panelCalcular.add(lblPropinaTitulo, gbcC);

        txtPropina = new JLabel("0.00");
        txtPropina.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtPropina.setForeground(new Color(0, 102, 204));
        gbcC.gridx = 1;
        gbcC.gridy = 3;
        gbcC.weightx = 0.6;
        panelCalcular.add(txtPropina, gbcC);

        lblTotalTitulo = new JLabel("TOTAL A PAGAR ($):");
        lblTotalTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbcC.gridx = 0;
        gbcC.gridy = 4;
        gbcC.weightx = 0.4;
        panelCalcular.add(lblTotalTitulo, gbcC);

        txtTotalPagar = new JLabel("0.00");
        txtTotalPagar.setFont(new Font("Tahoma", Font.BOLD, 14));
        txtTotalPagar.setForeground(new Color(40, 167, 69));
        gbcC.gridx = 1;
        gbcC.gridy = 4;
        gbcC.weightx = 0.6;
        panelCalcular.add(txtTotalPagar, gbcC);

        txtMensaje = new JLabel(" ");
        txtMensaje.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtMensaje.setForeground(new Color(108, 117, 125));
        txtMensaje.setBorder(BorderFactory.createTitledBorder("Estado"));
        gbcC.gridx = 0;
        gbcC.gridy = 5;
        gbcC.gridwidth = 2;
        gbcC.weightx = 1.0;
        panelCalcular.add(txtMensaje, gbcC);

        jTabbedPane1.addTab("CALCULAR PROPINA", panelCalcular);

        contentPane.add(jTabbedPane1, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    private void btnIniciarActionPerformed(ActionEvent evt) {
        try {
            if (btnIniciar.getText().equalsIgnoreCase("Conectar")) {
                puerto = Integer.parseInt(campoPuertoServidor.getText().trim());
                ipServidor = campoIPServidor.getText().trim();
                invocadorRemoto = new CallHandler();
                cliente = new Client(ipServidor, puerto, invocadorRemoto);
                calculoPropinaRemoto = (IRemotaCalculoPropina) cliente.getGlobal(IRemotaCalculoPropina.class);

                btnIniciar.setText("Desconectar");
                btnIniciar.setForeground(Color.RED);
                txtEstado.setText("Conectado");
                txtEstado.setForeground(new Color(0, 153, 51));
            } else if (btnIniciar.getText().equalsIgnoreCase("Desconectar")) {
                if (cliente != null) {
                    cliente.close();
                }
                btnIniciar.setText("Conectar");
                txtEstado.setText("Desconectado");
                btnIniciar.setForeground(new Color(0, 153, 51));
                txtEstado.setForeground(Color.RED);
                calculoPropinaRemoto = null;
            }
        } catch (IOException ex) {
            System.out.println("ERROR AL CONECTAR");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "No se pudo conectar al servidor RMI en " + ipServidor + ":" + puerto + "\n" + ex.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El puerto debe ser un número entero válido.",
                    "Puerto inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnCalcularActionPerformed(ActionEvent evt) {
        if (calculoPropinaRemoto == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe conectarse primero al servidor desde la pestaña 'CONEXION'.",
                    "No conectado",
                    JOptionPane.WARNING_MESSAGE);
            jTabbedPane1.setSelectedIndex(0);
            return;
        }

        float valorCuenta;
        float porcentajePropina;

        try {
            valorCuenta = Float.parseFloat(campoValorCuenta.getText().trim());
            porcentajePropina = Float.parseFloat(campoPorcentajePropina.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese valores numéricos válidos en el valor de la cuenta y el porcentaje.",
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Valor Cuenta: " + valorCuenta);
                    System.out.println("Porcentaje: " + porcentajePropina + "%");

                    DatosPropina datos = new DatosPropina(valorCuenta, porcentajePropina);
                    System.out.println("Enviados los datos\nEsperando respuesta");

                    datos = calculoPropinaRemoto.calcularPropina(datos);

                    System.out.println("Propina: " + datos.getValorPropina() + "\nTotal: " + datos.getTotalPagar());

                    final DatosPropina resultado = datos;
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            txtPropina.setText(String.format("$ %.2f", resultado.getValorPropina()));
                            txtTotalPagar.setText(String.format("$ %.2f", resultado.getTotalPagar()));
                            txtMensaje.setText(resultado.getMensaje());
                        }
                    });

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                            "ERROR con el cliente: " + ex.getMessage(),
                            "Error de Ejecución Remota",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("ERROR con el cliente: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };

        hilo.start();
    }

    public JLabel getTxtEstado() {
        return txtEstado;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }
}
