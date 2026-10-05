package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controlador.AdministradorControlador;
import Controlador.EmpleadoControlador;
import Modelo.Administrador;
import Modelo.Empleado;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_dni;
	private JPasswordField txtContrasenia;

	public Inicio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/imgenes/logo2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 424, 521);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(227, 0, 10));
		panel.setForeground(Color.WHITE);
		panel.setBounds(0, 0, 420, 494);
		contentPane.add(panel);
		panel.setLayout(null);

		ImageIcon originalIcon = new ImageIcon(Inicio.class.getResource("/imgenes/logo hd.png"));
		Image originalImage = originalIcon.getImage();

		// Escalamos suavemente
		Image scaledImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(scaledImage);

		// Creamos el JLabel y lo ubicamos
		JLabel label = new JLabel(iconoEscalado);
		label.setBounds(82, 10, 250, 250); // estos valores ubican bien el logo
		panel.add(label);

		JLabel lbl_dni = new JLabel("DNI");
		lbl_dni.setForeground(Color.WHITE);
		lbl_dni.setBackground(Color.WHITE);
		lbl_dni.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_dni.setBounds(187, 236, 45, 38);
		panel.add(lbl_dni);

		txt_dni = new JTextField();
		txt_dni.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txt_dni.setBounds(124, 268, 171, 27);
		panel.add(txt_dni);
		txt_dni.setColumns(10);

		JLabel lbl_contrasenia = new JLabel("CONTRASEÑA");
		lbl_contrasenia.setForeground(Color.WHITE);
		lbl_contrasenia.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_contrasenia.setBounds(142, 318, 136, 38);
		panel.add(lbl_contrasenia);

		JButton btn_IniciarSesion = new JButton("Iniciar Sesion");
		btn_IniciarSesion.setIcon(new ImageIcon(Inicio.class.getResource("/imgenes/icono de inicio de sesion.png")));
		btn_IniciarSesion.setBackground(new Color(240, 240, 240)); // gris claro
		btn_IniciarSesion.setForeground(Color.BLACK);
		btn_IniciarSesion.setFocusPainted(false);
		btn_IniciarSesion.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_IniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Animación al pasar el mouse (hover)
		btn_IniciarSesion.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btn_IniciarSesion.setBackground(new Color(60, 63, 65)); // gris oscuro
				btn_IniciarSesion.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btn_IniciarSesion.setBackground(new Color(240, 240, 240)); // gris claro
				btn_IniciarSesion.setForeground(Color.BLACK);
			}
		});
		btn_IniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txt_dni.getText().trim(); // Este campo ahora representa "usuario"
				@SuppressWarnings("deprecation")
				String contrasenia = txtContrasenia.getText().trim();

				EmpleadoControlador empleadoControlador = new EmpleadoControlador("Agencia.db");
				AdministradorControlador adminControlador = new AdministradorControlador();

				// Buscar si es admin
				Administrador admin = adminControlador.buscarPorUsuarioYContrasenia(usuario, contrasenia);
				if (admin != null) {
					PantallaAdmin pantallaAdmin = new PantallaAdmin();
					pantallaAdmin.setVisible(true);
					dispose();
					return;
				}

				// Buscar si es empleado (usa DNI)
				Empleado empleado = empleadoControlador.buscarPorDniYContrasenia(usuario, contrasenia);
				if (empleado != null) {
					PantallaEmpleado pantallaEmpleado = new PantallaEmpleado(empleado);
					pantallaEmpleado.setVisible(true);
					dispose();
					return;
				}

				JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
			}
		});
		btn_IniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_IniciarSesion.setBounds(98, 414, 223, 39);
		panel.add(btn_IniciarSesion);
		getRootPane().setDefaultButton(btn_IniciarSesion);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setFont(new Font("Tahoma", Font.PLAIN, 21));
		txtContrasenia.setBounds(124, 358, 171, 27);
		panel.add(txtContrasenia);

	}
}
