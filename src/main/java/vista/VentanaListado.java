package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador;
import controlador.exceptions.InputValidationException;
import modelo.validacion.Validador;
import modelo.vo.cliente.ClienteVO;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.sucursal.SucursalesVO;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaListado extends JFrame {

	Coordinador coordinador;

	private JComboBox<String> clienteComboBox;
	private JTable tableCuentas;
	private JButton btnOk, btnCancel;
	private JTable table;
	
	List<ClienteVO> clientesDisponibles;
	List<CuentaVO> cuentasDisponibles;
	List<SucursalesVO> sucursalesDisponibles;
	

	public VentanaListado(Coordinador coordinador) {
		this.coordinador = coordinador;
		actualizarVista();
	}

	public void actualizarVista() {
		setTitle("Banco Vigo");
		setSize(600, 330);
		setResizable(false);
		getContentPane().setLayout(null); // Establece el layout del contenedor a null para un diseño absoluto
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel superior con el combo para seleccionar clientes
		// No se usa un JPanel. Cada componente se posiciona individualmente.
		JLabel labelCliente = new JLabel("Seleccionar Cliente: ");
		labelCliente.setBounds(10, 10, 149, 25); // x, y, width, height
		getContentPane().add(labelCliente);

		clienteComboBox = new JComboBox<>();
		clienteComboBox.setBounds(169, 10, 200, 25);
		getContentPane().add(clienteComboBox);
		clienteComboBox.addActionListener(e -> {
			fetchCuentasDisponibles();
		});
		
		// Tabla de cuentas
		table = new JTable();
		
		String[] columnNames = { "Núm. Cta.", "Núm. Sucursal", "Ciudad", "Saldo" };
		DefaultTableModel model = new DefaultTableModel(columnNames,0);
		table.setModel(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 45, 580, 200); // Ajusta estas coordenadas según sea necesario
		getContentPane().add(scrollPane);

		// Panel inferior con botones
		// No se usa un JPanel. Cada componente se posiciona individualmente.
		btnOk = new JButton("OK");
		btnOk.setBounds(20, 256, 80, 25); // Ajusta estas coordenadas según sea necesario
		getContentPane().add(btnOk);
		btnOk.addActionListener(e -> {
			coordinador.ocultarVentanaListado();
			coordinador.mostrarVentanaPrincipal();
		});

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(110, 256, 80, 25); // Ajusta estas coordenadas según sea necesario
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(e -> {
			System.exit(0);
		});
		fetchClientesDisponibles();

	}

	private Object getObjetoSeleccionadoEnComboBox(ArrayList<?> listaBuscar, JComboBox<String> comboBoxBuscar, String nombreCampo) {
		if(listaBuscar.size() <= 0) {
			coordinador.mostrarVentanaError(nombreCampo+" Invalido. No hay opciones donde elegir.");
		}
		int index = comboBoxBuscar.getSelectedIndex();
		if(index < 0 || index >= listaBuscar.size()) {
			coordinador.mostrarVentanaError(nombreCampo+ " Invalido. Seleccionada opcion invalida.");
		}
		Object objetoSeleccionado = listaBuscar.get(index);
		if(objetoSeleccionado == null) {
			coordinador.mostrarVentanaError(nombreCampo+ " Invalido. No se ha encontrado el objeto en la lista.");
		}
		return objetoSeleccionado;
	}
	
	private void fetchClientesDisponibles() {
		clientesDisponibles = coordinador.buscarTodosClientes();
		for (ClienteVO clienteVO : clientesDisponibles) {
			clienteComboBox.addItem(clienteVO.getClNombre() + " " + clienteVO.getClApellido());
		}
	}
	
	private void fetchSucursalesDisponibles() {
		sucursalesDisponibles = coordinador.buscarTodasSucursales();
	}
	
	private String obtenerCiudadSucursal(Integer codigoSucursal) {
		String nombre = "Desconocido";
		for (SucursalesVO sucursal : sucursalesDisponibles) {
			if(sucursal.getSuCodSucursal() == codigoSucursal.intValue()) {
				nombre = sucursal.getSuCiudad();
			}
		}
		return nombre;
	}
	
	private void fetchCuentasDisponibles() {
		fetchSucursalesDisponibles();
		ClienteVO cliente = (ClienteVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) clientesDisponibles, clienteComboBox, "Cliente");
		try {
			Validador.validarCliente(cliente);
		} catch (InputValidationException e) {
			coordinador.mostrarVentanaError("ERROR VALIDANDO CLIENTE");
			e.printStackTrace();
			return;
		}
		String dni = cliente.getClDni();
		
		
		cuentasDisponibles = coordinador.buscarTodasCuentas(dni);
		String[] columnNames = { "Núm. Cta.", "Núm. Sucursal", "Ciudad", "Saldo" };
		
		DefaultTableModel model = new DefaultTableModel(columnNames,0);
		
		
		for (CuentaVO cuentaVO : cuentasDisponibles) {
             model.addRow(new Object[]{cuentaVO.getCuCodCuenta(), cuentaVO.getCuCodSucursal(), obtenerCiudadSucursal(cuentaVO.getCuCodSucursal()), cuentaVO.getCuSaldo()});
		}
		table.setModel(model);
		
	}

}
