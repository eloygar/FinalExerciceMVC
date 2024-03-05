package modelo.dao.sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;

import modelo.vo.sucursal.SucursalesVO;

public class SucursalDAO {

	public SucursalesVO crear(Connection connection, SucursalesVO sucursal) throws SQLException {
		System.out.println(getClass().getName() + " crear no implementado." );
		return null;
	}

	public void actualizar(Connection connection, SucursalesVO sucursal) throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " actualizar no implementado." );
		return;
	}

	public SucursalesVO buscar(Connection connection, Integer codigoSucursal) throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " buscar no implementado." );
		return null;
	}

	public List<SucursalesVO> buscarPorCiudad(Connection connection, String ciudad) throws SQLException {
		System.out.println(getClass().getName() + " buscarPorCiudad no implementado." );

		return null;
	}

	public void borrar(Connection connection, Integer codigoSucursal) throws InstanceNotFoundException, SQLException {
		System.out.println(getClass().getName() + " borrar no implementado." );
		return;
	}
	
	public List<SucursalesVO> buscarTodasSucursales(Connection connection) throws SQLException{
		String queryString = "SELECT * FROM Sucursales";
        
        List<SucursalesVO> sucursales = new ArrayList<SucursalesVO>();
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                SucursalesVO sucursal = new SucursalesVO();
                sucursal.setSuCodSucursal(resultSet.getInt("suCodSucursal"));
                sucursal.setSuCiudad(resultSet.getString("suCiudad"));
                sucursal.setSuActivo(resultSet.getBigDecimal("suActivo"));
                
                sucursales.add(sucursal);
            }
        }
        
        return sucursales;
		
	}

}
