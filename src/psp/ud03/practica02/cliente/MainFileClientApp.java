package psp.ud03.practica02.cliente;

import java.io.File;
import java.util.Scanner;

public class MainFileClientApp {

	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 4321;

	public static void main(String[] args) {

		// Creamos las variables de la ruta que indicará el cliente mediante el escaner
		String rutaArchivo;
		Scanner sc = new Scanner(System.in);

		// Creamos el objeto cliente con su host y su puerto
		Cliente cliente = new Cliente(DEFAULT_HOST, DEFAULT_PORT);

		// Recogemos la ruta indicada por consola
		System.out.println("Introduzca la ruta del archivo: (vacio para acabar)");
		rutaArchivo = sc.nextLine();

		// Si se ha indicado una ruta el cliente la envia para que el server haga su
		// trabajo
		if (rutaArchivo.length() > 0) {
			// Lo envía
			cliente.enviar(rutaArchivo);

			// Creamos el nuevo archivo donde queremos que se guarde la información
			File nuevoFichero = new File("folderClient/" + (new File(rutaArchivo)).getName());
			System.out.println("\nNuevo archivo: " + nuevoFichero);

			cliente.recibir(nuevoFichero);

		}

		// cerramos para terminar
		cliente.cerrar();
		sc.close();
		System.out.println("Terminando...");

	}
}
