package vista;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.Coordinador;
import controlador.exceptions.InputValidationException;
import modelo.validacion.Validador;
import modelo.vo.cliente.ClienteVO;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.cuenta.CuentasclienteVO;
import modelo.vo.sucursal.SucursalesVO;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaPrincipal extends JFrame {

	Coordinador coordinador;

	JTextField cuentaTextField;
	JComboBox<String> clienteComboBox;
	JComboBox<String> sucursalComboBox;
	JTextField fechaTextField;
	JTextField saldoTextField;

	List<ClienteVO> clientesDisponibles;
	List<SucursalesVO> sucursalesDisponibles;
	List<CuentaVO> cuentasSucursal;
	private JTable table;

	public VentanaPrincipal(Coordinador coordinador) {
		this.coordinador = coordinador;
		actualizarVista();
	}

	public void actualizarVista() {
		setTitle("BANCO VIGO - GESTION CUENTAS");
		setSize(600, 504);
		setResizable(false);

		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cuentas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(22, 16, 502, 189);
		getContentPane().add(panel);
		panel.setLayout(null);

		// Create labels
		JLabel cuentaLabel = new JLabel("Nº Cuenta:");
		cuentaLabel.setBounds(20, 19, 80, 25);
		panel.add(cuentaLabel);

		JLabel clienteLabel = new JLabel("Cliente:");
		clienteLabel.setBounds(20, 49, 80, 25);
		panel.add(clienteLabel);

		JLabel sucursalLabel = new JLabel("Sucursal:");
		sucursalLabel.setBounds(20, 79, 80, 25);
		panel.add(sucursalLabel);

		JLabel fechaLabel = new JLabel("Fecha:");
		fechaLabel.setBounds(20, 109, 80, 25);
		panel.add(fechaLabel);

		JLabel saldoLabel = new JLabel("Saldo:");
		saldoLabel.setBounds(20, 139, 80, 25);
		panel.add(saldoLabel);

		cuentaTextField = new JTextField();
		cuentaTextField.setBounds(100, 19, 160, 25);
		panel.add(cuentaTextField);

		clienteComboBox = new JComboBox<>();
		clienteComboBox.setBounds(100, 49, 160, 25);
		panel.add(clienteComboBox);

		sucursalComboBox = new JComboBox<>();
		sucursalComboBox.setBounds(100, 79, 160, 25);
		panel.add(sucursalComboBox);

		fechaTextField = new JTextField();
		fechaTextField.setBounds(100, 109, 160, 25);
		panel.add(fechaTextField);

		saldoTextField = new JTextField();
		saldoTextField.setBounds(100, 139, 160, 25);
		panel.add(saldoTextField);

		// Create buttons
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(300, 19, 100, 25);
		panel.add(btnNuevo);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(300, 49, 100, 25);
		panel.add(btnActualizar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(300, 79, 100, 25);
		panel.add(btnEliminar);

		JButton btnListado = new JButton("Listado");
		btnListado.setBounds(300, 109, 100, 25);
		panel.add(btnListado);

		JButton btnOperaciones = new JButton("Operaciones");
		btnOperaciones.setBounds(300, 140, 100, 25);
		panel.add(btnOperaciones);

		btnOperaciones.addActionListener(e -> {
			coordinador.ocultarVentanaPrincipal();
			coordinador.mostrarVentanaOperacion();
		});

		btnListado.addActionListener(e -> {
			listarSucursales();
		});

		btnEliminar.addActionListener(e -> {
			eliminarCuenta();
		});
		btnActualizar.addActionListener(e -> {
			actualizarCuenta();
		});
		btnNuevo.addActionListener(e -> {
			crearCuenta();
		});

		JButton okButton = new JButton("OK");
		okButton.setBounds(22, 431, 80, 25);
		okButton.addActionListener(e -> {
			coordinador.ocultarVentanaPrincipal();
			coordinador.mostrarVentanaListado();
		});
		getContentPane().add(okButton);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setBounds(112, 431, 80, 25);
		cancelButton.addActionListener(e -> {
			System.exit(0);
		});

		getContentPane().add(cancelButton);

		table = new JTable();
		String[] columnNames = { "Núm. Cta.", "Fecha Creacion", "Saldo" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		table.setModel(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 226, 496, 183);
		getContentPane().add(scrollPane);

		fetchClientesDisponibles();
		fetchSucursalesDisponibles();
	}

	public Integer getIntegerFromTextField(JTextField textField, String nombreCampo) {
		try {
			return Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			coordinador.mostrarVentanaError(
					nombreCampo + "[" + (textField.getText().isBlank() ? "(VACIO)" : textField.getText()) + "]"
							+ " debe contener un numero entero válido. ");
			return null;
		}
	}

	private Date getSQLDateFromString(String fechaString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			java.util.Date utilDate = dateFormat.parse(fechaString);

			return new Date(utilDate.getTime());
		} catch (ParseException e) {
			coordinador.mostrarVentanaError("Fecha Creacion [" + (fechaString.isBlank() ? "(VACIO)" : fechaString)
					+ "] no es valido. Se esperaba una fecha en el formato dd-MM-yyyy.");
			return null;
		}
	}

	private Object getObjetoSeleccionadoEnComboBox(ArrayList<?> listaBuscar, JComboBox<String> comboBoxBuscar,
			String nombreCampo) {
		if (listaBuscar.size() <= 0) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. No hay opciones donde elegir.");
		}
		int index = comboBoxBuscar.getSelectedIndex();
		if (index < 0 || index >= listaBuscar.size()) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. Seleccionada opcion invalida.");
		}
		Object objetoSeleccionado = listaBuscar.get(index);
		if (objetoSeleccionado == null) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. No se ha encontrado el objeto en la lista.");
		}
		return objetoSeleccionado;
	}

	private void crearCuenta() {
		/*
		 * Integer codigoCuenta = getIntegerFromTextField(cuentaTextField,
		 * "Numero de Cuenta"); if (!Validador.validarIntPositivo(codigoCuenta)) {
		 * return; }
		 */

		Integer codigoCuenta = 0;

		SucursalesVO sucursal = (SucursalesVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) sucursalesDisponibles,
				sucursalComboBox, "Sucursal");
		try {
			Validador.validarSucursal(sucursal);
		} catch (InputValidationException e) {
			coordinador.mostrarVentanaError("ERROR VALIDANDO SUCURSAL");
			e.printStackTrace();
			return;
		}
		Integer codigoSucursal = sucursal.getSuCodSucursal();
		if (!Validador.validarIntPositivo(codigoSucursal)) {
			return;
		}
		System.out.println(fechaTextField.getText());
		Date fechaCreacion = getSQLDateFromString(fechaTextField.getText());
		if (!Validador.validarDateSQL(fechaCreacion)) {
			return;
		}
		Integer saldo = getIntegerFromTextField(saldoTextField, "Saldo");
		if (!Validador.validarIntPositivo(saldo)) {
			return;
		}
		ClienteVO cliente = (ClienteVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) clientesDisponibles,
				clienteComboBox, "Cliente");
		try {
			Validador.validarCliente(cliente);
		} catch (InputValidationException e) {
			coordinador.mostrarVentanaError("ERROR VALIDANDO CLIENTE");
			e.printStackTrace();
			return;
		}
		String dni = cliente.getClDni();

		CuentaVO cuenta = new CuentaVO();
		cuenta.setCuCodCuenta(codigoCuenta);
		cuenta.setCuCodSucursal(codigoSucursal);
		cuenta.setCuFechaCreacion(fechaCreacion);
		cuenta.setCuSaldo(saldo);

		System.out.println("Datos Validados. Insertando Cuenta");
		CuentaVO cuentaInsertada = coordinador.insercionCuenta(cuenta);

		// Crear CuentasClienteVO
		CuentasclienteVO cuentaCliente = new CuentasclienteVO();
		cuentaCliente.setCcCodCuenta(cuentaInsertada.getCuCodCuenta());
		cuentaCliente.setCcDni(dni);
		System.out.println(cuentaInsertada.getCuCodCuenta());

		System.out.println("Creando Relacion Cuenta-Cliente");
		coordinador.insercionCuentaCliente(cuentaCliente);

		coordinador.mostrarInfoDialog("Nueva Cuenta Creada", "Creada la cuenta de " + cliente.toPrettyString()
				+ ". CODIGO CUENTA: " + cuentaInsertada.getCuCodCuenta());
	}

	private void actualizarCuenta() {
		System.out.println("Actualizar cuenta");
		Integer codigoCuenta = getIntegerFromTextField(cuentaTextField, "Numero de Cuenta");
		if (!Validador.validarIntPositivo(codigoCuenta)) {
			return;
		}

		SucursalesVO sucursal = (SucursalesVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) sucursalesDisponibles,
				sucursalComboBox, "Sucursal");
		try {
			Validador.validarSucursal(sucursal);
		} catch (InputValidationException e) {
			coordinador.mostrarVentanaError("ERROR VALIDANDO SUCURSAL");
			e.printStackTrace();
			return;
		}
		Integer codigoSucursal = sucursal.getSuCodSucursal();
		if (!Validador.validarIntPositivo(codigoSucursal)) {
			return;
		}
		System.out.println(fechaTextField.getText());
		Date fechaCreacion = getSQLDateFromString(fechaTextField.getText());
		if (!Validador.validarDateSQL(fechaCreacion)) {
			return;
		}
		Integer saldo = getIntegerFromTextField(saldoTextField, "Saldo");
		if (!Validador.validarIntPositivo(saldo)) {
			return;
		}

		CuentaVO cuenta = new CuentaVO();
		cuenta.setCuCodCuenta(codigoCuenta);
		cuenta.setCuCodSucursal(codigoSucursal);
		cuenta.setCuFechaCreacion(fechaCreacion);
		cuenta.setCuSaldo(saldo);
		System.out.println("Validados datos formulario. Actualizando");
		if (!coordinador.actualizarCuenta(cuenta)) {
			return;
		}

		coordinador.mostrarInfoDialog("Cuenta Actualizada", "Actualizada la cuenta " + cuenta.getCuCodCuenta());
	}

	private void eliminarCuenta() {
		System.out.println("Eliminar cuenta");
		Integer codigoCuenta = getIntegerFromTextField(cuentaTextField, "Numero de Cuenta");
		if (!Validador.validarIntPositivo(codigoCuenta)) {
			return;
		}

		System.out.println("Validados datos formulario. Actualizando");

		if (!coordinador.borrarCuentaCliente(codigoCuenta)) {
			return; // No se pudo borrar cuentaCliente, no borrar cuenta
		}

		coordinador.borrarCuenta(codigoCuenta);
		coordinador.mostrarInfoDialog("Cuenta Borrada", "Borrada la cuenta " + codigoCuenta);

	}

	private void fetchClientesDisponibles() {
		clientesDisponibles = coordinador.buscarTodosClientes();
		for (ClienteVO clienteVO : clientesDisponibles) {
			clienteComboBox.addItem(clienteVO.getClNombre() + " " + clienteVO.getClApellido());
		}
	}

	private void fetchSucursalesDisponibles() {
		sucursalesDisponibles = coordinador.buscarTodasSucursales();
		for (SucursalesVO sucursalVO : sucursalesDisponibles) {
			sucursalComboBox.addItem(sucursalVO.getSuCiudad());
		}
	}

	private String obtenerCiudadSucursal(Integer codigoSucursal) {
		String nombre = "Desconocido";
		for (SucursalesVO sucursal : sucursalesDisponibles) {
			if (sucursal.getSuCodSucursal() == codigoSucursal.intValue()) {
				nombre = sucursal.getSuCiudad();
			}
		}
		return nombre;
	}

	
	
	private void fetchCuentasSucursal() {
		SucursalesVO sucursalSeleccionada = (SucursalesVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) sucursalesDisponibles, sucursalComboBox, "Sucursal");
		
		cuentasSucursal = coordinador.buscarTodasCuentas(sucursalSeleccionada.getSuCodSucursal());
		
		String[] columnNames = { "Núm. Cta.", "Fecha Creacion", "Saldo"};
		
		DefaultTableModel model = new DefaultTableModel(columnNames,0);
		
		for (CuentaVO cuentaVO : cuentasSucursal) {
             model.addRow(new Object[]{cuentaVO.getCuCodCuenta(), cuentaVO.getCuFechaCreacion(),cuentaVO.getCuSaldo()});
		}
		table.setModel(model);
		
	}

	private void listarSucursales() {
		fetchCuentasSucursal();
	}
}
