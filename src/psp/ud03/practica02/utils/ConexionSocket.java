package psp.ud03.practica02.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexionSocket implements Conexion {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public ConexionSocket(Socket socket) {
		this.socket = socket;
		try {
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void enviar(String mensaje) {

		// Si el socket está conectado
		if (socket.isConnected()) {

			try {
				out.writeUTF(mensaje);
				out.flush();
			} catch (IOException e) {
				// En caso de cualquier error de comunicaciones
				// Lanzamos una excepción hacia arriba
				throw new ConexionException(e);
			}

		} else {
			// Si el socket no está conectado, lanzamos una excepción
			throw new ConexionException("Socket no conectado");
		}
	}

	@Override
	public String recibir() {

		// Si el socket está conectado
		if (socket.isConnected()) {

			// Intenta leer del socket (vamos a usar un Reader porque usamos texto)
			// Si se recibieran datos binarios habría que usar directamente el stream
			try {

				String resultado = in.readUTF();
				// Devolvemos el mensaje enviado por el servidor
				return resultado;

			} catch (Exception e) {
				// Si hay algún error con la conexión lanzamos nuestra excepcion
				throw new ConexionException(e);
			}
		} else {
			// Si el socket no está conectado lanza una excepcion
			throw new ConexionException("Socket no conectado");
		}
	}

	@Override
	public void enviarBytes(byte[] contenidoFicheroBytes) {

		if (socket.isConnected()) {

			try {
				out.writeInt(contenidoFicheroBytes.length);
				// Enviamos el array que queremos que reciban
				out.write(contenidoFicheroBytes);
				out.flush();
			} catch (IOException e) {
				throw new ConexionException(e);
			}

		} else {
			throw new ConexionException("Socket no conectado");
		}
	}

	@Override
	public byte[] recibirBytes() {

		// Si el socket está conectado
		if (socket.isConnected()) {

			try {

				// Leemos la longitud del array que nos ha enviado el out
				int longitud = in.readInt();
				// Leemos los bytes obtenidos y los introducimos en el array
				byte[] buffer = new byte[longitud];
				in.readFully(buffer, 0, buffer.length);
				return buffer;
			} catch (Exception e) {
				// Si hay algún error con la conexión lanzamos nuestra excepcion
				throw new ConexionException(e);
			}

		} else {
			// Si el socket no está conectado lanza una excepcion
			throw new ConexionException("Socket no conectado");
		}
	}

	@Override
	public void cerrar() {

		// Lo intenta cerrar. Si no se pueda no pasa nada
		try {
			socket.close();
		} catch (IOException e) {
		}

	}

}
