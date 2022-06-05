package com.jovian.fontaneria.app;

/**
 * clase para construir los elementos que componen la factura
 * @author Jorge Victoria Andreu
 * @version 1.0
 */
public class FacturaBase {
	
	//variables miembro
	private int cantidad;
	private String descripcion;
	private double precioUnitario;
	private double importe;
	
	/**
	 * clase constructor
	 * @param cantidad de material
	 * @param descripcion el nombre del material
	 * @param precioUnitario precio de fabrica del material
	 * @param importe precio del material del vendedor
	 */
	public FacturaBase(int cantidad, String descripcion, double precioUnitario, double importe) {
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.importe = importe;
	}

   /**
    * metodo que devuelve la cantidad
    * @return la cantidad
    */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * metodo para establecer la cantidad
	 * @param cantidad del material
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * metodo que devuelve el nombre del material
	 * @return el nombre del material
	 */
	public String getDescripcion() {
		return descripcion;
	}


	/**
	 * metodo para establecer el nombre del material
	 * @param descripcion el nombre del material
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	/**
	 * metodo que devuelve el precio de fabrica del material
	 * @return el precio original del material
	 */
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * metodo para establecer el precio de fabrica del material
	 * @param precioUnitario el precio de fabrica del material
	 */
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	/**
	 * metodo que devuelve el precio del material que propone el vendedor
	 * @return el precio del material que propone el vendedor
	 */
	public double getImporte() {
		return importe;
	}


	/**´
	 * metodo para establecer el precio que pone el vendedor al material
	 * @param importe el precio que pone el vendedor al material
	 */
	public void setImporte(int importe) {
		this.importe = importe;
	}
	
	

}
