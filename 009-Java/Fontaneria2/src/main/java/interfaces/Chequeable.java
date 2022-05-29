package interfaces;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * clase de tipo interfaz para comprobar el formato de los datos introducidos en los campos
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public interface Chequeable {
	
	/**
	 * metodo para comprobar que los datos del cliente sean correctos
	 * recibe todos los datos del cliente
	 * @param lblWarning es la etiqueta del formulario donde se mostrarán los errores
	 * @param tfDNI
	 * @param tfNombreCliente
	 * @param tfApellido1
	 * @param tfApellido2
	 * @param tfDireccion
	 * @param tfCPostal
	 * @param tfLocalidad
	 * @param tfProvincia
	 * @param tfEmail
	 * @param tfTelefono
	 * @return un valor booleano con el resultado de la comprobacion
	 */
	public static boolean chequeaCliente(Label lblWarning, TextField tfDNI, TextField tfNombreCliente, TextField tfApellido1, TextField tfApellido2, TextField tfDireccion, TextField tfCPostal, TextField tfLocalidad, TextField tfProvincia, TextField tfEmail, TextField tfTelefono) {
		
		//variables locales
		boolean correcto = true;
		
		//mientras correcto sea true, iremos chequeando que los campos esten rellenos correctamente
		while(correcto) {
			
			//comprobamos que el formato del DNI es correcto
			correcto = Comprobable.comprobarFormatoDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("El formato del DNI incorrecto.");
				break;
			}
			
			//comprobamos que la letra del dni es correcta
			correcto = Comprobable.comprobarLetraDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("La letra del DNI no es correcta.");
				break;
			}
			
			//comprobamos que el campo nombre no esté vacio y tenga el formato correcto(solo letras)
			tfNombreCliente.setText(tfNombreCliente.getText().trim());
			correcto = Comprobable.comprobarNombres(tfNombreCliente.getText());
			if(!correcto) {
				lblWarning.setText("El formato del nombre no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido1 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido1.setText(tfApellido1.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido1.getText());
			if(!correcto) {
				lblWarning.setText("El formato del primer apellido no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido2 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido2.setText(tfApellido2.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido2.getText());
			if(!correcto) {
				lblWarning.setText("El formato del segundo apellido no es correcto.");
				break;
			}
			
			//comprobamos que el campo direccion no esté vacio y tenga el formato correcto(letras y numeros segun patron)
			tfDireccion.setText(tfDireccion.getText().trim());
			correcto = Comprobable.comprobarDireccion(tfDireccion.getText());
			if(!correcto) {
				lblWarning.setText("El formato de la direccion no es correcto.");
				break;
			}
			
			//comprobamos que el campo codigo postal no esté vacio y tenga el formato correcto(segun patron)
			tfCPostal.setText(tfCPostal.getText().trim());
			correcto = Comprobable.comprobarCodigoPostal(tfCPostal.getText());
			if(!correcto) {
				lblWarning.setText("El formato del codigo postal no es correcto.");
				break;
			}
			
			//comprobamos que el campo localidad no esté vacio y tenga el formato correcto(solo letras)
			tfLocalidad.setText(tfLocalidad.getText().trim());
			correcto = Comprobable.comprobarNombres(tfLocalidad.getText());
			if(!correcto) {
				lblWarning.setText("El formato de la localidad no es correcto.");
				break;
			}
			
			//comprobamos que el campo provincia no esté vacio y tenga el formato correcto(solo letras)
			tfProvincia.setText(tfProvincia.getText().trim());
			correcto = Comprobable.comprobarNombres(tfProvincia.getText());
			if(!correcto) {
				lblWarning.setText("El formato de la provincia no es correcto.");
				break;
			}
			
			//comprobamos que el campo email no esté vacio y tenga el formato correcto(pattern correcto)
			tfEmail.setText(tfEmail.getText().trim());
			correcto = Comprobable.comprobarEmail(tfEmail.getText());
			if(!correcto) {
				lblWarning.setText("El formato del email no es correcto.");
				break;
			}
			
			//comprobamos que el campo telefono no esté vacio y tenga el formato correcto(pattern correcto)
			tfTelefono.setText(tfTelefono.getText().trim());
			correcto = Comprobable.comprobarTelefono(tfTelefono.getText());
			if(!correcto) {
				lblWarning.setText("El formato del numero de telefono no es correcto.");
				break;
			}
			
			//si esta todo correcto, ya podemos salir del bucle
			if(correcto) break;
			
		}
		
		//devolvemos el valor de la comprobación de los campos
		return correcto;
		
	}

	/**
	 * metodo para chequear que el campo dni tenga el formato correcto
	 * @param lblWarning es la etiqueta del formulario donde se mostrarán los errores
	 * @param tfDNI
	 * @return un valor booleano con el resultado de la comprobacion
	 */
	public static boolean chequearDni(Label lblWarning, TextField tfDNI) {
		
		//variables locales
		boolean correcto = true;
		
		while(correcto) {
			
			//comprobamos que el formato del DNI es correcto
			correcto = Comprobable.comprobarFormatoDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("El formato del DNI incorrecto.");
				break;
			}
					
			//comprobamos que la letra del dni es correcta
			correcto = Comprobable.comprobarLetraDNI(tfDNI.getText());
			if(!correcto) {
				lblWarning.setText("La letra del DNI no es correcta.");
				break;
			}
			
			//si esta todo correcto, ya podemos salir del bucle
			if(correcto) break;
			
		}
		
		//devolvemos el valor de la comprobación de los campos
		return correcto;
		
	}

	/**
	 * metodo para comprobar que el nombre y apellidos del cliente sean correctos
	 * @param lblWarning es la etiqueta del formulario donde se mostrarán los errores
	 * @param tfNombreCliente
	 * @param tfApellido1
	 * @param tfApellido2
	 * @return un valor booleano con el resultado de la comprobacion
	 */
	public static boolean chequearNombreApellidos(Label lblWarning, TextField tfNombreCliente, TextField tfApellido1,TextField tfApellido2) {
		
		//variables locales
		boolean correcto = true;
		
		while(correcto) {
			
			//comprobamos que el campo nombre no esté vacio y tenga el formato correcto(solo letras)
			tfNombreCliente.setText(tfNombreCliente.getText().trim());
			correcto = Comprobable.comprobarNombres(tfNombreCliente.getText());
			if(!correcto) {
				lblWarning.setText("El formato del nombre no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido1 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido1.setText(tfApellido1.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido1.getText());
			if(!correcto) {
				lblWarning.setText("El formato del primer apellido no es correcto.");
				break;
			}
			
			//comprobamos que el campo apellido2 no esté vacio y tenga el formato correcto(solo letras)
			tfApellido2.setText(tfApellido2.getText().trim());
			correcto = Comprobable.comprobarNombres(tfApellido2.getText());
			if(!correcto) {
				lblWarning.setText("El formato del segundo apellido no es correcto.");
				break;
			}
			
			//si esta todo correcto, ya podemos salir del bucle
			if(correcto) break;
			
		}
		
		//devolvemos el valor de la comprobación de los campos
		return correcto;
	}

	/**
	 * metodo para comprobar que los datos del formulario de alta de material sean correctos
	 * @param lblWarning es la etiqueta del formulario donde se mostrarán los errores
	 * @param claseMaterial
	 * @param tfNombreMaterial
	 * @param tfPrecioCosteMaterial
	 * @param tfPorcentajeIncrementoMaterial
	 * @param taDescripcionMaterial
	 * @return un valor booleano con el resultado de la comprobacion
	 */
	public static boolean chequeaMaterial(Label lblWarning, String claseMaterial, TextField tfNombreMaterial, TextField tfPrecioCosteMaterial, TextField tfPorcentajeIncrementoMaterial, TextArea taDescripcionMaterial) {
		
		//variables locales
		boolean correcto = true;
		
		while(correcto) {
			
			//comprobamos que el campo nombreMaterial no esté vacio.
			tfNombreMaterial.setText(tfNombreMaterial.getText().trim());
			if(tfNombreMaterial.getText().isBlank() && tfNombreMaterial.getText().isEmpty())correcto = false;
			if(!correcto) {
				lblWarning.setText("El campo nombre no puede estar vacio");
				break;
			}
			
			//comprobamos que el campo claseMaterial corresponda con algun tipo de materual
			if(claseMaterial.equals("ninguno")) correcto = false;
			if(!correcto) {
				lblWarning.setText("Debe seleccionar un tipo de material");
				break;
			}
			
			//comprobamos que el campo tfPrecioCosteMaterial sea de tipo numerico
			correcto = Comprobable.comprobarDouble(tfPrecioCosteMaterial);
			if(!correcto) {
				lblWarning.setText("El campo precio debe ser numerico");
				break;
			}
			
			//comprobamos que el campo tfPrecioCosteMaterial sea de tipo numerico
			correcto = Comprobable.comprobarDouble(tfPorcentajeIncrementoMaterial);
			if(!correcto) {
				lblWarning.setText("El campo del porcentaje debe ser numerico");
				break;
			}
			
			if(taDescripcionMaterial.getText().isBlank() && taDescripcionMaterial.getText().isEmpty()) taDescripcionMaterial.setText("sin descripcion");
			
			
			//si esta todo correcto, ya podemos salir del bucle
			if(correcto) break;
			
		}
		
		return correcto;
	}

}
