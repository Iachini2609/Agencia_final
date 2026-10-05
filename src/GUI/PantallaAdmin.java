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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class PantallaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RegistrarEmpleado RegistrarEmpleado;
	private GestionEmpleado GestionEmpleado;
	private EditarEmpleado EditarEmpleado;

	
	public PantallaAdmin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaAdmin.class.getResource("/imgenes/logo2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1179, 756);
		setResizable(false);
		setLocationRelativeTo(null);

		// Este bloque se ejecuta cuando el usuario cierra la ventana
		addWindowListener((WindowListener) new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        Inicio inicio = new Inicio();
	        inicio.setVisible(true);
		    }
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RegistrarEmpleado = new RegistrarEmpleado();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(262, 0, 981, 730);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 920, 720);
		if (!java.beans.Beans.isDesignTime()) {
		    tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
		        @Override
		        protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
		            return 0;
		        }
		    });
		}
		panel.add(tabbedPane);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 265, 720);
		contentPane.add(panel_1);
		panel_1.setBackground(new Color(235, 0, 0));
		panel_1.setLayout(null);
		
		ImageIcon originalIcon = new ImageIcon(Inicio.class.getResource("/imgenes/logo hd.png"));
		Image originalImage = originalIcon.getImage();

		// Escalamos suavemente
		Image scaledImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(scaledImage);
		
		JLabel lbl_logo = new JLabel(iconoEscalado);
		lbl_logo.setBounds(10, 10, 245, 184);
		panel_1.add(lbl_logo);
		
		JButton btn_gestionEmpleado = new JButton("Gestion Empleado");
		btn_gestionEmpleado.setIcon(new ImageIcon(PantallaAdmin.class.getResource("/imgenes/icono empleado.png")));
		btn_gestionEmpleado.setBackground(new Color(240, 240, 240)); // gris claro
		btn_gestionEmpleado.setForeground(Color.BLACK);
		btn_gestionEmpleado.setFocusPainted(false);
		btn_gestionEmpleado.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_gestionEmpleado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btn_gestionEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);

			}
		});
		btn_gestionEmpleado.setBounds(21, 232, 223, 39);
		btn_gestionEmpleado.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_gestionEmpleado.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_gestionEmpleado.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_gestionEmpleado.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_gestionEmpleado.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_gestionEmpleado.setForeground(Color.BLACK);
		    }
		});
		panel_1.add(btn_gestionEmpleado);
		
		JButton btn_registrarEmpleado = new JButton("Registrar Empleado");
		btn_registrarEmpleado.setIcon(new ImageIcon(PantallaAdmin.class.getResource("/imgenes/icono de crear cuenta.png")));
		btn_registrarEmpleado.setBackground(new Color(240, 240, 240)); // gris claro
		btn_registrarEmpleado.setForeground(Color.BLACK);
		btn_registrarEmpleado.setFocusPainted(false);
		btn_registrarEmpleado.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_registrarEmpleado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_registrarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(0);
				
			}
		});
		btn_registrarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_registrarEmpleado.setBounds(21, 281, 223, 39);
		btn_registrarEmpleado.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_registrarEmpleado.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_registrarEmpleado.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_registrarEmpleado.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_registrarEmpleado.setForeground(Color.BLACK);
		    }
		});
		panel_1.add(btn_registrarEmpleado);
		
		JButton btn_cerrarSesion = new JButton("Cerrar Sesion");
		btn_cerrarSesion.setIcon(new ImageIcon(PantallaAdmin.class.getResource("/imgenes/icono de volver.png")));
		btn_cerrarSesion.setBackground(new Color(240, 240, 240)); // gris claro
		btn_cerrarSesion.setForeground(Color.BLACK);
		btn_cerrarSesion.setFocusPainted(false);
		btn_cerrarSesion.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_cerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(btn_cerrarSesion);
				ventanaActual.dispose(); // Cierra la pantalla de administrador
				Inicio inicio = new Inicio(); // Crear nueva pantalla de inicio
				inicio.setVisible(true); // Mostrarla
			}
		});
		btn_cerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_cerrarSesion.setBounds(21, 639, 223, 39);
		btn_cerrarSesion.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_cerrarSesion.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_cerrarSesion.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_cerrarSesion.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_cerrarSesion.setForeground(Color.BLACK);
		    }
		});
		
		panel_1.add(btn_cerrarSesion);
		
		JButton btn_CrearAdmin = new JButton("Crear Admin");
		btn_CrearAdmin.setIcon(new ImageIcon(PantallaAdmin.class.getResource("/imgenes/Icono de Masculino.png")));
		btn_CrearAdmin.addActionListener(e -> {
		    CrearAdminDialog dialog = new CrearAdminDialog(null); // pasá tu frame si lo tenés
		    dialog.setVisible(true);
		});
		btn_CrearAdmin.setForeground(Color.BLACK);
		btn_CrearAdmin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_CrearAdmin.setFocusPainted(false);
		btn_CrearAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_CrearAdmin.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_CrearAdmin.setBackground(new Color(240, 240, 240)); // gris claro
		btn_CrearAdmin.setBounds(21, 544, 223, 39);
		btn_CrearAdmin.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_CrearAdmin.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_CrearAdmin.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_CrearAdmin.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_CrearAdmin.setForeground(Color.BLACK);
		    }
		});
		panel_1.add(btn_CrearAdmin);
		
		JButton btn_editarEmpleado = new JButton("Editar Empleado");
		btn_editarEmpleado.setIcon(new ImageIcon(PantallaAdmin.class.getResource("/imgenes/icono de registrarse.png")));
		btn_editarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btn_editarEmpleado.setForeground(Color.BLACK);
		btn_editarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 18));
		btn_editarEmpleado.setFocusPainted(false);
		btn_editarEmpleado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_editarEmpleado.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btn_editarEmpleado.setBackground(new Color(240, 240, 240));
		btn_editarEmpleado.setBounds(21, 330, 223, 39);
		btn_editarEmpleado.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent e) {
		    	btn_editarEmpleado.setBackground(new Color(60, 63, 65)); // gris oscuro
		    	btn_editarEmpleado.setForeground(Color.WHITE);
		    }

		    public void mouseExited(MouseEvent e) {
		    	btn_editarEmpleado.setBackground(new Color(240, 240, 240)); // gris claro
		    	btn_editarEmpleado.setForeground(Color.BLACK);
		    }
		});
		panel_1.add(btn_editarEmpleado);
		
		
		
		RegistrarEmpleado = new RegistrarEmpleado();
		tabbedPane.addTab("Registrar Empleado", RegistrarEmpleado);

		GestionEmpleado = new GestionEmpleado();
		tabbedPane.addTab("Gestion Empleados", GestionEmpleado);

		EditarEmpleado = new EditarEmpleado();
		tabbedPane.addTab("Editar Empleados", EditarEmpleado);
		
		// Esta línea selecciona por defecto la pestaña de Gestión Empleados
		tabbedPane.setSelectedIndex(1);
		
		

	}
}
