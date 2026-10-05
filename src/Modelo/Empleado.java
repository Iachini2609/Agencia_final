package Modelo;

public class Empleado {

    private int id; // ← nuevo campo
    private String nombre;
    private String apellido;
    private String dni;
    private String contrasena;

    public Empleado() {
    }

    // Constructor sin ID (para insertar nuevos empleados)
    public Empleado(String nombre, String apellido, String dni, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.contrasena = contrasena;
    }

    // Constructor con ID (para empleados ya existentes en la BD)
    public Empleado(int id, String nombre, String apellido, String dni, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.contrasena = contrasena;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}