package modelo.dao.cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.management.InstanceNotFoundException;

import modelo.vo.cuenta.CuentasclienteVO;

public class CuentasClienteDAO {

	public CuentasclienteVO crear(Connection connection, CuentasclienteVO cuentaCliente) throws SQLException {
		String checkClienteQuery = "SELECT 1 FROM Cliente WHERE clDni = ?";
		String checkCuentaQuery = "SELECT 1 FROM Cuenta WHERE cuCodCuenta = ?";
		String insertQuery = "INSERT INTO CuentasCliente (ccDNI, ccCodCuenta) VALUES (?, ?)";

		try (PreparedStatement checkClienteStatement = connection.prepareStatement(checkClienteQuery);
				PreparedStatement checkCuentaStatement = connection.prepareStatement(checkCuentaQuery);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			checkClienteStatement.setString(1, cuentaCliente.getCcDni());
			try (ResultSet clienteResultSet = checkClienteStatement.executeQuery()) {
				if (!clienteResultSet.next()) {
					throw new SQLException("Cliente with ccDNI " + cuentaCliente.getCcDni() + " does not exist.");
				}
			}

			checkCuentaStatement.setInt(1, cuentaCliente.getCcCodCuenta());
			try (ResultSet cuentaResultSet = checkCuentaStatement.executeQuery()) {
				if (!cuentaResultSet.next()) {
					throw new SQLException(
							"Cuenta with ccCodCuenta " + cuentaCliente.getCcCodCuenta() + " does not exist.");
				}
			}

			insertStatement.setString(1, cuentaCliente.getCcDni());
			insertStatement.setInt(2, cuentaCliente.getCcCodCuenta());
			int rowsInserted = insertStatement.executeUpdate();

			if (rowsInserted == 0) {
				throw new SQLException("Creating cuentaCliente failed, no rows affected.");
			}
		}

		return cuentaCliente;
	}

	public void actualizar(Connection connection, CuentasclienteVO cuentaCliente)
			throws InstanceNotFoundException, SQLException {
		String updateQuery = "UPDATE CuentasCliente SET ccCodCuenta = ? WHERE ccDNI = ?";

		try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
			updateStatement.setInt(1, cuentaCliente.getCcCodCuenta());
			updateStatement.setString(2, cuentaCliente.getCcDni());

			int rowsUpdated = updateStatement.executeUpdate();

			if (rowsUpdated == 0) {
				throw new InstanceNotFoundException(
						"CuentasCliente record with ccDNI " + cuentaCliente.getCcDni() + " not found.");
			}
		}
	}

	public List<CuentasclienteVO> buscarPorCuenta(Connection connection, Integer codigoCuenta) throws SQLException {
		System.out.println(getClass().getName() + " buscarPorCuenta no implementado.");
		return null;
	}

	public List<CuentasclienteVO> buscarPorDNI(Connection connection, String dni) throws SQLException {
		System.out.println(getClass().getName() + " buscarPorDNI no implementado.");
		return null;
	}

	public void borrar(Connection connection, Integer codigoCuenta) throws InstanceNotFoundException, SQLException {
		String deleteQuery = "DELETE FROM CuentasCliente WHERE ccCodCuenta = ?";

		try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
			deleteStatement.setInt(1, codigoCuenta);

			int rowsDeleted = deleteStatement.executeUpdate();

			if (rowsDeleted == 0) {
				throw new InstanceNotFoundException("No records found with codigoCuenta " + codigoCuenta);
			}
		}
	}

}
