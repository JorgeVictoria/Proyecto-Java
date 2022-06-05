package com.jovian.fontaneria.app;

public class Presupuesto {
	
	//variables miembro
	private String IDPresupuesto;
	private String Cliente_DNI;
	private String NumAlbaran;
	private String NumFactura;
	private String Fecha;
	private int TasaIva;
	private Double BaseImponible;
	private Double ImporteIva;
	private Double Total;
	
	public Presupuesto(String iDPresupuesto, String cliente_DNI, String numAlbaran, String numFactura, String fecha,
			int tasaIva, Double baseImponible, Double importeIva, Double total) {
		IDPresupuesto = iDPresupuesto;
		Cliente_DNI = cliente_DNI;
		NumAlbaran = numAlbaran;
		NumFactura = numFactura;
		Fecha = fecha;
		TasaIva = tasaIva;
		BaseImponible = baseImponible;
		ImporteIva = importeIva;
		Total = total;
	}

	public String getIDPresupuesto() {
		return IDPresupuesto;
	}

	public String getCliente_DNI() {
		return Cliente_DNI;
	}

	public String getNumAlbaran() {
		return NumAlbaran;
	}

	public String getNumFactura() {
		return NumFactura;
	}

	public String getFecha() {
		return Fecha;
	}

	public int getTasaIva() {
		return TasaIva;
	}

	public Double getBaseImponible() {
		return BaseImponible;
	}

	public Double getImporteIva() {
		return ImporteIva;
	}

	public Double getTotal() {
		return Total;
	}

	public void setIDPresupuesto(String iDPresupuesto) {
		IDPresupuesto = iDPresupuesto;
	}

	public void setCliente_DNI(String cliente_DNI) {
		Cliente_DNI = cliente_DNI;
	}

	public void setNumAlbaran(String numAlbaran) {
		NumAlbaran = numAlbaran;
	}

	public void setNumFactura(String numFactura) {
		NumFactura = numFactura;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public void setTasaIva(int tasaIva) {
		TasaIva = tasaIva;
	}

	public void setBaseImponible(Double baseImponible) {
		BaseImponible = baseImponible;
	}

	public void setImporteIva(Double importeIva) {
		ImporteIva = importeIva;
	}

	public void setTotal(Double total) {
		Total = total;
	}
	
	

}
