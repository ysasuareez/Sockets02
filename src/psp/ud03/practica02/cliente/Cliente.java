package psp.ud03.practica02.cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import psp.ud03.practica02.utils.Conexion;
import psp.ud03.practica02.utils.ConexionException;
import psp.ud03.practica02.utils.ConexionSocket;

public class Cliente {

	private Conexion conexion;

	public Cliente(String host, int port) {
		// Creamos y conectamos el socket (no se almacenan los parametros)
		try {
			// Se intenta crear (y conectar)
			conexion = new ConexionSocket(new Socket(host, port));
		} catch (Exception e) {
			// Si no se puede conectar, lanzamos una excepcion
			throw new ConexionException(e);
		}

	}

	// Cliente envia mediante su conexion
	public void enviar(String msg) {
		conexion.enviar(msg);
	}

	/**
	 * El metodo que creara un fichero rellenándolo con la información que manda el
	 * server
	 * 
	 * @param nuevoFichero, con la ruta del nuevo fichero
	 * @return FileOutputStream
	 */
	public FileOutputStream recibir(File nuevoFichero) {
		FileOutputStream fos = null;

		try {

			// Recibimos el array
			byte[] buffer = conexion.recibirBytes();

			// Traducimos la respuesta para comprobar si el archivo se ha encontrado
			// o no, sabiendo que el OK\n\r estará al principio
			String respuesta = new String(buffer, StandardCharsets.UTF_8);
			// Si es OK comenzamos
			if (respuesta.startsWith("OK\n\r")) {

				// Creamos el FileOutputStream para poder introducir los bytes recibidos
				fos = new FileOutputStream(nuevoFichero);

				// Traducimos lo recibido para visualizarlo en pantalla
				respuesta = new String(buffer, StandardCharsets.UTF_8);
				System.out.println("\nRecibido:\n" + buffer);
				System.out.println("\nTraduccion:\n" + respuesta);
				System.out.println("\nCreando...");

				// Escribimos en el fos obviando los 4 primeros bytes que pertenecen al OK\n\r
				fos.write(buffer, 4, buffer.length - 4);

				// Cerramos el FileOutputStream
				fos.flush();
				fos.close();
				System.out.println("\nArchivo creado con éxito.");

				// Cerramos la conexion
				conexion.cerrar();
			} else {
				// Lanza error si la respuesta del servidor es distinta a Ok\n\r que en nuestro
				// caso sera ERR O KO
				System.err.print("\nError al crear el archivo.");

				// Cerramos la conexion
				conexion.cerrar();
			}

			return fos;

		} catch (IOException e) {
			// Si el archivo no existe devolverá por pantalla el siguiente mensaje
			System.err.print("\nError al recibir el archivo.");
			// Cerramos la conexion
			conexion.cerrar();
			return null;
		}

	}

	/**
	 * Cierra conexion
	 */
	public void cerrar() {
		conexion.cerrar();
	}

}
