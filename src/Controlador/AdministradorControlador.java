package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modelo.Administrador;

public class AdministradorControlador {

    private Connection conexion;

    public AdministradorControlador() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:Agencia.db");
            crearTablaAdministradores(); // Verifica o crea la tabla al inicializar
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public Administrador buscarPorUsuarioYContrasenia(String usuario, String contrasenia) {
        String sql = "SELECT * FROM administradores WHERE usuario = ? AND contrasenia = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasenia);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Administrador(
                    rs.getString("usuario"),
                    rs.getString("contrasenia")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar administrador: " + e.getMessage());
        }

        return null;
    }

    public boolean guardarAdminEnBD(String usuario, String contrasenia) {
        String sql = "INSERT INTO administradores (usuario, contrasenia) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasenia);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void crearTablaAdministradores() {
        String sql = "CREATE TABLE IF NOT EXISTS administradores (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "usuario TEXT NOT NULL UNIQUE," +
                     "contrasenia TEXT NOT NULL)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error al crear tabla administradores: " + e.getMessage());
        }
    }

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