package controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.swing.JOptionPane;

import controlador.exceptions.InputValidationException;
import modelo.Logica;
import modelo.vo.cliente.ClienteVO;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.cuenta.CuentasclienteVO;
import modelo.vo.sucursal.SucursalesVO;
import modelo.vo.transaccion.TransaccionesVO;
import vista.VentanaListado;
import vista.VentanaOperaciones;
import vista.VentanaPrincipal;

public class Coordinador {
	private Logica logica;
	private VentanaPrincipal ventanaPrincipal;
	private VentanaListado ventanaListado;
	private VentanaOperaciones ventanaOperaciones;

	public Logica getLogica() {
		return logica;
	}
	public void setLogica(Logica logica) {
		this.logica = logica;
	}
	public VentanaPrincipal getVentanaPrincipal() {
		return ventanaPrincipal;
	}
	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}
	public VentanaListado getVentanaListado() {
		return ventanaListado;
	}
	public void setVentanaListado(VentanaListado ventanaListado) {
		this.ventanaListado = ventanaListado;
	}
	public VentanaOperaciones getVentanaOperaciones() {
		return ventanaOperaciones;
	}
	public void setVentanaOperacion(VentanaOperaciones ventanaOperaciones) {
		this.ventanaOperaciones = ventanaOperaciones;
	}
	
	//////////////////////////////////////////////////////////

	public void mostrarVentanaPrincipal() {
		ventanaPrincipal.setVisible(true);
	}

	public void ocultarVentanaPrincipal() {
		ventanaPrincipal.setVisible(false);
	}

	public void mostrarVentanaListado() {
		ventanaListado.setVisible(true);
	}

	public void ocultarVentanaListado() {
		ventanaListado.setVisible(false);
	}

	public void mostrarVentanaOperacion() {
		ventanaOperaciones.setVisible(true);
	}

	public void ocultarVentanaOperacion() {
		ventanaOperaciones.setVisible(false);
	}

	public void mostrarVentanaError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarInfoDialog(String tituloVentana, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, tituloVentana,JOptionPane.INFORMATION_MESSAGE );
	}

	public int mostrarConfirmacionDialog(String tituloVentana, String mensaje) {
		return JOptionPane.showConfirmDialog(null, mensaje, tituloVentana,JOptionPane.INFORMATION_MESSAGE );
	}
	
	// Logica ---
	
	// LOGICA DE CLIENTE ----
	
	public void insercionCliente(ClienteVO cliente) {
		try {
			logica.validarCreaCliente(cliente);
		} catch (InputValidationException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void buscarCliente(String dni) {

	}
	
	public List<ClienteVO> buscarTodosClientes(){
		List<ClienteVO> clientes = new ArrayList<ClienteVO>();
		try {
			clientes = logica.validarBuscaTodosClientes();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
		return clientes;
	}

	public void actualizarCliente(ClienteVO cliente) {
		try {
			logica.validarActualizaCliente(cliente);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (InputValidationException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void borrarCliente(String dni) {
		try {
			logica.validarBorraCliente(dni);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// LOGICA DE CUENTA ------
	
	public CuentaVO insercionCuenta(CuentaVO cuenta) {
		CuentaVO cuentaInsertada = null;
		try {
			cuentaInsertada = logica.validarCreaCuenta(cuenta);
		} catch (InputValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuentaInsertada;
	}

	public CuentaVO buscarCuenta(int codigoCuenta) {
		try {
			return logica.validarBuscaCuenta(codigoCuenta);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
		}
		return null;
	}
	
	public List<CuentaVO> buscarTodasCuentas(String dni){
		List<CuentaVO> cuentas = new ArrayList<CuentaVO>();
		
		try {
			cuentas = logica.validarBuscaTodasCuentas(dni);
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
		}
		
		return cuentas;
	}
	
	public List<CuentaVO> buscarTodasCuentas(Integer codigoSucursal){
		List<CuentaVO> cuentas = new ArrayList<CuentaVO>();
		
		try {
			cuentas = logica.validarBuscaTodasCuentas(codigoSucursal);
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
		}
		
		return cuentas;
	}

	public List<CuentaVO> buscarTodasCuentas(){
		List<CuentaVO> cuentas = new ArrayList<CuentaVO>();
		
		try {
			cuentas = logica.validarBuscaTodasCuentas();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
		}
		
		return cuentas;
	}

	
	public boolean actualizarCuenta(CuentaVO cuenta) {
		try {
			logica.validarActualizaCuenta(cuenta);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		} catch (InputValidationException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		}
		return true;
	}

	public void borrarCuenta(int codigoCuenta) {
		try {
			logica.validarBorraCuenta(codigoCuenta);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// LOGICA DE CUENTAS-CLIENTE
	
	public void insercionCuentaCliente(CuentasclienteVO cuentaCliente) {
		try {
			logica.validarCrearCuentaCliente(cuentaCliente);
		} catch (InputValidationException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actualizarCuentaCliente(CuentasclienteVO cuentaCliente) {
		try {
			logica.validarActualizaCuentaCliente(cuentaCliente);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (InputValidationException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean borrarCuentaCliente(Integer codigoCuenta) {
		try {
			logica.validarBorraCuentaCliente(codigoCuenta);
		} catch (InstanceNotFoundException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		}
		return true;
	}
	
	// LOGICA DE SUCURSAL -----
	
	public List<SucursalesVO> buscarTodasSucursales() {
		List<SucursalesVO> sucursales = new ArrayList<SucursalesVO>();
		
		try {
			sucursales = logica.validarBuscaTodasSucursales();
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			e.printStackTrace();
		}
		
		return sucursales;
	}
	
	// LOGICA DE TRANSACCION --
	
	public boolean insercionTransaccion(TransaccionesVO transaccion) {
		try {
			logica.validarCreaTransaccion(transaccion);
		} catch (SQLException e) {
			mostrarVentanaError(e.getMessage());
			return false;
		}
		return true;
	}
	
}
