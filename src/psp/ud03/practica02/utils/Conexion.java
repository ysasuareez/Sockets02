package psp.ud03.practica02.utils;

public interface Conexion {
	
	/**
	 * Método que envia mediante DataOutputStream un String
	 * @param mensaje, String a enviar
	 */
	public void enviar(String mensaje);

	/**
	 * Método que recibe mediante DataInputStream un String
	 * @return String recibido 
	 */
	public String recibir();

	/**
	 * Método que envia un array de bytes recibido mediante un DataOutputStream
	 * @param bytes
	 */
	public void enviarBytes(byte[] bytes);

	/**
	 * Método que devuelve un array de bytes con el recibido mediante DataInputStream
	 * @param buffer, array de bytes a rellenar
	 * @return array de bytes relleno
	 */
	public byte[] recibirBytes();
	
	/**
	 * Cierra el socket
	 */
	public void cerrar();
	
}
