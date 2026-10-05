package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Horarios {

    private int id;
    private int empleadoId;
    private LocalDate fecha;
    private String turno; // Ej: "mañana", "tarde", etc.
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private boolean feriado;

 // Constructor SIN ID (nuevo horario con feriado)
    public Horarios(int empleadoId, LocalDate fecha, String turno, LocalTime horaEntrada, LocalTime horaSalida, boolean feriado) {
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.turno = turno;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.feriado = feriado;
    }
    
 // Constructor CON ID (recuperado desde la base de datos)
    public Horarios(int id, int empleadoId, LocalDate fecha, String turno, LocalTime horaEntrada, LocalTime horaSalida, boolean feriado) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.turno = turno;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.feriado = feriado;
    }
    public Horarios()
    {
    	
    }
    
    // Constructor con ID (para cuando se recupera desde la base de datos)
    public Horarios(int id, int empleadoId, LocalDate fecha, String turno, LocalTime horaEntrada, LocalTime horaSalida) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.turno = turno;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setId(int id) {
        this.id = id;    
	}
    
    public boolean isFeriado() {
        return feriado;
    }

    public void setFeriado(boolean feriado) {
        this.feriado = feriado;
    }
    

}