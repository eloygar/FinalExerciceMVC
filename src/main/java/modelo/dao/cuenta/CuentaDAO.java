package modelo.dao.cuenta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.management.InstanceNotFoundException;

import modelo.vo.cuenta.CuentaVO;

public class CuentaDAO {

	public CuentaVO crear(Connection connection, CuentaVO cuenta) throws SQLException {

		// Crear "queryString"
		String queryString = "INSERT INTO Cuenta (cuCodSucursal, cuFechaCreacion, CuSaldo) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

		// Rellenar "preparedStatment"
		int i = 1;
		preparedStatement.setInt(i++, cuenta.getCuCodSucursal()); // cuCodSucursal
		preparedStatement.setDate(i++, cuenta.getCuFechaCreacion()); // cuFechaCreacion
		preparedStatement.setInt(i++, cuenta.getCuSaldo()); // CuSaldo

		// Ejecutar query
		preparedStatement.executeUpdate();

		// Generar identificador
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		if (!resultSet.next()) {
			throw new SQLException("JDBC driver no genera y devuelve una clave.");
		}

		int codigoCuentaInsertada = resultSet.getInt(1);

		// Devolver CuentaVO
		CuentaVO cuentaInsertada = new CuentaVO();
		cuentaInsertada.setCuCodCuenta(codigoCuentaInsertada);
		cuentaInsertada.setCuCodSucursal(cuenta.getCuCodSucursal());
		cuentaInsertada.setCuFechaCreacion(cuenta.getCuFechaCreacion());
		cuentaInsertada.setCuSaldo(cuenta.getCuSaldo());

		return cuentaInsertada;

	}

	public void actualizar(Connection connection, CuentaVO cuenta) throws InstanceNotFoundException, SQLException {
		String queryString = "UPDATE Cuenta SET cuCodSucursal = ?, cuFechaCreacion = ?, CuSaldo = ? WHERE cuCodCuenta = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, cuenta.getCuCodSucursal());
			preparedStatement.setDate(i++, cuenta.getCuFechaCreacion());
			preparedStatement.setInt(i++, cuenta.getCuSaldo());
			preparedStatement.setInt(i++, cuenta.getCuCodCuenta());

			int rowsUpdated = preparedStatement.executeUpdate();

			if (rowsUpdated == 0) {
				throw new InstanceNotFoundException("Cuenta not found with ID: " + cuenta.getCuCodCuenta());
			}
		}
	}

	public CuentaVO buscar(Connection connection, Integer codigoCuenta) throws InstanceNotFoundException, SQLException {
		String queryString = "SELECT * FROM Cuenta WHERE cuCodCuenta = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			preparedStatement.setInt(1, codigoCuenta);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int i = 1;

					int cuCodCuenta = resultSet.getInt(i++);
					int cuCodSucursal = resultSet.getInt(i++);
					Date cuFechaCreacion = resultSet.getDate(i++);
					int cuSaldo = resultSet.getInt(i++);
					CuentaVO cuenta = new CuentaVO();
					cuenta.setCuCodCuenta(cuCodCuenta);
					cuenta.setCuCodSucursal(cuCodSucursal);
					cuenta.setCuFechaCreacion(cuFechaCreacion);
					cuenta.setCuSaldo(cuSaldo);

					return cuenta;
				} else {
					throw new InstanceNotFoundException("Cuenta not found with ID: " + codigoCuenta);
				}
			}
		}
	}

	public List<CuentaVO> buscar(Connection connection, String ccDNI) throws SQLException {
		String queryStr = "SELECT Cuenta.* " + "FROM Cliente "
				+ "JOIN CuentasCliente ON Cliente.clDni = CuentasCliente.ccDNI "
				+ "JOIN Cuenta ON CuentasCliente.ccCodCuenta = Cuenta.cuCodCuenta " + "WHERE Cliente.clDni = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {
			preparedStatement.setString(1, ccDNI);

			// Execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			List<CuentaVO> list = new ArrayList<>();

			while (resultSet.next()) {
				int i = 1;

				int cuCodCuenta = resultSet.getInt(i++);
				int cuCodSucursal = resultSet.getInt(i++);
				Date cuFechaCreacion = resultSet.getDate(i++);
				int cuSaldo = resultSet.getInt(i++);

				CuentaVO cuenta = new CuentaVO();
				cuenta.setCuCodCuenta(cuCodCuenta);
				cuenta.setCuCodSucursal(cuCodSucursal);
				cuenta.setCuFechaCreacion(cuFechaCreacion);
				cuenta.setCuSaldo(cuSaldo);

				list.add(cuenta);
			}

			return list;

		}

	}
	
	public List<CuentaVO> buscarPorSucursal(Connection connection, Integer codigoSucursal) throws SQLException {
        String queryString = "SELECT * FROM Cuenta WHERE cuCodSucursal = ?";
        
        List<CuentaVO> cuentas = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setInt(1, codigoSucursal);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CuentaVO cuenta = new CuentaVO();
                    cuenta.setCuCodCuenta(resultSet.getInt("cuCodCuenta"));
                    cuenta.setCuCodSucursal(resultSet.getInt("cuCodSucursal"));
                    cuenta.setCuFechaCreacion(resultSet.getDate("cuFechaCreacion"));
                    cuenta.setCuSaldo(resultSet.getInt("CuSaldo"));
                    
                    cuentas.add(cuenta);
                }
            }
        }
        
        return cuentas;
	}


	public List<CuentaVO> buscar(Connection connection) throws SQLException {
		String queryStr = "SELECT * FROM Cuenta";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryStr)) {

			// Execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			List<CuentaVO> list = new ArrayList<>();

			while (resultSet.next()) {
				int i = 1;

				int cuCodCuenta = resultSet.getInt(i++);
				int cuCodSucursal = resultSet.getInt(i++);
				Date cuFechaCreacion = resultSet.getDate(i++);
				int cuSaldo = resultSet.getInt(i++);

				CuentaVO cuenta = new CuentaVO();
				cuenta.setCuCodCuenta(cuCodCuenta);
				cuenta.setCuCodSucursal(cuCodSucursal);
				cuenta.setCuFechaCreacion(cuFechaCreacion);
				cuenta.setCuSaldo(cuSaldo);

				list.add(cuenta);
			}

			return list;

		}

	}

	
	public void borrar(Connection connection, Integer cuCodCuenta) throws InstanceNotFoundException, SQLException {
		String queryString = "DELETE FROM Cuenta WHERE cuCodCuenta = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			preparedStatement.setInt(1, cuCodCuenta);

			int rowsDeleted = preparedStatement.executeUpdate();

			if (rowsDeleted == 0) {
				throw new InstanceNotFoundException("Cuenta not found with ID: " + cuCodCuenta);
			}
		}
	}
}
