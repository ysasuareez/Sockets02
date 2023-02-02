package psp.ud03.practica02.servidor;

import java.net.ServerSocket;

import psp.ud03.practica02.utils.Conexion;
import psp.ud03.practica02.utils.ConexionException;
import psp.ud03.practica02.utils.ConexionSocket;

public class Server {

	private ServerSocket serverSocket;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			throw new ConexionException(e);
		}
	}

	public Conexion esperarConexion() {
		// Intentamos aceptar una conexion. Si no hay ninguna en cola espera
		try {
			return new ConexionSocket(serverSocket.accept());
		} catch (Exception e) {
			// Error. Devolvemos no ok
			return null;
		}
	}
}
