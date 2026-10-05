package Controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modelo.Sueldo;

public class SueldoControlador {
    private Connection conexion;

    public SueldoControlador() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:Agencia.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarOActualizarSueldo(Sueldo sueldo) {
        String sqlCheck = "SELECT id FROM sueldos WHERE empleado_id = ? AND mes = ? AND anio = ?";
        String sqlInsert = "INSERT INTO sueldos (empleado_id, mes, anio, valor_hora, horas_normales, horas_feriado, sueldo_total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE sueldos SET valor_hora = ?, horas_normales = ?, horas_feriado = ?, sueldo_total = ? WHERE id = ?";

        try (PreparedStatement checkStmt = conexion.prepareStatement(sqlCheck)) {
            checkStmt.setInt(1, sueldo.getEmpleadoId());
            checkStmt.setInt(2, sueldo.getMes());
            checkStmt.setInt(3, sueldo.getAnio());

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                try (PreparedStatement updateStmt = conexion.prepareStatement(sqlUpdate)) {
                    updateStmt.setDouble(1, sueldo.getValorHora());
                    updateStmt.setDouble(2, sueldo.getHorasNormales());
                    updateStmt.setDouble(3, sueldo.getHorasFeriado());
                    updateStmt.setDouble(4, sueldo.getSueldoTotal());
                    updateStmt.setInt(5, id);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conexion.prepareStatement(sqlInsert)) {
                    insertStmt.setInt(1, sueldo.getEmpleadoId());
                    insertStmt.setInt(2, sueldo.getMes());
                    insertStmt.setInt(3, sueldo.getAnio());
                    insertStmt.setDouble(4, sueldo.getValorHora());
                    insertStmt.setDouble(5, sueldo.getHorasNormales());
                    insertStmt.setDouble(6, sueldo.getHorasFeriado());
                    insertStmt.setDouble(7, sueldo.getSueldoTotal());
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Sueldo> obtenerSueldosPorEmpleadoYPeriodo(int empleadoId, int desdeMes, int hastaMes, int anio) {
        Map<Integer, Sueldo> sueldoPorMes = new HashMap<>();
        String sql = "SELECT * FROM sueldos WHERE empleado_id = ? AND mes BETWEEN ? AND ? AND anio = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setInt(2, desdeMes);
            stmt.setInt(3, hastaMes);
            stmt.setInt(4, anio);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sueldo s = new Sueldo();
                s.setId(rs.getInt("id"));
                s.setEmpleadoId(rs.getInt("empleado_id"));
                s.setMes(rs.getInt("mes"));
                s.setAnio(rs.getInt("anio"));
                s.setHorasNormales(rs.getDouble("horas_normales"));
                s.setHorasFeriado(rs.getDouble("horas_feriado"));
                s.setValorHora(rs.getDouble("valor_hora"));
                s.setSueldoTotal(rs.getDouble("sueldo_total"));
                sueldoPorMes.put(s.getMes(), s);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener sueldos del período: " + e.getMessage());
        }

        // Crear lista asegurando que estén todos los meses
        List<Sueldo> sueldosCompletos = new ArrayList<>();
        for (int mes = desdeMes; mes <= hastaMes; mes++) {
            if (sueldoPorMes.containsKey(mes)) {
                sueldosCompletos.add(sueldoPorMes.get(mes));
            } else {
                // Crear Sueldo con sueldo total 0 para meses sin datos
                Sueldo s = new Sueldo();
                s.setEmpleadoId(empleadoId);
                s.setMes(mes);
                s.setAnio(anio);
                s.setHorasNormales(0);
                s.setHorasFeriado(0);
                s.setValorHora(0);
                s.setSueldoTotal(0);
                sueldosCompletos.add(s);
            }
        }

        return sueldosCompletos;
    }
}