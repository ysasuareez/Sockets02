package psp.ud03.practica02.servidor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import psp.ud03.practica02.utils.Conexion;

public class Worker extends Thread {

	private Conexion conexion;

	public Worker(Conexion conexion) {
		this.conexion = conexion;

	}

	@Override
	public void run() {

		String peticion = null;

		// Recibe la ruta del archivo a procesar y la impirme para comprobar por consola
		peticion = conexion.recibir();
		System.out.println("Peticion: " + peticion);

		// Creamos con esa ruta el fichero para comprobar si dicha ruta existe y
		// a su vez es un fichero y no un directorio
		File f = new File(peticion);

		// Si cumple los requisitos envia un array de bytes de OK para que el cliente
		// sepa si debe o no crear el nuevo fichero. Enviamos tambien los bytes del
		// fichero
		if (f.exists() && !f.isDirectory()) {
			byte[] respuesta = "OK\n\r".getBytes();
			conexion.enviarBytes(concat(respuesta, ficheroBytes(f)));
			System.out.println("\nEnviando...\n       " + ficheroBytes(f));
		} else {
			conexion.enviarBytes("KO\n\r".getBytes());
		}

	}

	/**
	 * Metodo que devuelve los bytes de un fichero
	 * 
	 * @param f, fichero al que sacar el array de bytes
	 * @return array de bytes a devolver
	 */
	public byte[] ficheroBytes(File f) {
		try {
			return Files.readAllBytes(f.toPath());
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * M?todo que concatena dos arrays pasados por parametros
	 * 
	 * @param first
	 * @param second
	 * @return la suma de los dos
	 */
	public static byte[] concat(byte[] first, byte[] second) {

		if (first.length > 0 && second.length > 0) {
			byte[] result = new byte[first.length + second.length];
			System.arraycopy(first, 0, result, 0, first.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		} else {
			return null;
		}

	}

}
