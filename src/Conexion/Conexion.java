package Conexion;

import java.sql.*;

public class Conexion {
	Connection cx = null;

	public Connection conectar() {
	    try {
	        Class.forName("org.sqlite.JDBC");
	        cx = DriverManager.getConnection("jdbc:sqlite:Agencia.db");
	        System.out.println("Conectado correctamente");
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	    return cx;
	}
	

	public void Desconectar() {
	    try {
	        if (cx != null && !cx.isClosed()) {
	            cx.close();
	            System.out.println("Desconexión exitosa");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
