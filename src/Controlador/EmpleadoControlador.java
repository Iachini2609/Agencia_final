package Controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelo.Empleado;

public class EmpleadoControlador {

    private Connection conexion;

    public EmpleadoControlador(String rutaBaseDeDatos) {
        try {
            // Conexión a la base de datos SQLite
            conexion = DriverManager.getConnection("jdbc:sqlite:Agencia.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Crear (INSERT)
    public boolean registrarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleado (nombre, apellido, dni, contrasena) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getDni());
            stmt.setString(4, empleado.getContrasena());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar empleado: " + e.getMessage());
            return false;
        }
    }

    // Leer (SELECT)
    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("contrasena")
                );
                empleados.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener empleados: " + e.getMessage());
        }
        return empleados;
    }

    // Buscar por DNI
    public Empleado buscarPorDni(String dni) {
        String sql = "SELECT * FROM empleado WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }
    
    public Empleado buscarPorDniYContrasenia(String dni, String contrasenia) {
        String sql = "SELECT * FROM empleado WHERE dni = ? AND contrasena = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, contrasenia);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setDni(rs.getString("dni"));
                empleado.setContrasena(rs.getString("contrasena"));
                return empleado;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar empleado por DNI y contraseña: " + e.getMessage());
        }

        return null;
    }
    
    public boolean eliminarPorDni(String dni) {
        String sql = "DELETE FROM empleado WHERE dni = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado por DNI: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, contrasena = ? WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getContrasena());
            stmt.setString(4, empleado.getDni());  // usamos el DNI para buscar al empleado a modificar
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }
    
    
    

    // Cerrar conexión (opcional)
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
