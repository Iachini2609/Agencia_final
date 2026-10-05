package Controlador;

import Modelo.Horarios;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HorarioControlador {

    private Connection conexion;

    public HorarioControlador() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:Agencia.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✔️ Insertar horario
    public boolean registrarHorario(Horarios horario) {
        String sql = "INSERT INTO horarios (empleado_id, fecha, turno, hora_entrada, hora_salida) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, horario.getEmpleadoId());
            stmt.setString(2, horario.getFecha().toString());
            stmt.setString(3, horario.getTurno());
            stmt.setString(4, horario.getHoraEntrada().toString());
            stmt.setString(5, horario.getHoraSalida() != null ? horario.getHoraSalida().toString() : null);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar horario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registrarEntrada(int empleadoId, String fecha, String turno, String horaEntrada) {
        String sql = "INSERT INTO horarios (empleado_id, fecha, turno, hora_entrada, feriado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, fecha);
            stmt.setString(3, turno);
            stmt.setString(4, horaEntrada);
            stmt.setBoolean(5, false);  // feriado siempre false para el empleado

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean yaTieneEntrada(int empleadoId, String fecha, String turno) {
        String sql = "SELECT COUNT(*) FROM horarios WHERE empleado_id = ? AND fecha = ? AND turno = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, fecha);
            stmt.setString(3, turno);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // true si ya tiene una entrada
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar entrada existente: " + e.getMessage());
        }

        return false; // por defecto, asumimos que no tiene
    }
    
    public boolean yaTieneSalida(int empleadoId, String fecha, String turno) {
        String sql = "SELECT hora_salida FROM horarios WHERE empleado_id = ? AND fecha = ? AND turno = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, fecha);
            stmt.setString(3, turno);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String horaSalida = rs.getString("hora_salida");
                return horaSalida != null; // true si ya tiene salida registrada
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar salida existente: " + e.getMessage());
        }

        return false;
    }

    public boolean tieneEntradaSinSalida(int empleadoId, String fecha, String turno) {
        String sql = "SELECT hora_entrada, hora_salida FROM horarios WHERE empleado_id = ? AND fecha = ? AND turno = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, fecha);
            stmt.setString(3, turno);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String entrada = rs.getString("hora_entrada");
                String salida = rs.getString("hora_salida");
                return entrada != null && salida == null;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar entrada sin salida: " + e.getMessage());
        }

        return false;
    }
    
    public void actualizarHorario(int horarioId, String fecha, String turno, String entrada, String salida, boolean feriado) {
        // Fíjate que ahora actualizamos también la fecha (por si el usuario la editó)
        // y el WHERE es exclusivamente por 'id'
        String sql = "UPDATE horarios SET fecha = ?, turno = ?, hora_entrada = ?, hora_salida = ?, feriado = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, fecha);
            stmt.setString(2, turno);
            stmt.setString(3, entrada);
            stmt.setString(4, salida);
            stmt.setBoolean(5, feriado);
            stmt.setInt(6, horarioId); // Usamos el ID de la fila
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean registrarSalida(int empleadoId, LocalDate fecha, String turno, LocalTime horaSalida) {
        String sql = "UPDATE horarios SET hora_salida = ? WHERE empleado_id = ? AND fecha = ? AND turno = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, horaSalida.toString());
            stmt.setInt(2, empleadoId);
            stmt.setString(3, fecha.toString());
            stmt.setString(4, turno);

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar salida: " + e.getMessage());
            return false;
        }
    }

    // ✔️ Obtener horarios de un empleado por mes/año
    public List<Horarios> obtenerHorariosPorEmpleadoYMes(int empleadoId, int mes, int anio) {
        List<Horarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE empleado_id = ? AND strftime('%m', fecha) = ? AND strftime('%Y', fecha) = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, String.format("%02d", mes));
            stmt.setString(3, String.valueOf(anio));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Horarios h = new Horarios(
                        rs.getInt("id"),
                        rs.getInt("empleado_id"),
                        LocalDate.parse(rs.getString("fecha")),
                        rs.getString("turno"),
                        LocalTime.parse(rs.getString("hora_entrada")),
                        rs.getString("hora_salida") != null ? LocalTime.parse(rs.getString("hora_salida")) : null
                );
                lista.add(h);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener horarios: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean actualizarFeriado(int horarioId, boolean feriado) {
        String sql = "UPDATE horarios SET feriado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, feriado);
            stmt.setInt(2, horarioId);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Horarios> obtenerHorariosPorMes(int empleadoId, int mes, int anio) {
        List<Horarios> listaHorarios = new ArrayList<>();
        String sql = "SELECT * FROM horarios WHERE empleado_id = ? AND strftime('%m', fecha) = ? AND strftime('%Y', fecha) = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoId);
            stmt.setString(2, String.format("%02d", mes)); // Mes con dos dígitos
            stmt.setString(3, String.valueOf(anio));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Horarios h = new Horarios();
                h.setId(rs.getInt("id"));
                h.setEmpleadoId(rs.getInt("empleado_id"));

                // Fecha
                String fechaStr = rs.getString("fecha");
                LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
                h.setFecha(fecha);

                // Turno
                h.setTurno(rs.getString("turno"));

                // Horas
                String horaEntradaStr = rs.getString("hora_entrada");
                if (horaEntradaStr != null) {
                    LocalTime horaEntrada = LocalTime.parse(horaEntradaStr, DateTimeFormatter.ISO_LOCAL_TIME);
                    h.setHoraEntrada(horaEntrada);
                }

                String horaSalidaStr = rs.getString("hora_salida");
                if (horaSalidaStr != null) {
                    LocalTime horaSalida = LocalTime.parse(horaSalidaStr, DateTimeFormatter.ISO_LOCAL_TIME);
                    h.setHoraSalida(horaSalida);
                }

                // ✅ AÑADIDO: Feriado
                h.setFeriado(rs.getBoolean("feriado"));

                listaHorarios.add(h);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener horarios: " + e.getMessage());
        }
        return listaHorarios;
    }

    // ✔️ Calcular horas trabajadas del mes
    public double calcularHorasTrabajadas(int empleadoId, int mes, int anio) {
        double totalHoras = 0;

        List<Horarios> horarios = obtenerHorariosPorEmpleadoYMes(empleadoId, mes, anio);

        for (Horarios h : horarios) {
            if (h.getHoraEntrada() != null && h.getHoraSalida() != null) {
                long minutos = java.time.Duration.between(h.getHoraEntrada(), h.getHoraSalida()).toMinutes();
                totalHoras += minutos / 60.0;
            }
        }

        return totalHoras;
    }

    // ✔️ Calcular sueldo
    public double calcularSueldo(int empleadoId, int mes, int anio, double valorHora) {
        double horas = calcularHorasTrabajadas(empleadoId, mes, anio);
        return horas * valorHora;
    }

    // ✔️ Cerrar conexión
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    public double calcularAguinaldo(int empleadoId, int anio, double valorHora) {
        double mejorSueldo = 0.0;
        // Vamos a recorrer 6 meses: enero a junio (semestre 1)
        // O podés hacer de julio a diciembre para semestre 2, ajusta según necesidad.
        for (int mes = 1; mes <= 6; mes++) {
            double sueldoMes = calcularSueldo(empleadoId, mes, anio, valorHora);
            if (sueldoMes > mejorSueldo) {
                mejorSueldo = sueldoMes;
            }
        }
        // Retornamos la mitad del mejor sueldo
        return mejorSueldo / 2.0;
    }
}
