package modelo.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.management.InstanceNotFoundException;
import modelo.vo.cliente.ClienteVO;

public class ClienteDAO {

	public ClienteVO crear(Connection connection, ClienteVO cliente) throws SQLException {

		// Crear "queryString"
		String queryString = "INSERT INTO Cliente (clDni, clNombre, clApellido, clTelefono) VALUES (?, ?, ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){			
			// Rellenar "preparedStatment"
			int i = 1;
			preparedStatement.setString(i++, cliente.getClDni()); // clDni
			preparedStatement.setString(i++, cliente.getClNombre()); // clNombre
			preparedStatement.setString(i++, cliente.getClApellido()); // clApellido
			preparedStatement.setInt(i++, cliente.getClTelefono()); // clTelefono
			
			// Ejecutar query
			preparedStatement.executeUpdate();
			
			// Devolver ClienteVO
			ClienteVO clienteInsertado = new ClienteVO();
			clienteInsertado.setClDni(cliente.getClDni());
			clienteInsertado.setClNombre(cliente.getClNombre());
			clienteInsertado.setClApellido(cliente.getClApellido());
			clienteInsertado.setClTelefono(cliente.getClTelefono());
			
			return clienteInsertado;
		}


	}

	public void actualizar(Connection connection, ClienteVO cliente) throws InstanceNotFoundException, SQLException {
		String queryString = "UPDATE Cliente SET clNombre = ?, clApellido = ?, clTelefono = ? WHERE clDni = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, cliente.getClNombre());
			preparedStatement.setString(i++, cliente.getClApellido());
			preparedStatement.setInt(i++, cliente.getClTelefono());
			preparedStatement.setString(i++, cliente.getClDni());

			int rowsUpdated = preparedStatement.executeUpdate();

			if (rowsUpdated == 0) {
				throw new InstanceNotFoundException("Cliente not found with DNI: " + cliente.getClDni());
			}
		}
	}

	public ClienteVO buscar(Connection connection, String dni) throws InstanceNotFoundException, SQLException {
		String queryString = "SELECT * FROM Cliente WHERE clDni = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			preparedStatement.setString(1, dni);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int i = 1;
					String clDni = resultSet.getString(i++);
					String clNombre = resultSet.getString(i++);
					String clApellido = resultSet.getString(i++);
					Integer clTelefono = resultSet.getInt(i++);

					ClienteVO cliente = new ClienteVO();
					cliente.setClDni(clDni);
					cliente.setClNombre(clNombre);
					cliente.setClApellido(clApellido);
					cliente.setClTelefono(clTelefono);

					return cliente;
				} else {
					throw new InstanceNotFoundException("Cliente not found with DNI: " + dni);
				}
			}
		}
	}

	public List<ClienteVO> buscarTodosClientes(Connection connection) throws SQLException {
		String queryString = "SELECT Cliente.* FROM Cliente ";

		List<ClienteVO> list = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int i = 1;
					String clDni = resultSet.getString(i++);
					String clNombre = resultSet.getString(i++);
					String clApellido = resultSet.getString(i++);
					Integer clTelefono = resultSet.getInt(i++);

					ClienteVO cliente = new ClienteVO();
					cliente.setClDni(clDni);
					cliente.setClNombre(clNombre);
					cliente.setClApellido(clApellido);
					cliente.setClTelefono(clTelefono);

					list.add(cliente);
				}
			}
		}

		return list;
	}

	public void borrar(Connection connection, String ccDNI) throws InstanceNotFoundException, SQLException {
		String queryString = "DELETE FROM Cliente WHERE clDni = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			preparedStatement.setString(1, ccDNI);

			int rowsDeleted = preparedStatement.executeUpdate();

			if (rowsDeleted == 0) {
				throw new InstanceNotFoundException("Client not found with DNI: " + ccDNI);
			}
		}
	}

}
