package com.jovian.fontaneria.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Comprobable {
	
	/**
	 * metodo para comprobar que el DNI cumple con el formato correcto
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

}
