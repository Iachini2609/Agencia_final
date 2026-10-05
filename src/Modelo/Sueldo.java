package Modelo;

public class Sueldo {
    private int id;
    private int empleadoId;
    private int mes;
    private int anio;
    private double valorHora;
    private double horasNormales;
    private double horasFeriado;
    private double sueldoTotal;

    public Sueldo(int empleadoId, int mes, int anio, double valorHora, double horasNormales, double horasFeriado, double sueldoTotal) {
        this.empleadoId = empleadoId;
        this.mes = mes;
        this.anio = anio;
        this.valorHora = valorHora;
        this.horasNormales = horasNormales;
        this.horasFeriado = horasFeriado;
        this.sueldoTotal = sueldoTotal;
    }
    
    public Sueldo() {
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(int empleadoId) {
		this.empleadoId = empleadoId;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public double getValorHora() {
		return valorHora;
	}

	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}

	public double getHorasNormales() {
		return horasNormales;
	}

	public void setHorasNormales(double horasNormales) {
		this.horasNormales = horasNormales;
	}

	public double getHorasFeriado() {
		return horasFeriado;
	}

	public void setHorasFeriado(double horasFeriado) {
		this.horasFeriado = horasFeriado;
	}

	public double getSueldoTotal() {
		return sueldoTotal;
	}

	public void setSueldoTotal(double sueldoTotal) {
		this.sueldoTotal = sueldoTotal;
	}

    
}