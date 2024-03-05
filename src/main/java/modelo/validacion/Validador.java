package modelo.validacion;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Random;

import controlador.exceptions.InputValidationException;
import modelo.vo.cliente.ClienteVO;
import modelo.vo.cuenta.CuentaVO;
import modelo.vo.cuenta.CuentasclienteVO;
import modelo.vo.sucursal.SucursalesVO;
import modelo.vo.transaccion.TransaccionesVO;

public class Validador {

	public static boolean validarDNI(String dni) {
		if (!validarString(dni)) {
			return false;
		}
		if (dni.length() != 9) {
			return false;
		}
		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(dni.charAt(i))) {
				return false;
			}
		}
		char letra = dni.charAt(8);
		if (!Character.isLetter(letra)) {
			return false;
		}

		return true;
	}

	public static boolean validarString(String string) {
		if (string == null) {
			return false;
		}
		if (string.isBlank()) {
			return false;
		}
		return true;
	}

	public static boolean validarIntPositivo(Integer numero) {
		if (numero == null) {
			return false;
		}
		if (numero.intValue() < 0) {
			return false;
		}
		return true;
	}

	public static boolean validarDateSQL(Date fecha) {
		if (fecha == null) {
			return false;
		}
		return true;
	}

	public static boolean validarBigDecimalPositivo(BigDecimal numero) {
		if (numero == null) {
			return false;
		}
		if (numero.intValue() < 0) {
			return false;
		}
		return true;
	}

	public static boolean validarTipoTransaccion(String tipo) {
		if (!validarString(tipo)) {
			return false;
		}
		if (tipo.charAt(0) != 'I' || tipo.charAt(0) != 'R') {
			return false;
		}
		return true;
	}

	public static void validarCliente(ClienteVO cliente) throws InputValidationException {
		if (cliente == null) {
			throw new InputValidationException("Cliente [" + cliente + "] Invalido");
		}
		String DNI = cliente.getClDni();
		if (!validarDNI(DNI)) {
			throw new InputValidationException("DNI [" + DNI + "] Invalido");
		}
		String apellido = cliente.getClApellido();
		if (!validarString(apellido)) {
			throw new InputValidationException("String Apellido [" + apellido + "] Invalido");
		}
		String nombre = cliente.getClNombre();
		if (!validarString(nombre)) {
			throw new InputValidationException("String Nombre [" + nombre + "] Invalido");
		}
		Integer telefono = cliente.getClTelefono();
		if (!validarIntPositivo(telefono)) {
			throw new InputValidationException("Integer Telefono [" + telefono + "] Invalido");
		}
	}

	public static void validarCuenta(CuentaVO cuenta) throws InputValidationException {
		if (cuenta == null) {
			throw new InputValidationException("Cuenta [" + cuenta + "] Invalido");
		}
		int codigoCuenta = cuenta.getCuCodCuenta();
		if (!validarIntPositivo(codigoCuenta)) {
			throw new InputValidationException("int Codigo Cuenta [" + codigoCuenta + "] Invalido");
		}
		Integer codigoSucursal = cuenta.getCuCodSucursal();
		if (!validarIntPositivo(codigoSucursal)) {
			throw new InputValidationException("Integer Codigo Sucursal [" + codigoSucursal + "] Invalido");
		}
		Date fechaCreacion = cuenta.getCuFechaCreacion();
		if (!validarDateSQL(fechaCreacion)) {
			throw new InputValidationException("DateSQL Fecha Creacion Cuenta [" + fechaCreacion + "] Invalido");
		}
		Integer saldo = cuenta.getCuSaldo();
		if (!validarIntPositivo(saldo)) {
			throw new InputValidationException("Integer Saldo [" + saldo + "] Invalido");
		}
	}

	public static void validarSucursal(SucursalesVO sucursal) throws InputValidationException {
		if(sucursal == null) {
			throw new InputValidationException("Sucursal [" + sucursal + "] Invalido");
		}
		BigDecimal activo = sucursal.getSuActivo();
		if (!validarBigDecimalPositivo(activo)) {
			throw new InputValidationException("BigDecimal Activo Sucursal [" + activo + "] Invalido");
		}
		String ciudad = sucursal.getSuCiudad();
		if (!validarString(ciudad)) {
			throw new InputValidationException("String ciudad [" + ciudad + "] Invalido");
		}
		Integer codigoSucursal = sucursal.getSuCodSucursal();
		if (!validarIntPositivo(codigoSucursal)) {
			throw new InputValidationException("Integer codigoSucursal [" + ciudad + "] Invalido");
		}
	}

	public static void validarTransaccion(TransaccionesVO transaccion) throws InputValidationException {
		if(transaccion == null) {
			throw new InputValidationException("Transaccion [" + transaccion + "] Invalido");
		}
		Integer idTransaccion = transaccion.getId();
		if (!validarIntPositivo(idTransaccion)) {
			throw new InputValidationException("int ID transaccion [" + idTransaccion + "] Invalido");
		}
		Integer cantidadTransaccion = transaccion.getTrCantidad();
		if (!validarIntPositivo(cantidadTransaccion)) {
			throw new InputValidationException("int Cantidad Transaccion [" + cantidadTransaccion + "] Invalido");
		}
		Integer codigoCuenta = transaccion.getTrCodCuenta();
		if (!validarIntPositivo(codigoCuenta)) {
			throw new InputValidationException("int Codigo Cuenta [" + codigoCuenta + "] Invalido");
		}
		Date fechaTransaccion = transaccion.getTrFechaTransaccion();
		if (!validarDateSQL(fechaTransaccion)) {
			throw new InputValidationException("fecha Transaccion [" + fechaTransaccion + "] Invalido");
		}
		String tipoTransaccion = transaccion.getTrTipo();
		if (!validarTipoTransaccion(tipoTransaccion)) {
			throw new InputValidationException("tipo Transaccion [" + fechaTransaccion + "] Invalido");
		}
	}

	public static void validarRelacionCuentaCliente(CuentasclienteVO cuentaCliente) throws InputValidationException {
		if(cuentaCliente == null) {
			throw new InputValidationException("CuentaCliente [" + cuentaCliente + "] Invalido");
		}
		Integer codigoCuenta = cuentaCliente.getCcCodCuenta();
		if (!validarIntPositivo(codigoCuenta)) {
			throw new InputValidationException("int Codigo Cuenta [" + codigoCuenta + "] Invalido");
		}
		String dni = cuentaCliente.getCcDni();
		if (!validarDNI(dni)) {
			throw new InputValidationException("DNI [" + dni + "] Invalido");
		}
	}

	public static String generarDNI() {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();

		// Generar 8 dígitos aleatorios
		for (int i = 0; i < 8; i++) {
			sb.append(rand.nextInt(10));
		}

		// Generar una letra aleatoria (mayúscula) para el último caracter
		char letra = (char) (rand.nextInt(26) + 'A');
		sb.append(letra);

		return sb.toString();
	}

}
