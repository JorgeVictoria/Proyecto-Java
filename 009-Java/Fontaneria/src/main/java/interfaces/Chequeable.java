package interfaces;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface Chequeable {
	
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

}
