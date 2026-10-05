
package GUI; // Asegurate de que coincida con tu package

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Controlador.HorarioControlador; // Asegurate de importar tus paquetes correctos
import Modelo.Empleado;
import Modelo.Horarios;

public class PantallaEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablaHorarios;
	private JComboBox<String> cmbMes, cmbAnio, cmbTurno;
	private JButton btnRegistrarEntrada, btnRegistrarSalida;
	private HorarioControlador horarioControlador = new HorarioControlador();

	@SuppressWarnings("serial")
	public PantallaEmpleado(Empleado empleado) {
		
		// Icono de la ventana
		setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaEmpleado.class.getResource("/imgenes/logo2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// APLICAMOS LAS MEDIDAS DEL NUEVO DISEÑO
		setBounds(100, 100, 808, 732);
		setResizable(false);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
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

		// FONDO ROJO ORIGINAL
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 805, 705); // Adaptado a la nueva ventana
		contentPane.add(panel);
		panel.setLayout(null);

		// ================== TITULO ==================
		JLabel lblTitulo = new JLabelConContorno("GESTION DE HORARIOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitulo.setForeground(new Color(230, 230, 230));
		lblTitulo.setBounds(357, 44, 404, 40); // Nueva posicion
		panel.add(lblTitulo);

		// ================== FILTROS (IZQUIERDA) ==================
		JLabel lblMes = new JLabel("MES:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMes.setForeground(new Color(230, 230, 230));
		lblMes.setBounds(93, 217, 60, 30);
		panel.add(lblMes);

		cmbMes = new JComboBox<>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
		cmbMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbMes.setBounds(143, 220, 70, 30);
		panel.add(cmbMes);

		JLabel lblAnio = new JLabel("AÑO:");
		lblAnio.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAnio.setForeground(new Color(230, 230, 230));
		lblAnio.setBounds(77, 276, 60, 30);
		panel.add(lblAnio);

		cmbAnio = new JComboBox<>(new String[] { "2024", "2025", "2026", "2027" });
		cmbAnio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbAnio.setBounds(134, 276, 90, 30);
		panel.add(cmbAnio);

		JButton btnVerHoras = new JButton("Ver horarios");
		btnVerHoras.setIcon(new ImageIcon(PantallaEmpleado.class.getResource("/imgenes/icono de buscar.png")));
		btnVerHoras.setBackground(new Color(240, 240, 240)); 
		btnVerHoras.setForeground(Color.BLACK);
		btnVerHoras.setFocusPainted(false);
		btnVerHoras.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnVerHoras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVerHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVerHoras.setBounds(93, 317, 167, 30);
		btnVerHoras.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnVerHoras.setBackground(new Color(60, 63, 65)); 
				btnVerHoras.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				btnVerHoras.setBackground(new Color(240, 240, 240)); 
				btnVerHoras.setForeground(Color.BLACK);
			}
		});
		panel.add(btnVerHoras);

		// ================== REGISTRO (IZQUIERDA) ==================
		JLabel lblTurno = new JLabel("TURNO:");
		lblTurno.setBounds(140, 396, 115, 30);
		lblTurno.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTurno.setForeground(new Color(230, 230, 230));
		panel.add(lblTurno);

		cmbTurno = new JComboBox<>(new String[] { "Mañana", "Tarde" });
		cmbTurno.setBounds(67, 427, 220, 30);
		cmbTurno.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		panel.add(cmbTurno);

		JLabel lblHoraEntrada = new JLabel("ENTRADA:");
		lblHoraEntrada.setBounds(67, 480, 220, 30);
		lblHoraEntrada.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHoraEntrada.setForeground(new Color(230, 230, 230));
		panel.add(lblHoraEntrada);

		btnRegistrarEntrada = new JButton("Registrar Entrada");
		btnRegistrarEntrada.setIcon(new ImageIcon(PantallaEmpleado.class.getResource("/imgenes/icono de siguiente.png")));
		btnRegistrarEntrada.setBackground(new Color(240, 240, 240)); 
		btnRegistrarEntrada.setForeground(Color.BLACK);
		btnRegistrarEntrada.setFocusPainted(false);
		btnRegistrarEntrada.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnRegistrarEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrarEntrada.setBounds(67, 510, 220, 30);
		btnRegistrarEntrada.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnRegistrarEntrada.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnRegistrarEntrada.setBackground(new Color(60, 63, 65)); 
				btnRegistrarEntrada.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				btnRegistrarEntrada.setBackground(new Color(240, 240, 240)); 
				btnRegistrarEntrada.setForeground(Color.BLACK);
			}
		});
		panel.add(btnRegistrarEntrada);

		JLabel lblHoraSalida = new JLabel("SALIDA:");
		lblHoraSalida.setBounds(67, 551, 220, 30);
		lblHoraSalida.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHoraSalida.setForeground(new Color(230, 230, 230));
		panel.add(lblHoraSalida);

		btnRegistrarSalida = new JButton("Registrar Salida");
		btnRegistrarSalida.setIcon(new ImageIcon(PantallaEmpleado.class.getResource("/imgenes/icono de volver.png")));
		btnRegistrarSalida.setBackground(new Color(240, 240, 240)); 
		btnRegistrarSalida.setForeground(Color.BLACK);
		btnRegistrarSalida.setFocusPainted(false);
		btnRegistrarSalida.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnRegistrarSalida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrarSalida.setBounds(67, 581, 220, 30);
		btnRegistrarSalida.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnRegistrarSalida.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnRegistrarSalida.setBackground(new Color(60, 63, 65)); 
				btnRegistrarSalida.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				btnRegistrarSalida.setBackground(new Color(240, 240, 240)); 
				btnRegistrarSalida.setForeground(Color.BLACK);
			}
		});
		panel.add(btnRegistrarSalida);

		// ================== TABLA Y DISEÑO MINIMALISTA ==================
		JLabel lblTablaHoraios = new JLabel("Tabla de Horarios");
		lblTablaHoraios.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTablaHoraios.setForeground(new Color(230, 230, 230));
		lblTablaHoraios.setBounds(447, 131, 300, 30);
		panel.add(lblTablaHoraios);

		tablaHorarios = new JTable();
		tablaHorarios.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Fecha", "Turno", "Hora Entrada", "Hora Salida" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		// Aplicando el diseño de la tabla Padel On pero con tus colores
		tablaHorarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tablaHorarios.setRowHeight(30); // Filas mas altas
		tablaHorarios.setShowVerticalLines(false); // Sin lineas verticales
		tablaHorarios.setGridColor(Color.LIGHT_GRAY); 
		tablaHorarios.setSelectionBackground(new Color(184, 207, 229)); // Celeste original tuyo
		tablaHorarios.setSelectionForeground(Color.BLACK);

		// Renderizador unificado: Centra el texto y aplica colores intercalados
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.CENTER); // Centrado
				
				if (!isSelected) {
					c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : new Color(230, 230, 230));
				}
				return c;
			}
		};
		for (int i = 0; i < tablaHorarios.getColumnCount(); i++) {
			tablaHorarios.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}

		JTableHeader header = tablaHorarios.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 14));
		header.setBackground(new Color(200, 0, 0)); // Tu rojo oscuro
		header.setForeground(Color.WHITE);
		header.setReorderingAllowed(false);
		header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE)); // Borde inferior limpio

		JScrollPane scrollPane = new JScrollPane(tablaHorarios);
		scrollPane.setBounds(346, 160, 415, 490);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
		panel.add(scrollPane);

		// ================== LOGO REDIMENSIONADO ==================
		ImageIcon originalIcon = new ImageIcon(Inicio.class.getResource("/imgenes/logo hd.png"));
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(140, 140, Image.SCALE_SMOOTH); // Logo mas chico para que entre
		JLabel lblLogoGrande = new JLabel(new ImageIcon(PantallaEmpleado.class.getResource("/imgenes/logo1.png")));
		lblLogoGrande.setBounds(10, 0, 250, 190);
		panel.add(lblLogoGrande);

		// ================== LÓGICA ORIGINAL ==================
		LocalTime ahora2 = LocalTime.now();
		if (ahora2.isBefore(LocalTime.of(15, 0))) {
		    cmbTurno.setSelectedItem("Mañana");
		} else {
		    cmbTurno.setSelectedItem("Tarde");
		}

		LocalDate ahora = LocalDate.now();
		String mesActual = String.format("%02d", ahora.getMonthValue());
		String anioActual = String.valueOf(ahora.getYear());
		cmbMes.setSelectedItem(mesActual);
		cmbAnio.setSelectedItem(anioActual);

		cargarHorarios(empleado, mesActual, anioActual);

		

		btnVerHoras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mes = cmbMes.getSelectedItem().toString();
				String anio = cmbAnio.getSelectedItem().toString();
				cargarHorarios(empleado, mes, anio);
			}
		});

		btnRegistrarEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String turno = cmbTurno.getSelectedItem().toString();
				LocalTime horaActual = LocalTime.now().withSecond(0).withNano(0);
				LocalDate hoy = LocalDate.now();
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
				String horaFormateadaStr = horaActual.format(formatoHora);
				String fechaStr = hoy.toString();

				if (horarioControlador.yaTieneEntrada(empleado.getId(), fechaStr, turno)) {
					JOptionPane.showMessageDialog(null, "Ya registraste una entrada para el turno " + turno + " hoy.");
					return;
				}

				boolean ok = horarioControlador.registrarEntrada(empleado.getId(), fechaStr, turno, horaFormateadaStr);
				if (ok) {
					JOptionPane.showMessageDialog(null, "Entrada registrada.");
					String mes = cmbMes.getSelectedItem().toString();
					String anio = cmbAnio.getSelectedItem().toString();
					cargarHorarios(empleado, mes, anio);
				} else {
					JOptionPane.showMessageDialog(null, "Error al registrar entrada.");
				}
			}
		});

		btnRegistrarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String turno = cmbTurno.getSelectedItem().toString();
				LocalTime horaActual = LocalTime.now().withSecond(0).withNano(0);
				LocalDate hoy = LocalDate.now();
				String fechaStr = hoy.toString();

				if (horarioControlador.yaTieneSalida(empleado.getId(), fechaStr, turno)) {
					JOptionPane.showMessageDialog(null, "Ya registraste una salida para el turno " + turno + " hoy.");
					return;
				}

				if (!horarioControlador.tieneEntradaSinSalida(empleado.getId(), fechaStr, turno)) {
					JOptionPane.showMessageDialog(null, "No tenés registrada una entrada para el turno " + turno + ".");
					return;
				}

				boolean ok = horarioControlador.registrarSalida(empleado.getId(), hoy, turno, horaActual);
				if (ok) {
					JOptionPane.showMessageDialog(null, "Salida registrada.");
					String mes = cmbMes.getSelectedItem().toString();
					String anio = cmbAnio.getSelectedItem().toString();
					cargarHorarios(empleado, mes, anio);
				} else {
					JOptionPane.showMessageDialog(null, "Error al registrar salida.");
				}
			}
		});
	}

	private void cargarHorarios(Empleado empleado, String mes, String anio) {
		DefaultTableModel modelo = (DefaultTableModel) tablaHorarios.getModel();
		modelo.setRowCount(0); 

		int mesInt = Integer.parseInt(mes);
		int anioInt = Integer.parseInt(anio);

		List<Horarios> lista = horarioControlador.obtenerHorariosPorEmpleadoYMes(empleado.getId(), mesInt, anioInt);

		for (Horarios h : lista) {
			modelo.addRow(new Object[] { h.getFecha().toString(), h.getTurno(), h.getHoraEntrada().toString(),
					h.getHoraSalida() != null ? h.getHoraSalida().toString() : "" });
		}
	}
}