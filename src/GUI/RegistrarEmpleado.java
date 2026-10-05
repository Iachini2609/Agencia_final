package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;

import Controlador.EmpleadoControlador;
import Modelo.Empleado;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class RegistrarEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDni;
	private JTextField txtContraseña;

	/**
	 * Create the panel.
	 */
	public RegistrarEmpleado() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, -12, 930, 730);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setIcon(new ImageIcon(RegistrarEmpleado.class.getResource("/imgenes/logo2.png")));
		lblNewLabel.setBounds(-30, 10, 373, 674);
		panel.add(lblNewLabel);
		
		JLabel lblRegistrarEmpleado = new JLabelConContorno("Registar Empleado");
		lblRegistrarEmpleado.setVerticalAlignment(SwingConstants.TOP);
		lblRegistrarEmpleado.setForeground(new Color(255, 255, 255));
		lblRegistrarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblRegistrarEmpleado.setBounds(288, 40, 354, 49);
		panel.add(lblRegistrarEmpleado);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNombre.setBounds(415, 143, 99, 32);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNombre.setBounds(320, 171, 289, 32);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setForeground(Color.WHITE);
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblApellido.setBounds(415, 227, 99, 32);
		panel.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtApellido.setColumns(10);
		txtApellido.setBounds(320, 258, 289, 32);
		panel.add(txtApellido);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setForeground(Color.WHITE);
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblDni.setBounds(434, 313, 62, 32);
		panel.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtDni.setColumns(10);
		txtDni.setBounds(320, 345, 289, 32);
		panel.add(txtDni);
		
		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setForeground(Color.WHITE);
		lblContrasenia.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblContrasenia.setBounds(396, 399, 138, 32);
		panel.add(lblContrasenia);
		
		txtContraseña = new JTextField();
		txtContraseña.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(320, 428, 289, 32);
		panel.add(txtContraseña);
		
		JButton btn_CrearUsuario = new JButton("Crear Usuario\r\n");
		btn_CrearUsuario.setIcon(new ImageIcon(RegistrarEmpleado.class.getResource("/imgenes/icono de crear cuenta.png")));
		btn_CrearUsuario.setBackground(new Color(240, 240, 240)); // gris claro
		btn_CrearUsuario.setForeground(Color.BLACK);
		btn_CrearUsuario.setFocusPainted(false);
		btn_CrearUsuario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_CrearUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_CrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText().trim();
				String apellido = txtApellido.getText().trim();
				String dni = txtDni.getText().trim();
				String contrasena = txtContraseña.getText().trim();

				// Validación básica
				if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || contrasena.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Crear el objeto empleado
				Empleado nuevo = new Empleado(nombre, apellido, dni, contrasena);

				// Registrar usando el controlador
				EmpleadoControlador controlador = new EmpleadoControlador("Agencia.db");
				if (controlador.registrarEmpleado(nuevo)) {
					JOptionPane.showMessageDialog(null, "Empleado registrado correctamente.");
					txtNombre.setText("");
					txtApellido.setText("");
					txtDni.setText("");
					txtContraseña.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Error al registrar empleado.", "Error", JOptionPane.ERROR_MESSAGE);
				}

				controlador.cerrarConexion();
			}
		});

		btn_CrearUsuario.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_CrearUsuario.setBounds(353, 491, 223, 39);
		btn_CrearUsuario.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_CrearUsuario.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_CrearUsuario.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_CrearUsuario.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_CrearUsuario.setForeground(Color.BLACK);
		    }
		});
		panel.add(btn_CrearUsuario);

	}
}
