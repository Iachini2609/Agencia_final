package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;
import javax.swing.JList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Controlador.EmpleadoControlador;
import Controlador.HorarioControlador;
import Controlador.SueldoControlador;
import Modelo.Empleado;
import Modelo.Horarios;
import Modelo.Sueldo;

public class GestionEmpleado extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> cmbMes;
	private JComboBox<String> cmbAnio;
	private JTextField txtValorHora;
	private JTable tablaHorarios;
	private DefaultTableModel modeloTabla;
	private boolean modoEdicion = false;
	private JLabel lblEmpleado;
	private HorarioControlador horarioControlador;
	private SueldoControlador sueldoControlador;
	private Empleado empleado;
	private JTextField txtResultado;
	private EmpleadoControlador empleadoControlador;
	private JComboBox comboBox;

	@SuppressWarnings("serial")
	public GestionEmpleado() {

		sueldoControlador = new SueldoControlador();
		empleadoControlador = new EmpleadoControlador("sqlite:Agencia.db");
		
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, -12, 930, 730);
		add(panel);
		panel.setLayout(null);

		JLabel lblTitulo = new JLabelConContorno("Gestión de Empleado");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitulo.setBounds(301, 20, 327, 40);
		panel.add(lblTitulo);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setForeground(Color.WHITE);
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDni.setBounds(43, 151, 140, 25);
		panel.add(lblDni);

		JLabel lblMes = new JLabel("Mes:");
		lblMes.setForeground(Color.WHITE);
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMes.setBounds(43, 277, 50, 25);
		panel.add(lblMes);

		// Crear combo de meses
		cmbMes = new JComboBox<>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
		cmbMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbMes.setBounds(119, 278, 80, 25);
		panel.add(cmbMes);

		JLabel lblAnio = new JLabel("Año:");
		lblAnio.setForeground(Color.WHITE);
		lblAnio.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAnio.setBounds(43, 241, 50, 25);
		panel.add(lblAnio);

		// Crear combo de años
		cmbAnio = new JComboBox<>(new String[] { "2024", "2025", "2026" });
		cmbAnio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbAnio.setBounds(119, 242, 80, 25);
		panel.add(cmbAnio);

		JLabel lblValorHora = new JLabel("Valor por hora ($):");
		lblValorHora.setForeground(Color.WHITE);
		lblValorHora.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValorHora.setBounds(10, 503, 145, 25);
		panel.add(lblValorHora);

		txtValorHora = new JTextField();
		txtValorHora.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtValorHora.setBounds(153, 503, 69, 25);
		panel.add(txtValorHora);

		JButton btnConsultar = new JButton("Ver Horarios");
		btnConsultar.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono de historial.png")));
		btnConsultar.setBackground(new Color(240, 240, 240)); // gris claro
		btnConsultar.setForeground(Color.BLACK);
		btnConsultar.setFocusPainted(false);
		btnConsultar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnConsultar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConsultar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConsultar.setBounds(29, 313, 170, 35);
		btnConsultar.setContentAreaFilled(true);
		btnConsultar.setOpaque(true); 
		btnConsultar.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnConsultar.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnConsultar.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnConsultar.setBackground(new Color(240, 240, 240)); // gris claro
				btnConsultar.setForeground(Color.BLACK);
			}
		});
		panel.add(btnConsultar);

		JButton btnSueldo = new JButton("Calcular Sueldo");
		btnSueldo.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono dinero.png")));
		btnSueldo.setBackground(new Color(240, 240, 240)); // gris claro
		btnSueldo.setForeground(Color.BLACK);
		btnSueldo.setFocusPainted(false);
		btnSueldo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnSueldo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSueldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSueldo.setBounds(29, 457, 176, 35);
		btnSueldo.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnSueldo.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnSueldo.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnSueldo.setBackground(new Color(240, 240, 240)); // gris claro
				btnSueldo.setForeground(Color.BLACK);
			}
		});
		panel.add(btnSueldo);

		JButton btnAguinaldo_1 = new JButton("Calcular Aguinaldo 1°");
		btnAguinaldo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcularYGenerarAguinaldoPDF(true);
			}
		});
		btnAguinaldo_1.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono de aguinaldo.png")));
		btnAguinaldo_1.setBackground(new Color(240, 240, 240)); // gris claro
		btnAguinaldo_1.setForeground(Color.BLACK);
		btnAguinaldo_1.setFocusPainted(false);
		btnAguinaldo_1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnAguinaldo_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAguinaldo_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAguinaldo_1.setBounds(701, 200, 176, 35);
		btnAguinaldo_1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnAguinaldo_1.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnAguinaldo_1.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnAguinaldo_1.setBackground(new Color(240, 240, 240)); // gris claro
				btnAguinaldo_1.setForeground(Color.BLACK);
			}
		});
		panel.add(btnAguinaldo_1);

		// Tabla de horarios
		modeloTabla = new DefaultTableModel(new String[] { "Fecha", "Turno", "Entrada", "Salida", "Feriado" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Solo permitir editar Turno (1), Entrada (2) y Salida (3) si está en modo
				// edición
				if (modoEdicion) {
					return column == 1 || column == 2 || column == 3;
				}
				return false;
			}
		};

		// Crear la tabla
		tablaHorarios = new JTable(modeloTabla);
		tablaHorarios.setFont(new Font("Arial", Font.PLAIN, 14));
		tablaHorarios.setRowHeight(24);
		tablaHorarios.setShowGrid(true);
		tablaHorarios.setGridColor(Color.LIGHT_GRAY);
		tablaHorarios.setIntercellSpacing(new Dimension(1, 1));

		// Encabezado estilizado
		JTableHeader header = tablaHorarios.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 15));
		header.setBackground(new Color(200, 0, 0)); // rojo oscuro
		header.setForeground(Color.WHITE);
		header.setReorderingAllowed(false);

		// Zebra striping: filas alternadas con diferentes colores
		tablaHorarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				String feriadoStr = (String) tablaHorarios.getValueAt(row, 4);
				boolean esFeriado = feriadoStr.equalsIgnoreCase("SI");

				if (isSelected) {
					c.setBackground(new Color(184, 207, 229)); // celeste
				} else if (esFeriado) {
					c.setBackground(new Color(255, 230, 200)); // naranja claro
				} else {
					c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : new Color(230, 230, 230));
				}

				return c;
			}
		});

		// Centrar el contenido de todas las celdas
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaHorarios.getColumnCount(); i++) {
			tablaHorarios.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}

		// Tooltip al pasar el mouse sobre una celda
		tablaHorarios.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int row = tablaHorarios.rowAtPoint(e.getPoint());
				int col = tablaHorarios.columnAtPoint(e.getPoint());
				if (row > -1 && col > -1) {
					Object value = tablaHorarios.getValueAt(row, col);
					tablaHorarios.setToolTipText(value != null ? value.toString() : "");
				}
			}
		});

		// Crear scroll con la tabla y agregar al panel
		JScrollPane scrollTabla = new JScrollPane(tablaHorarios);
		scrollTabla.setBounds(239, 70, 452, 613);
		panel.add(scrollTabla);
		
		txtResultado = new JTextField();
		txtResultado.setBounds(45, 539, 150, 30);
		txtResultado.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtResultado.setEditable(false);
		txtResultado.setHorizontalAlignment(JTextField.CENTER); // centrado opcional
		panel.add(txtResultado);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono modificar.png")));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaHorarios.getSelectedRow();
				if (fila == -1) {
					JOptionPane.showMessageDialog(null, "Seleccioná una fila para modificar.");
					return;
				}

				// Siempre abre directamente la ventana de edición
				mostrarVentanaEdicion(fila);
			}
		});
		btnModificar.setBackground(new Color(240, 240, 240)); // gris claro
		btnModificar.setForeground(Color.BLACK);
		btnModificar.setFocusPainted(false);
		btnModificar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificar.setBounds(701, 329, 176, 35);
		btnModificar.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnModificar.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnModificar.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnModificar.setBackground(new Color(240, 240, 240)); // gris claro
				btnModificar.setForeground(Color.BLACK);
			}
		});
		panel.add(btnModificar);

		JButton btnPDFSueldo = new JButton("Generar PDF ");
		btnPDFSueldo.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono de reportes.png")));
		btnPDFSueldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try {
			        // 🔸 Asegurar empleado seleccionado (y con ID)
			        Empleado empSel = obtenerEmpleadoSeleccionado();
			        if (empSel == null) {
			            JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado.");
			            return;
			        }
			        // (opcional) mantener compatibilidad con el resto del código
			        empleado = empSel;

			        String mes = cmbMes.getSelectedItem().toString();
			        String anio = cmbAnio.getSelectedItem().toString();

			        horarioControlador = new HorarioControlador();
			        List<Horarios> listaHorarios = horarioControlador.obtenerHorariosPorMes(
			                empSel.getId(), Integer.parseInt(mes), Integer.parseInt(anio));

			        if (listaHorarios.isEmpty()) {
			            JOptionPane.showMessageDialog(null, "No hay horarios registrados para este mes.");
			            return;
			        }

			        Document document = new Document();
			        String escritorio = obtenerRutaEscritorio();

			        File carpetaEmpleados = new File(escritorio + File.separator + "Empleados Sueldos");
			        if (!carpetaEmpleados.exists()) carpetaEmpleados.mkdir();

			        String nombreCarpetaEmpleado = empSel.getNombre() + "_" + empSel.getApellido();
			        File carpetaEmpleado = new File(carpetaEmpleados, nombreCarpetaEmpleado);
			        if (!carpetaEmpleado.exists()) carpetaEmpleado.mkdir();

			        String nombreArchivo = "Sueldo_" + mes + "_" + anio + ".pdf";
			        File archivoPDF = new File(carpetaEmpleado, nombreArchivo);

			        PdfWriter.getInstance(document, new FileOutputStream(archivoPDF));
			        document.open();

			        document.add(new Paragraph("Recibo de Sueldo"));
			        document.add(new Paragraph("Empleado: " + empSel.getNombre() + " " + empSel.getApellido()));
			        document.add(new Paragraph("Mes/Año: " + mes + "/" + anio));
			        document.add(new Paragraph(" "));

			        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(6);
			        table.addCell("Fecha");
			        table.addCell("Turno");
			        table.addCell("Hora Entrada");
			        table.addCell("Hora Salida");
			        table.addCell("Horas Trabajadas");
			        table.addCell("Feriado");

			        double horasNormales = 0.0;
			        double horasFeriado = 0.0;
			        double valorHora = Double.parseDouble(txtValorHora.getText().trim());

			        for (Horarios h : listaHorarios) {
			            table.addCell(h.getFecha().toString());
			            table.addCell(h.getTurno());
			            table.addCell(String.valueOf(h.getHoraEntrada()));
			            table.addCell(String.valueOf(h.getHoraSalida()));

			            try {
			                LocalTime entrada = h.getHoraEntrada();
			                LocalTime salida  = h.getHoraSalida();
			                Duration duracion = Duration.between(entrada, salida);
			                double horas = duracion.toMinutes() / 60.0;

			                if (h.isFeriado()) horasFeriado += horas;
			                else horasNormales += horas;

			                table.addCell(String.format("%.2f", horas));
			            } catch (Exception ex) {
			                table.addCell("N/A");
			            }
			            table.addCell(h.isFeriado() ? "Sí" : "No");
			        }

			        document.add(table);

			        double sueldo = (horasNormales * valorHora) + (horasFeriado * valorHora * 2);
			        double totalHoras = horasNormales + horasFeriado;

			        document.add(new Paragraph("Total de horas trabajadas: " + String.format("%.2f", totalHoras)));
			        document.add(new Paragraph("Valor por hora: $" + valorHora));
			        document.add(new Paragraph("Sueldo total: $" + String.format("%.2f", sueldo)));

			        document.close();
			        JOptionPane.showMessageDialog(null, "PDF generado correctamente: " + nombreArchivo);

			    } catch (Exception ex) {
			        JOptionPane.showMessageDialog(null, "Error al generar PDF: " + ex.getMessage());
			    }
			}
		});

		btnPDFSueldo.setForeground(Color.BLACK);
		btnPDFSueldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPDFSueldo.setFocusPainted(false);
		btnPDFSueldo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPDFSueldo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnPDFSueldo.setBackground(new Color(240, 240, 240));
		btnPDFSueldo.setBounds(9, 648, 219, 35);
		btnPDFSueldo.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnPDFSueldo.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnPDFSueldo.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnPDFSueldo.setBackground(new Color(240, 240, 240)); // gris claro
				btnPDFSueldo.setForeground(Color.BLACK);
			}
		});
		panel.add(btnPDFSueldo);

		JButton btnFeriado = new JButton("Dia Feriado");
		btnFeriado.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono de reservas.png")));
		btnFeriado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablaHorarios.getSelectedRow();
				if (filaSeleccionada != -1) {
					// Obtener el ID del horario desde la primera columna oculta
					int horarioId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);

					// Alternar valor actual
					String valorActual = (String) modeloTabla.getValueAt(filaSeleccionada, 5);
					boolean nuevoValor = !valorActual.equalsIgnoreCase("SI");

					// Actualizar en base de datos
					HorarioControlador hc = new HorarioControlador();
					boolean ok = hc.actualizarFeriado(horarioId, nuevoValor);
					hc.cerrarConexion();

					if (ok) {
						modeloTabla.setValueAt(nuevoValor ? "SI" : "NO", filaSeleccionada, 5);
						JOptionPane.showMessageDialog(null, "Feriado actualizado correctamente.");
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar feriado.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccioná una fila primero.");
				}
			}
		});
		btnFeriado.setBackground(new Color(240, 240, 240)); // gris claro
		btnFeriado.setForeground(Color.BLACK);
		btnFeriado.setFocusPainted(false);
		btnFeriado.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnFeriado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFeriado.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFeriado.setBounds(29, 359, 170, 35);
		btnFeriado.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnFeriado.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnFeriado.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnFeriado.setBackground(new Color(240, 240, 240)); // gris claro
				btnFeriado.setForeground(Color.BLACK);
			}
		});
		panel.add(btnFeriado);

		lblEmpleado = new JLabel("Empleado: ");
		lblEmpleado.setForeground(new Color(230, 230, 230));
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmpleado.setBounds(29, 76, 150, 25);
		panel.add(lblEmpleado);

		// Acciones
		btnConsultar.addActionListener(e -> cargarHorarios());
		btnSueldo.addActionListener(e -> calcularSueldo());

		// Obtener fecha actual
		LocalDate hoy = LocalDate.now();
		String mesActual = String.format("%02d", hoy.getMonthValue()); // ejemplo: "07"
		String anioActual = String.valueOf(hoy.getYear()); // ejemplo: "2025"

		// Seleccionar el mes y año actual en los combos
		cmbMes.setSelectedItem(mesActual);
		cmbAnio.setSelectedItem(anioActual);

		JButton btnCalcularAguinaldo = new JButton("Calcular Aguinaldo 2°");
		btnCalcularAguinaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcularYGenerarAguinaldoPDF(false);
			}
		});
		btnCalcularAguinaldo.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono de aguinaldo.png")));
		btnCalcularAguinaldo.setForeground(Color.BLACK);
		btnCalcularAguinaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCalcularAguinaldo.setFocusPainted(false);
		btnCalcularAguinaldo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCalcularAguinaldo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnCalcularAguinaldo.setBackground(new Color(240, 240, 240));
		btnCalcularAguinaldo.setBounds(701, 246, 176, 35);
		btnCalcularAguinaldo.setContentAreaFilled(true);
		btnCalcularAguinaldo.setOpaque(true); 
		btnCalcularAguinaldo.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnCalcularAguinaldo.setBackground(new Color(60, 63, 65)); // gris oscuro
				btnCalcularAguinaldo.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				btnCalcularAguinaldo.setBackground(new Color(240, 240, 240)); // gris claro
				btnCalcularAguinaldo.setForeground(Color.BLACK);
			}
		});
		panel.add(btnCalcularAguinaldo);
		
		comboBox = new JComboBox<>();           // <Empleado> si te deja aquí, mejor: new JComboBox<Empleado>();
		comboBox.setBounds(29, 104, 189, 25);
		panel.add(comboBox);
		
		JTextField txtDni = new JTextField();
		txtDni.setBounds(43, 175, 145, 25);
		txtDni.setEditable(false);
		panel.add(txtDni);
		
		cargarEmpleadosEnCombo(comboBox, txtDni);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(GestionEmpleado.class.getResource("/imgenes/icono logo 23.png")));
		lblNewLabel_1.setBounds(651, 20, 292, 169);
		panel.add(lblNewLabel_1);

		
	}

	@SuppressWarnings({ "serial" })
	private void cargarHorarios() {
	    // 1) Mes y año
	    int mes, anio;
	    try {
	        mes  = Integer.parseInt((String) cmbMes.getSelectedItem());
	        anio = Integer.parseInt((String) cmbAnio.getSelectedItem());
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Mes o año inválidos.");
	        return;
	    }

	    // 2) Empleado seleccionado desde el combo
	    Empleado seleccionado = (Empleado) comboBox.getSelectedItem();
	    if (seleccionado == null) {
	        JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado.");
	        return;
	    }

	    // 3) Asegurar que tenemos el ID del empleado (si no está, lo buscamos por DNI)
	    Empleado empleadoSel = seleccionado;
	    EmpleadoControlador ec = null;
	    if (empleadoSel.getId() == 0) { // ajusta la condición si tu ID "no asignado" es otro valor
	        ec = new EmpleadoControlador("Agencia.db");
	        Empleado desdeBD = ec.buscarPorDni(empleadoSel.getDni());
	        if (desdeBD == null) {
	            JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
	            if (ec != null) ec.cerrarConexion();
	            return;
	        }
	        empleadoSel = desdeBD;
	    }

	    // ✅ Guardar en el campo de la clase para que no quede null en otros métodos
	    this.empleado = empleadoSel;

	    // 4) Etiqueta con el nombre y apellido del empleado
	    lblEmpleado.setText("Empleado: " + empleadoSel.getNombre() + " " + empleadoSel.getApellido());

	    // 5) Traer horarios del mes/año
	    HorarioControlador hc = new HorarioControlador();
	    List<Horarios> horarios = hc.obtenerHorariosPorMes(empleadoSel.getId(), mes, anio);

	    // 6) Modelo de la tabla (no editable)
	    modeloTabla = new DefaultTableModel(
	        new Object[][] {},
	        new String[] { "ID", "Fecha", "Turno", "Entrada", "Salida", "Feriado" }
	    ) {
	        @Override
	        public boolean isCellEditable(int row, int column) { return false; }
	    };

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	    for (Horarios h : horarios) {
	        modeloTabla.addRow(new Object[] {
	            h.getId(),
	            h.getFecha().toString(), // ya es String
	            h.getTurno(),
	            (h.getHoraEntrada() != null ? h.getHoraEntrada().format(formatter) : ""),
	            (h.getHoraSalida()  != null ? h.getHoraSalida().format(formatter)  : ""),
	            h.isFeriado() ? "SI" : "NO"
	        });
	    }

	    // 7) Asignar modelo a la tabla
	    tablaHorarios.setModel(modeloTabla);

	    // 8) Ocultar columna ID
	    tablaHorarios.getColumnModel().getColumn(0).setMinWidth(0);
	    tablaHorarios.getColumnModel().getColumn(0).setMaxWidth(0);
	    tablaHorarios.getColumnModel().getColumn(0).setWidth(0);
	    tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(0);

	    // 9) Cierre de conexiones
	    hc.cerrarConexion();
	    if (ec != null) ec.cerrarConexion();

	    // 10) Mensaje si no hay horarios
	    if (modeloTabla.getRowCount() == 0) {
	        JOptionPane.showMessageDialog(this, "No hay horarios cargados para el período seleccionado.");
	    }
	}
	
	
	private void mostrarVentanaEdicion(int fila) {
		JDialog dialogo = new JDialog((Frame) null, "Editar horario", true);
		dialogo.setSize(360, 440);
		dialogo.getContentPane().setLayout(null);
		dialogo.getContentPane().setBackground(new Color(200, 0, 0));
		dialogo.setResizable(false);

		Font fuente = new Font("Tahoma", Font.BOLD, 16);

		int y = 20;
		int espaciado = 40;

		JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setFont(fuente);
		lblFecha.setBounds(20, y, 300, 25);
		dialogo.getContentPane().add(lblFecha);

		JTextField txtFecha = new JTextField(modeloTabla.getValueAt(fila, 1).toString());
		txtFecha.setFont(fuente);
		txtFecha.setBounds(20, y += 25, 300, 25);
		dialogo.getContentPane().add(txtFecha);

		JLabel lblTurno = new JLabel("Turno:");
		lblTurno.setForeground(Color.WHITE);
		lblTurno.setFont(fuente);
		lblTurno.setBounds(20, y += espaciado, 300, 25);
		dialogo.getContentPane().add(lblTurno);

		JTextField txtTurno = new JTextField(modeloTabla.getValueAt(fila, 2).toString());
		txtTurno.setFont(fuente);
		txtTurno.setBounds(20, y += 25, 300, 25);
		dialogo.getContentPane().add(txtTurno);

		JLabel lblEntrada = new JLabel("Entrada (HH:MM):");
		lblEntrada.setForeground(Color.WHITE);
		lblEntrada.setFont(fuente);
		lblEntrada.setBounds(20, y += espaciado, 300, 25);
		dialogo.getContentPane().add(lblEntrada);

		JTextField txtEntrada = new JTextField(modeloTabla.getValueAt(fila, 3).toString());
		txtEntrada.setFont(fuente);
		txtEntrada.setBounds(20, y += 25, 300, 25);
		dialogo.getContentPane().add(txtEntrada);

		JLabel lblSalida = new JLabel("Salida (HH:MM):");
		lblSalida.setForeground(Color.WHITE);
		lblSalida.setFont(fuente);
		lblSalida.setBounds(20, y += espaciado, 300, 25);
		dialogo.getContentPane().add(lblSalida);

		JTextField txtSalida = new JTextField(modeloTabla.getValueAt(fila, 4).toString());
		txtSalida.setFont(fuente);
		txtSalida.setBounds(20, y += 25, 300, 25);
		dialogo.getContentPane().add(txtSalida);

		JLabel lblFeriado = new JLabel("Feriado (SI/NO):");
		lblFeriado.setForeground(Color.WHITE);
		lblFeriado.setFont(fuente);
		lblFeriado.setBounds(20, y += espaciado, 300, 25);
		dialogo.getContentPane().add(lblFeriado);

		JTextField txtFeriado = new JTextField(modeloTabla.getValueAt(fila, 5).toString());
		txtFeriado.setFont(fuente);
		txtFeriado.setBounds(20, y += 25, 300, 25);
		dialogo.getContentPane().add(txtFeriado);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(20, y += 40, 130, 35);
		btnGuardar.setFont(fuente);
		btnGuardar.setBackground(new Color(240, 240, 240));
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFocusPainted(false);
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		dialogo.getContentPane().add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(190, y, 130, 35);
		btnCancelar.setFont(fuente);
		btnCancelar.setBackground(new Color(240, 240, 240));
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setFocusPainted(false);
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		dialogo.getContentPane().add(btnCancelar);

		// Efecto hover
		MouseAdapter hover = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JButton btn = (JButton) e.getSource();
				btn.setBackground(new Color(60, 63, 65));
				btn.setForeground(Color.WHITE);
			}

			public void mouseExited(MouseEvent e) {
				JButton btn = (JButton) e.getSource();
				btn.setBackground(new Color(240, 240, 240));
				btn.setForeground(Color.BLACK);
			}
		};

		btnGuardar.addMouseListener(hover);
		btnCancelar.addMouseListener(hover);

		btnGuardar.addActionListener(e -> {
		    String fecha = txtFecha.getText().trim();
		    String turno = txtTurno.getText().trim();
		    String entrada = txtEntrada.getText().trim();
		    String salida = txtSalida.getText().trim();
		    String feriadoTexto = txtFeriado.getText().trim().toUpperCase(); 

		    // Validaciones
		    if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
		        JOptionPane.showMessageDialog(dialogo, "La fecha debe ser formato AAAA-MM-DD.");
		        return;
		    }
		    if (!entrada.matches("\\d{2}:\\d{2}") || !salida.matches("\\d{2}:\\d{2}")) {
		        JOptionPane.showMessageDialog(dialogo, "La hora debe ser formato HH:MM.");
		        return;
		    }
		    if (!feriadoTexto.equals("SI") && !feriadoTexto.equals("NO")) {
		        JOptionPane.showMessageDialog(dialogo, "El campo 'Feriado' debe ser SI o NO (en mayúsculas).");
		        return;
		    }

		    try {
		        LocalDate.parse(fecha);
		        LocalTime.parse(entrada);
		        LocalTime.parse(salida);
		    } catch (DateTimeParseException ex) {
		        JOptionPane.showMessageDialog(dialogo, "Fecha u hora inválida.");
		        return;
		    }

		    // ACA ESTA LA CLAVE: Obtenemos el ID de la fila que estamos editando
		    int horarioId = (int) modeloTabla.getValueAt(fila, 0);

		    // Actualizamos la tabla visual
		    modeloTabla.setValueAt(fecha, fila, 1);
		    modeloTabla.setValueAt(turno, fila, 2);
		    modeloTabla.setValueAt(entrada, fila, 3);
		    modeloTabla.setValueAt(salida, fila, 4);
		    modeloTabla.setValueAt(feriadoTexto, fila, 5); 

		    // Le pasamos el ID a la base de datos
		    actualizarHorarioEnBD(horarioId, fecha, turno, entrada, salida, feriadoTexto);
		    
		    dialogo.dispose();
		    JOptionPane.showMessageDialog(null, "Horario actualizado.");
		});

		btnCancelar.addActionListener(e -> dialogo.dispose());

		dialogo.setLocationRelativeTo(null);
		dialogo.setVisible(true);
	}

	private void calcularSueldo() {
		try {
			String textoValorHora = txtValorHora.getText().trim();
			if (textoValorHora.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, ingresá un valor por hora para calcular el sueldo.");
				return;
			}

			double valorHora;
			try {
				valorHora = Double.parseDouble(textoValorHora);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El valor por hora debe ser un número válido.");
				return;
			}

			double horasNormales = 0;
			double horasFeriado = 0;

			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				String entrada = modeloTabla.getValueAt(i, 3).toString().trim();
				String salida = modeloTabla.getValueAt(i, 4).toString().trim();
				String feriadoStr = modeloTabla.getValueAt(i, 5).toString().trim();
				boolean esFeriado = feriadoStr.equalsIgnoreCase("SI");

				LocalTime hEntrada = LocalTime.parse(entrada);
				LocalTime hSalida = LocalTime.parse(salida);

				Duration duracion = Duration.between(hEntrada, hSalida);
				double horas = duracion.toMinutes() / 60.0;

				if (esFeriado) {
					horasFeriado += horas;
				} else {
					horasNormales += horas;
				}
			}

			double sueldoTotal = (horasNormales * valorHora) + (horasFeriado * valorHora * 2);
			txtResultado.setText(String.format("$%,.2f", sueldoTotal));

			// ✅ Obtener empleado seleccionado desde el combo
			Empleado seleccionado = (Empleado) comboBox.getSelectedItem();
			if (seleccionado == null) {
			    JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado.");
			    return;
			}

			// Usar el DNI del empleado seleccionado
			String dni = seleccionado.getDni();

			EmpleadoControlador empleadoControlador = new EmpleadoControlador("Agencia.db");
			Empleado empleado = empleadoControlador.buscarPorDni(dni);
			if (empleado == null) {
			    JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
			    return;
			}

			if (modeloTabla.getRowCount() == 0) {
			    JOptionPane.showMessageDialog(this, "No hay horarios cargados.");
			    return;
			}
			String fechaStr = modeloTabla.getValueAt(0, 1).toString(); // columna "Fecha"
			LocalDate fecha = LocalDate.parse(fechaStr); // ← Esto sirve si la fecha está como "2025-07-01"
			int mes = fecha.getMonthValue();
			int anio = fecha.getYear();

			// Crear objeto Sueldo
			Sueldo sueldo = new Sueldo(empleado.getId(), mes, anio, valorHora, horasNormales, horasFeriado,
					sueldoTotal);

			// Guardar en base de datos
			SueldoControlador sueldoControlador = new SueldoControlador();
			sueldoControlador.guardarOActualizarSueldo(sueldo);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al calcular sueldo.");
		}
	}
	// Si el objeto del combo no trae ID, lo busca por DNI para obtenerlo.
	public Empleado obtenerEmpleadoSeleccionado() {
	    Empleado seleccionado = (Empleado) comboBox.getSelectedItem();
	    if (seleccionado == null) return null;

	    if (seleccionado.getId() != 0) return seleccionado; // ya tiene ID

	    EmpleadoControlador ec = new EmpleadoControlador("Agencia.db");
	    try {
	        return ec.buscarPorDni(seleccionado.getDni());
	    } finally {
	        ec.cerrarConexion();
	    }
	}

	public void calcularYGenerarAguinaldoPDF(boolean primerSemestre) {
	    if (this.empleado == null) {
	        JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado.");
	        return;
	    }

	    int desdeMes = primerSemestre ? 1 : 7;
	    int hastaMes = primerSemestre ? 6 : 12;
	    int anioActual = LocalDate.now().getYear();

	    List<Sueldo> sueldosDelPeriodo = sueldoControlador
	            .obtenerSueldosPorEmpleadoYPeriodo(empleado.getId(), desdeMes, hastaMes, anioActual);

	    if (sueldosDelPeriodo.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay sueldos registrados para este período.");
	        return;
	    }

	    double mejorSueldo = sueldosDelPeriodo.stream()
	            .mapToDouble(Sueldo::getSueldoTotal)
	            .max()
	            .orElse(0);

	    double aguinaldo = mejorSueldo / 2;

	    try {
	        String nombreArchivo = "Aguinaldo_" 
	                + (primerSemestre ? "Primer" : "Segundo") 
	                + "_Semestre_" + anioActual + ".pdf";

	        String ruta = obtenerRutaEscritorio() + "/Empleados Aguinaldos/"
	                + empleado.getNombre() + "_" + empleado.getApellido() + "/";
	        Files.createDirectories(Paths.get(ruta));

	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(ruta + nombreArchivo));
	        document.open();

	        document.add(new Paragraph("Aguinaldo - " 
	                + (primerSemestre ? "Primer" : "Segundo") + " Semestre " + anioActual));
	        document.add(new Paragraph("Empleado: " 
	                + empleado.getNombre() + " " + empleado.getApellido()));
	        document.add(new Paragraph(" "));

	        PdfPTable tabla = new PdfPTable(2);
	        tabla.addCell("Mes");
	        tabla.addCell("Sueldo");

	        for (Sueldo s : sueldosDelPeriodo) {
	            tabla.addCell(String.valueOf(s.getMes()));
	            tabla.addCell("$" + String.format("%.2f", s.getSueldoTotal()));
	        }

	        document.add(tabla);

	        document.add(new Paragraph(" "));
	        document.add(new Paragraph("Mejor sueldo: $" + String.format("%.2f", mejorSueldo)));
	        document.add(new Paragraph("Aguinaldo calculado: $" + String.format("%.2f", aguinaldo)));

	        document.close();

	        JOptionPane.showMessageDialog(null, "PDF del aguinaldo generado correctamente.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al generar el PDF del aguinaldo.");
	    }
	}

	private void actualizarHorarioEnBD(int horarioId, String fecha, String turno, String entrada, String salida, String feriadoTexto) {
	    boolean feriado = feriadoTexto.equalsIgnoreCase("SI");

	    HorarioControlador hc = new HorarioControlador();
	    // Le mandamos el ID único al controlador
	    hc.actualizarHorario(horarioId, fecha, turno, entrada, salida, feriado);

	    // Cerramos la conexión
	    hc.cerrarConexion();
	}
	
	public void cargarEmpleadosEnCombo(JComboBox<Empleado> comboBox, JTextField txtDni) {
	    // 1) Traer empleados
	    List<Empleado> empleados = empleadoControlador.obtenerTodosLosEmpleados();

	    // 2) Modelo
	    DefaultComboBoxModel<Empleado> modelo = new DefaultComboBoxModel<>();
	    for (Empleado emp : empleados) modelo.addElement(emp);
	    comboBox.setModel(modelo);

	    // 3) Renderer (Nombre + Apellido)
	    comboBox.setRenderer(new DefaultListCellRenderer() {
	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	                                                      boolean isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (value instanceof Empleado) {
	                Empleado emp = (Empleado) value;
	                setText(emp.getNombre() + " " + emp.getApellido());
	            }
	            return this;
	        }
	    });

	    // 4) Sincronizar SIEMPRE el campo this.empleado y txtDni/label cuando cambia el combo
	    comboBox.addActionListener(e -> {
	        Empleado sel = obtenerEmpleadoSeleccionado();
	        if (sel != null) {
	            this.empleado = sel; // ✅ clave para que no sea null al generar PDF
	            if (txtDni != null) txtDni.setText(sel.getDni());
	            lblEmpleado.setText("Empleado: " + sel.getNombre() + " " + sel.getApellido());
	        }
	    });

	    // 5) Selección inicial (por si el usuario no toca el combo)
	    if (modelo.getSize() > 0) {
	        comboBox.setSelectedIndex(0);
	        Empleado sel = obtenerEmpleadoSeleccionado();
	        if (sel != null) {
	            this.empleado = sel;
	            if (txtDni != null) txtDni.setText(sel.getDni());
	            lblEmpleado.setText("Empleado: " + sel.getNombre() + " " + sel.getApellido());
	        }
	    }
	}

	public static String obtenerRutaEscritorio() {
		File escritorio = FileSystemView.getFileSystemView().getHomeDirectory();
		return escritorio.getAbsolutePath();
	}
}
