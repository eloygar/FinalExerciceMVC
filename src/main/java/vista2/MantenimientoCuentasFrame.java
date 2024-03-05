package vista2;

import javax.swing.*;
import java.awt.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MantenimientoCuentasFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MantenimientoCuentasFrame().setVisible(true);
        });
    }

	/**
	 * Create the frame.
	 */
	public MantenimientoCuentasFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		 initComponents();
	}

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("BANCO VIGO - Mantenimiento CUENTAS");
        setSize(600, 400);
        setLayout(new BorderLayout(5, 5));

        // Panel de Datos
        JPanel panelDatos = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Etiquetas y Campos de Texto
        String[] labels = {"Nº Cuenta:", "Cliente:", "Sucursal:", "Fecha:", "Saldo:"};
        for (int i = 0; i < labels.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            panelDatos.add(new JLabel(labels[i]), c);
            c.gridx = 1;
            c.gridy = i;
            if (i == 1 || i == 2) { // ComboBox para Cliente y Sucursal
                panelDatos.add(new JComboBox<>(), c);
            } else {
                panelDatos.add(new JTextField(20), c);
            }
        }

        // Panel de Botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        String[] botones = {"Nuevo", "Actualizar", "Eliminar", "Listado"};
        for (String textoBoton : botones) {
            panelBotones.add(new JButton(textoBoton));
        }

        // Botones OK y Cancel
        JPanel panelSur = new JPanel();
        panelSur.add(new JButton("OK"));
        panelSur.add(new JButton("Cancel"));

        // Agregar paneles al JFrame
        add(panelDatos, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);
        add(panelSur, BorderLayout.SOUTH);

        pack(); // Ajusta el tamaño de la ventana a los componentes
        setLocationRelativeTo(null); // Centrar en pantalla
    }


}
