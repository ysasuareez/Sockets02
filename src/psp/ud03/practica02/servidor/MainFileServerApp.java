package psp.ud03.practica02.servidor;

import psp.ud03.practica02.utils.Conexion;

public class MainFileServerApp {

	private static final int PUERTO = 4321;

	public static void main(String[] args) {
		
		// Creamos el servidor con el puerto
		Server servidor = new Server(PUERTO);

		// Esperamos conexiones una detrás de otra (Si se produce un error, se termina)
		Conexion conexion = null;		
		while ((conexion = servidor.esperarConexion()) != null) {			
			// Creamos un hilo para procesar los archivos de la nueva conexion
			Worker worker = new Worker(conexion);
			worker.start();
		}

	}
	

}
