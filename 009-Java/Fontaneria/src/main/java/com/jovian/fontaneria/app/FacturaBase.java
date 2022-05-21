package com.jovian.fontaneria.app;

public class FacturaBase {
	
	private int cantidad;
	private String descripcion;
	private double precioUnitario;
	private double importe;
	
	
	public FacturaBase(int cantidad, String descripcion, double precioUnitario, double importe) {
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.importe = importe;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public double getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	public double getImporte() {
		return importe;
	}


	public void setImporte(int importe) {
		this.importe = importe;
	}
	
	

}
