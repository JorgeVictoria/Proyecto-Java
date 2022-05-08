package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class BaseDatos {
	
	//variables para la conexion a la BBD
	private static String url = "jdbc:mariadb://localhost:3306/fontaneria"; 
	private static String user = "root";									
    private static String password = "";									
    private static Connection connection = null;
	
	/**
	 * metodo que realiza la conexion a la BBDD
	 * @return true/false : en funcion de si hemos conectado o no
	 * @throws SQLException
	 */
	public static boolean conectarBBDD() throws SQLException {
		//intentamos la conexion
		connection = DriverManager.getConnection(url, user, password);
		//en funcion de si conectamos o no, devolvemos true or false
		if(connection!=null) return true;
		else return false;
	}
	
	/**
	 * metodo con la sentencia sql para poder insertar datos del cliente en la BBDD
	 * @return 
	 * @throws SQLException
	 */
	public static boolean insertar(String sql) throws SQLException {
		
		//variables locales
		//creamos el statement para poder realizar la consulta
		Statement sentencia = connection.createStatement();
		boolean insertado = true;
				
		//insertamos los clientes
		//para controlar el duplicado de la primary key, lo encerramos en un try catch
		try {
			
			//intentamos realizar la ejecución de la sentencia
			sentencia.executeUpdate(sql);
			
			//excepciones por si el campo dni está repetido
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("No se ha podido ejecutar la linea: " + sql);
			insertado = false;
		}
		
		//cerramos la conexion
		connection.close();
		
		return insertado;
		
	}
	
	public static ResultSet buscar(String sql) throws SQLException {
		
		//creamos el statement para poder realizar la consulta
		Statement sentencia = connection.createStatement();
		
		//recogemos los datos de la consulta
		return sentencia.executeQuery(sql);
		
	}
	
	public static PreparedStatement modificar(String sql) throws SQLException {
		
		//creamos el statement para poder realizar la consulta
		PreparedStatement sentencia = (PreparedStatement) connection.prepareStatement(sql);
		
		//devolvemos la sentencia
		return sentencia;
		
	}

	public static void borrar(String sql) throws SQLException {
		
		//creamos el statement para poder realizar la consulta
		PreparedStatement sentencia = (PreparedStatement) connection.prepareStatement(sql);
		
		//ejecutamos la sentencia
		sentencia.execute();
		
	}
	
	

}
