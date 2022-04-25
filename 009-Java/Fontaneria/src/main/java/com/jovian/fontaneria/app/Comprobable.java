package com.jovian.fontaneria.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Comprobable {
	
	/**
	 * metodo para comprobar que el DNI cumple con el formato correcto
	 * @param tfDNI, dni del cliente
	 * @return true or false, en funcion de si cumple o no el formato
	 */
	public static boolean comprobarFormatoDNI(String tfDNI){
		
		//creamos el formato que deseamos tener para el DNI
		Pattern pat = Pattern.compile("[0-9]{8}[A-Z a-z]");
				
		//creamos el comparador para ver que coincide con la cadena que le pasamos
		Matcher mat = pat.matcher(tfDNI);
				
		//devolvemos el resultado de la comparacion
		return mat.matches();
		
	}
	
	/**
	 * metodo para comprobar que la letra del dni es correcta
	 * @param tfDNI, dni del cliente
	 * @return true or false, en funcion de si la letra coincide o no
	 */
	public static boolean comprobarLetraDNI(String tfDNI) {
		
		//orden de las las letras del DNI
		char [] letras = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
		
		//cogemos la parte numerica del dni que recibimos
		String numDni = tfDNI.substring(0, tfDNI.length()-1);
		
		//cogemos la letra del dni que recibimos
		String letraDni = tfDNI.substring(tfDNI.length()-1).toUpperCase();
		
		//pasamos a entero la parte numerica del dni y calculamos su resto
		int DNIentero = Integer.parseInt(numDni);
		int resto = DNIentero%23;
		
		//el resto nos sirve para obtener la posicion del array de letras y coger la letra que corresponda
		String letraResto = Character.toString(letras[resto]);
		
		//finalmente comprobamos la letra del dni que hemos recibido con la letra del array de caracteres y devolvemos un resultado
		if(letraDni.equals(letraResto)) return true;
		else return false;
		
	}
	
	/**
	 * metodo para ver si el nombre o los apellidos cumplen con el patrón
	 * @param nombre, nombre o apellidos
	 * @return true or false, en funcion de si se cumple el patrón
	 */
	public static boolean comprobarNombres(String nombre) {
		//creamos un pattern con los caracteres que queremos validar, incluyendo acentos
		Pattern patron = Pattern.compile("[ A-Za-zñÑáéíóúÁÉÍÓÚ]{3,50}");
				
		//comparamos el patron con la cadena recibida como parametro
		Matcher comprobar = patron.matcher(nombre);
				
		//en funcion de la comparación se devolvera true or false
		if (comprobar.matches()) return true;
		else return false;
	}

	public static boolean comprobarDireccion(String direccion) {
		//creamos un pattern con los caracteres que queremos validar, incluyendo acentos
		Pattern patron = Pattern.compile("[ 0-9A-Za-zñÑáéíóúÁÉÍÓÚ,.ºª/-]{10,200}");
						
		//comparamos el patron con la cadena recibida como parametro
		Matcher comprobar = patron.matcher(direccion);
						
		//en funcion de la comparación se devolvera true or false
		if (comprobar.matches()) return true;
		else return false;
	}

	public static boolean comprobarCodigoPostal(String cPostal) {
		
		//creamos un pattern con los caracteres que queremos validar, incluyendo acentos
		Pattern patron = Pattern.compile("[0-5][0-9]{4}");
						
		//comparamos el patron con la cadena recibida como parametro
		Matcher comprobar = patron.matcher(cPostal);
						
	    //en funcion de la comparación se devolvera true or false
		if (comprobar.matches()) return true;
		else return false;
	}

	public static boolean comprobarEmail(String email) {
		
		//creamos un pattern con los caracteres que queremos validar, incluyendo acentos
		Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
										
		//comparamos el patron con la cadena recibida como parametro
		Matcher comprobar = patron.matcher(email);
										
		//en funcion de la comparación se devolvera true or false
		if (comprobar.matches()) return true;
		else return false;
	}

	public static boolean comprobarTelefono(String tfno) {
		
		//creamos un pattern con los caracteres que queremos validar, incluyendo acentos
		Pattern patron = Pattern.compile("^(\\+34|0034|34)?[6789][0-9]{8}$");
								
		//comparamos el patron con la cadena recibida como parametro
		Matcher comprobar = patron.matcher(tfno);
								
	    //en funcion de la comparación se devolvera true or false
		if (comprobar.matches()) return true;
		else return false;
	}

}
