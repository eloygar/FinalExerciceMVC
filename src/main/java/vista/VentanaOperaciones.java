package vista;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controlador.Coordinador;
import controlador.exceptions.InputValidationException;
import modelo.validacion.Validador;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.transaccion.TransaccionesVO;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class VentanaOperaciones extends JFrame {

	Coordinador coordinador;

	JTextField dniTextField;
	JTextField cantidadTextField;
	List<CuentaVO> cuentasDeClienteActual;
	List<CuentaVO> cuentasDestinatarios;
	JComboBox<String> cuentasComboBox;
	JComboBox<String> destinatarioComboBox;

	public VentanaOperaciones(Coordinador coordinador) {
		this.coordinador = coordinador;
		inicializarVista();
	}

	private void actualizarVista() {
		validarDNIyPoblarComboBox();
	}
	
	public void inicializarVista() {
		cuentasDeClienteActual = new ArrayList<CuentaVO>();
		cuentasDestinatarios = new ArrayList<CuentaVO>();
		setTitle("Banco Vigo - Operaciones");
		getContentPane().setLayout(null);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Operaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(21, 22, 625, 58);
		getContentPane().add(panel);
		panel.setLayout(null);

		// Botones de opción

		JRadioButton ingresoButton = new JRadioButton("Ingreso");
		ingresoButton.setBounds(157, 15, 100, 30);
		panel.add(ingresoButton);

		JRadioButton reintegroButton = new JRadioButton("Reintegro");
		reintegroButton.setBounds(267, 15, 100, 30);
		panel.add(reintegroButton);

		JRadioButton transferenciaButton = new JRadioButton("Transferencia");
		transferenciaButton.setBounds(377, 15, 120, 30);
		panel.add(transferenciaButton);

		// Grupo de botones para que solo uno pueda ser seleccionado a la vez
		ButtonGroup operacionGroup = new ButtonGroup();
		operacionGroup.add(reintegroButton);
		operacionGroup.add(ingresoButton);
		operacionGroup.add(transferenciaButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(21, 91, 625, 107);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		// Campo de texto para el DNI
		JLabel dniLabel = new JLabel("DNI:");
		dniLabel.setBounds(24, 24, 80, 25);
		panel_1.add(dniLabel);

		
		dniTextField = new JTextField();
		dniTextField.setBounds(72, 24, 150, 25);
		panel_1.add(dniTextField);
		dniTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validarDNIyPoblarComboBox();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validarDNIyPoblarComboBox();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				return;
			}

		});

		
		// Combobox para seleccionar cuenta
		JLabel cuentaLabel = new JLabel("Seleccionar cuenta:");
		cuentaLabel.setBounds(257, 24, 150, 25);
		panel_1.add(cuentaLabel);
		cuentasComboBox = new JComboBox<>();
		cuentasComboBox.setBounds(372, 24, 243, 25);
		panel_1.add(cuentasComboBox);

		// Etiqueta para la cantidad
		JLabel cantidadLabel = new JLabel("Cantidad:");
		cantidadLabel.setBounds(257, 60, 80, 25);
		panel_1.add(cantidadLabel);

		cantidadTextField = new JTextField();
		cantidadTextField.setBounds(372, 60, 243, 25);
		panel_1.add(cantidadTextField);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Destinatario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(21, 209, 625, 71);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		// Combobox para seleccionar cuenta del destinatario
		JLabel cuentaDestLabel = new JLabel("Seleccionar cuenta:");
		cuentaDestLabel.setBounds(43, 24, 150, 25);
		panel_2.add(cuentaDestLabel);

		destinatarioComboBox = new JComboBox();
		destinatarioComboBox.setBounds(191, 25, 243, 22);
		panel_2.add(destinatarioComboBox);

		destinatarioComboBox.setEnabled(false);
		ingresoButton.addActionListener(e -> {
			destinatarioComboBox.setEnabled(false);
		});
		reintegroButton.addActionListener(e -> {
			destinatarioComboBox.setEnabled(false);
		});
		transferenciaButton.addActionListener(e -> {
			destinatarioComboBox.setEnabled(true);
			fetchCuentasDestinatarios();
		});

		// Botones OK y Cancel
		JButton okButton = new JButton("OK");
		okButton.setBounds(21, 287, 80, 30);
		getContentPane().add(okButton);
		okButton.addActionListener(e -> {
			System.out.println("OK");
			ButtonModel selectedButton = operacionGroup.getSelection();
			if (selectedButton != null) {
				if (selectedButton.equals(reintegroButton.getModel())) {
					hacerReintegro();
				} else if (selectedButton.equals(ingresoButton.getModel())) {
					hacerIngreso();
				} else if (selectedButton.equals(transferenciaButton.getModel())) {
					hacerTransferencia();
				}
			} else {
				coordinador.mostrarInfoDialog("Operaciones", "Selecciona una operacion");
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(121, 287, 80, 30);
		getContentPane().add(cancelButton);
		cancelButton.addActionListener(e -> {
			coordinador.ocultarVentanaOperacion();
			coordinador.mostrarVentanaPrincipal();
		});

		// Configurar tamaño del JFrame y comportamiento de cierre
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void validarDNIyPoblarComboBox() {
		cuentasComboBox.removeAllItems();
		String dni = dniTextField.getText();
		if (!Validador.validarDNI(dni)) {
			return;
		}
		fetchCuentasCliente(dni);
	}

	public void fetchCuentasCliente(String dni) {
		cuentasDeClienteActual = coordinador.buscarTodasCuentas(dni);
		for (CuentaVO cuentaVO : cuentasDeClienteActual) {
			cuentasComboBox.addItem("N.Cuenta = [" + cuentaVO.getCuCodCuenta() + "] Saldo = " + cuentaVO.getCuSaldo());
		}
	}

	public void fetchCuentasDestinatarios() {
		cuentasDestinatarios = coordinador.buscarTodasCuentas();
		for (CuentaVO cuentaVO : cuentasDestinatarios) {
			destinatarioComboBox
					.addItem("N.Cuenta = [" + cuentaVO.getCuCodCuenta() + "] Saldo = " + cuentaVO.getCuSaldo());
		}
	}

	private Object getObjetoSeleccionadoEnComboBox(ArrayList<?> listaBuscar, JComboBox<String> comboBoxBuscar,
			String nombreCampo) {
		if (listaBuscar.size() <= 0) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. No hay opciones donde elegir.");
			return null;
		}
		int index = comboBoxBuscar.getSelectedIndex();
		if (index < 0 || index >= listaBuscar.size()) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. Seleccionada opcion invalida.");
			return null;
		}
		Object objetoSeleccionado = listaBuscar.get(index);
		if (objetoSeleccionado == null) {
			coordinador.mostrarVentanaError(nombreCampo + " Invalido. No se ha encontrado el objeto en la lista.");
		}
		return objetoSeleccionado;
	}

	private void hacerIngreso() {
		System.out.println("INGRESO");
		CuentaVO cuenta = (CuentaVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) cuentasDeClienteActual,
				cuentasComboBox, "Cuenta");
		try {
			Validador.validarCuenta(cuenta);
		} catch (InputValidationException e) {
			return;
		}

		String cantidadString = cantidadTextField.getText();
		Integer cantidad;
		try {
			cantidad = Integer.parseInt(cantidadString);
			cantidad = Math.abs(cantidad);
			Validador.validarIntPositivo(cantidad);
		} catch (NumberFormatException e) {
			coordinador.mostrarVentanaError("La cantidad debe ser un numero entero positivo.");
			return;
		}
		cuenta.setCuSaldo(cuenta.getCuSaldo() + cantidad);
		// Actualizar Cuenta
		if (!coordinador.actualizarCuenta(cuenta)) {
			return;
		}

		// Crear Transaccion
		TransaccionesVO transaccion = new TransaccionesVO();
		transaccion.setId(0);
		transaccion.setTrCodCuenta(cuenta.getCuCodCuenta());
		transaccion.setTrCantidad(cantidad);

		long currentTimeMillis = System.currentTimeMillis();
		java.util.Date currentDate = new java.util.Date(currentTimeMillis);
		Date sqlDate = new Date(currentDate.getTime());

		transaccion.setTrFechaTransaccion(sqlDate);
		transaccion.setTrTipo("I");
		if (!coordinador.insercionTransaccion(transaccion)) {
			return;
		}
		coordinador.mostrarInfoDialog("Nueva Transaccion Completada", "Completada transaccion INGRESO en cuenta "
				+ cuenta.getCuCodCuenta() + " de " + cantidad + ". Nuevo Saldo: " + cuenta.getCuSaldo());
		actualizarVista();
	}

	private void hacerReintegro() {
		CuentaVO cuenta = (CuentaVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) cuentasDeClienteActual,
				cuentasComboBox, "Cuenta");
		try {
			Validador.validarCuenta(cuenta);
		} catch (InputValidationException e) {
			return;
		}

		String cantidadString = cantidadTextField.getText();
		Integer cantidad;
		try {
			cantidad = Integer.parseInt(cantidadString);
			cantidad = Math.abs(cantidad);
			Validador.validarIntPositivo(cantidad);
		} catch (NumberFormatException e) {
			coordinador.mostrarVentanaError("La cantidad debe ser un numero entero positivo.");
			return;
		}
		Integer total = cuenta.getCuSaldo() - cantidad;
		if (total.intValue() < 0) {
			int decision = coordinador.mostrarConfirmacionDialog("Saldo Insuficiente",
					"Saldo insuficiente. ¿Desea seguir con el reintegro?");
			if (decision == JOptionPane.NO_OPTION || decision == JOptionPane.CANCEL_OPTION) {
				limpiarCajas();
				return;
			}
			total = 0;
		}
		cuenta.setCuSaldo(total);
		// Actualizar Cuenta
		if (!coordinador.actualizarCuenta(cuenta)) {
			return;
		}

		// Crear Transaccion
		TransaccionesVO transaccion = new TransaccionesVO();
		transaccion.setId(0);
		transaccion.setTrCodCuenta(cuenta.getCuCodCuenta());
		transaccion.setTrCantidad(cantidad);

		long currentTimeMillis = System.currentTimeMillis();
		java.util.Date currentDate = new java.util.Date(currentTimeMillis);
		Date sqlDate = new Date(currentDate.getTime());

		transaccion.setTrFechaTransaccion(sqlDate);
		transaccion.setTrTipo("R");
		if (!coordinador.insercionTransaccion(transaccion)) {
			return;
		}
		coordinador.mostrarInfoDialog("Nueva Transaccion Completada", "Completada transaccion REINTEGRO en cuenta "
				+ cuenta.getCuCodCuenta() + " de " + cantidad + ". Nuevo Saldo: " + cuenta.getCuSaldo());
		actualizarVista();
	}

	private void hacerTransferencia() {
		System.out.println("TRANSFERENCIA");
		CuentaVO cuentaOrigen = (CuentaVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) cuentasDeClienteActual,
				cuentasComboBox, "Cuenta");
		try {
			Validador.validarCuenta(cuentaOrigen);
		} catch (InputValidationException e) {
			return;
		}

		String cantidadString = cantidadTextField.getText();
		Integer cantidad;
		try {
			cantidad = Integer.parseInt(cantidadString);
			cantidad = Math.abs(cantidad);
			Validador.validarIntPositivo(cantidad);
		} catch (NumberFormatException e) {
			coordinador.mostrarVentanaError("La cantidad debe ser un numero entero positivo.");
			return;
		}
		int diferencia = cantidad;

		Integer totalOrigen = cuentaOrigen.getCuSaldo() - cantidad;
		if (totalOrigen.intValue() < 0) {
			int decision = coordinador.mostrarConfirmacionDialog("Saldo Insuficiente",
					"Saldo insuficiente. ¿Desea seguir con la transferencia?");
			if (decision == JOptionPane.NO_OPTION || decision == JOptionPane.CANCEL_OPTION) {
				limpiarCajas();
				return;
			}
			diferencia = cantidad - Math.abs(totalOrigen);
			totalOrigen = 0;
		}
		cuentaOrigen.setCuSaldo(totalOrigen);
		// Actualizar Cuenta Destino
		CuentaVO cuentaDestino = (CuentaVO) getObjetoSeleccionadoEnComboBox((ArrayList<?>) cuentasDestinatarios,
				destinatarioComboBox, "Cuenta Destinatario");
		try {
			Validador.validarCuenta(cuentaDestino);
			if(cuentaOrigen.getCuCodCuenta() == cuentaDestino.getCuCodCuenta()) {
				coordinador.mostrarVentanaError("NO PUEDES HACER UNA TRANSFERENCIA A LA MISMA CUENTA DE ORIGEN 🤓");
				return;
			}
		} catch (InputValidationException e) {
			return;
		}
		
		
		// Actualizar Cuenta Origen
		if (!coordinador.actualizarCuenta(cuentaOrigen)) {
			return;
		}
		Integer totalDestino = cuentaDestino.getCuSaldo() + diferencia;

		cuentaDestino.setCuSaldo(totalDestino);
		if (!coordinador.actualizarCuenta(cuentaDestino)) {
			return;
		}
		coordinador.mostrarInfoDialog("Transferencia Completada",
				"Completada TRANSFERENCIA de cuenta " + cuentaOrigen.getCuCodCuenta() + " por " + cantidad
						+ " a cuenta " + cuentaDestino.getCuCodCuenta() + ". Nuevo Saldo origen: "
						+ cuentaOrigen.getCuSaldo() + ". Nuevo Saldo destino: " + cuentaDestino.getCuSaldo());
		actualizarVista();
		fetchCuentasDestinatarios();
	}
	
	private void limpiarCajas() {
		cantidadTextField.setText("");
	}
}
