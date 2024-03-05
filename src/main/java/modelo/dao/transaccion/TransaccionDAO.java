package modelo.dao.transaccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.management.InstanceNotFoundException;

import modelo.vo.transaccion.TransaccionesVO;

public class TransaccionDAO {

	public void crear(Connection connection, TransaccionesVO transaccion) throws SQLException {
		String insertQuery = "INSERT INTO Transacciones (trCodCuenta, trFechaTransaccion, trTipo, trCantidad) VALUES (?, ?, ?, ?)";

		try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
			insertStatement.setInt(1, transaccion.getTrCodCuenta());
			insertStatement.setDate(2, transaccion.getTrFechaTransaccion());
			insertStatement.setString(3, transaccion.getTrTipo());
			insertStatement.setInt(4, transaccion.getTrCantidad());

			int rowsInserted = insertStatement.executeUpdate();

			if (rowsInserted == 0) {
				throw new SQLException("Creating transaction failed, no rows affected.");
			}
		}
	}

	public void actualizar(Connection connection, TransaccionesVO transaccion)
			throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " actualizar no implementado.");
		return;
	}

	public TransaccionesVO buscar(Connection connection, Integer idTransaccion)
			throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " buscar no implementado.");
		return null;
	}

	public List<TransaccionesVO> buscarPorCuenta(Connection connection, Integer codigoCuenta) throws SQLException {
		System.out.println(getClass().getName() + " buscarPorCuenta no implementado.");
		return null;
	}

	public void borrar(Connection connection, Integer idTransaccion) throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " borrar no implementado.");
		return;
	}

}
