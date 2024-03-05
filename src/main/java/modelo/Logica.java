package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;

import controlador.exceptions.InputValidationException;
import modelo.conexion.Conexion;
import modelo.dao.cliente.ClienteDAO;
import modelo.dao.cuenta.CuentaDAO;
import modelo.dao.cuenta.CuentasClienteDAO;
import modelo.dao.sucursal.SucursalDAO;
import modelo.dao.transaccion.TransaccionDAO;
import modelo.vo.cliente.ClienteVO;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.cuenta.CuentasclienteVO;
import modelo.vo.sucursal.SucursalesVO;
import modelo.vo.transaccion.TransaccionesVO;
import modelo.validacion.Validador;

public class Logica {

	// OPERACIONES CLIENTE ----

	public void validarCreaCliente(ClienteVO cliente) throws InputValidationException, SQLException {
		Validador.validarCliente(cliente);

		ClienteDAO clienteDAO = new ClienteDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		clienteDAO.crear(con, cliente);

		conexion.desconectar();
		//return clienteInsertado;
	}

	public ClienteVO validarBuscaCliente(String dni) throws InstanceNotFoundException, SQLException {
		ClienteDAO clienteDAO = new ClienteDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		ClienteVO cliente = clienteDAO.buscar(con, dni);

		conexion.desconectar();
		return cliente;
	}

	public List<ClienteVO> validarBuscaTodosClientes() throws SQLException{
		ClienteDAO clienteDAO = new ClienteDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		List<ClienteVO> clientes = clienteDAO.buscarTodosClientes(con);
		
		conexion.desconectar();
		return clientes;
	}
	
	public void validarActualizaCliente(ClienteVO cliente)
			throws InputValidationException, InstanceNotFoundException, SQLException {
		Validador.validarCliente(cliente);

		ClienteDAO clienteDAO = new ClienteDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		clienteDAO.actualizar(con, cliente);

		conexion.desconectar();
	}

	public void validarBorraCliente(String DNI) throws InstanceNotFoundException, SQLException {
		ClienteDAO clienteDAO = new ClienteDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		clienteDAO.borrar(con, DNI);

		conexion.desconectar();
	}

	// OPERACIONES CUENTA -----

	public CuentaVO validarCreaCuenta(CuentaVO cuenta) throws InputValidationException, SQLException {
		Validador.validarCuenta(cuenta);

		CuentaDAO cuentaDAO = new CuentaDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		CuentaVO cuentaInsertada = cuentaDAO.crear(con, cuenta);

		conexion.desconectar();
		return cuentaInsertada;
	}
	
	public CuentaVO validarBuscaCuenta(int codigoCuenta) throws InstanceNotFoundException, SQLException {
		CuentaDAO cuentaDAO = new CuentaDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		CuentaVO cuenta = cuentaDAO.buscar(con, codigoCuenta);

		conexion.desconectar();
		
		return cuenta;
	}

	public List<CuentaVO> validarBuscaTodasCuentas(String dni) throws SQLException{
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		List<CuentaVO> cuentas = cuentaDAO.buscar(con, dni);
		
		conexion.desconectar();
		return cuentas;
	}
	
	public List<CuentaVO> validarBuscaTodasCuentas(Integer codigoSucursal) throws SQLException{
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		List<CuentaVO> cuentas = cuentaDAO.buscarPorSucursal(con, codigoSucursal);
		
		conexion.desconectar();
		return cuentas;
	}
	
	
	public List<CuentaVO> validarBuscaTodasCuentas() throws SQLException{
		CuentaDAO cuentaDAO = new CuentaDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		List<CuentaVO> cuentas = cuentaDAO.buscar(con);
		
		conexion.desconectar();
		return cuentas;
	}
	
	
	public void validarActualizaCuenta(CuentaVO cuenta)
			throws InputValidationException, InstanceNotFoundException, SQLException {
		Validador.validarCuenta(cuenta);

		CuentaDAO cuentaDAO = new CuentaDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		cuentaDAO.actualizar(con, cuenta);

		conexion.desconectar();
	}

	public void validarBorraCuenta(int codigoCuenta) throws InstanceNotFoundException, SQLException {
		CuentaDAO cuentaDAO = new CuentaDAO();

		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();

		cuentaDAO.borrar(con, codigoCuenta);

		conexion.desconectar();
	}

	// OPERACIONES CUENTA-CLIENTE
	
	public void validarCrearCuentaCliente(CuentasclienteVO cuentaCliente) throws InputValidationException, SQLException {
		Validador.validarRelacionCuentaCliente(cuentaCliente);
		
		CuentasClienteDAO cuentasClienteDAO = new CuentasClienteDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		cuentasClienteDAO.crear(con, cuentaCliente);
		
		conexion.desconectar();
	}
	
	public void validarActualizaCuentaCliente(CuentasclienteVO cuentaCliente) throws InputValidationException, InstanceNotFoundException, SQLException {
		Validador.validarRelacionCuentaCliente(cuentaCliente);
		
		CuentasClienteDAO cuentasClienteDAO = new CuentasClienteDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		cuentasClienteDAO.actualizar(con, cuentaCliente);
		
		conexion.desconectar();
	}
	
	public void validarBorraCuentaCliente(Integer codigoCuenta) throws InstanceNotFoundException, SQLException {
		CuentasClienteDAO cuentasClienteDAO = new CuentasClienteDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		cuentasClienteDAO.borrar(con, codigoCuenta);
		
		conexion.desconectar();
	}
	
	// OPERACIONES SUCURSAL ----
	
	public List<SucursalesVO> validarBuscaTodasSucursales() throws SQLException{
		SucursalDAO sucursalDAO = new SucursalDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		List<SucursalesVO> sucursales = sucursalDAO.buscarTodasSucursales(con);
		
		conexion.desconectar();
		return sucursales;
	}
	
	// OPERACIONES TRANSACCION -
	
	public void validarCreaTransaccion(TransaccionesVO transaccion) throws SQLException {
		TransaccionDAO transaccionDAO = new TransaccionDAO();
		
		Conexion conexion = new Conexion();
		Connection con = conexion.getConnection();
		
		transaccionDAO.crear(con, transaccion);
		
		conexion.desconectar();
	}
	
}
