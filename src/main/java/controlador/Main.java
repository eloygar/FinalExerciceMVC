package controlador;

import java.sql.Connection;

import modelo.Logica;
import vista.VentanaListado;
import vista.VentanaOperaciones;
import vista.VentanaPrincipal;


public class Main {
	
	VentanaPrincipal _ventanaPrincipal;
	VentanaListado _ventanaListado;
	VentanaOperaciones _ventanaOperaciones;
	Coordinador _coordinador;
	Logica _logica;

	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
	}
	
	
	private void initialize() {
		
		// Instanciamos las clases
		_logica = new Logica();
		_coordinador = new Coordinador();
		_coordinador.setLogica(_logica);
		
		_ventanaPrincipal = new VentanaPrincipal(_coordinador);
		_ventanaListado = new VentanaListado(_coordinador);
		_ventanaOperaciones = new VentanaOperaciones(_coordinador);
		
		// Pasasmos las referencias al coordinador
		_coordinador.setVentanaPrincipal(_ventanaPrincipal);
		_coordinador.setVentanaOperacion(_ventanaOperaciones);
		_coordinador.setVentanaListado(_ventanaListado);
		
		// Iniciamos la vista principal
		_coordinador.mostrarVentanaPrincipal();
		//_coordinador.mostrarVentanaListado();
		//_coordinador.mostrarVentanaOperacion();
	}
}
