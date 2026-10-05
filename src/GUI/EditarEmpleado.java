package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlador.EmpleadoControlador;
import Modelo.Empleado;

public class EditarEmpleado extends JPanel {

	private JComboBox<Empleado> cmbEmpleados;
	private JTextField txtNombre, txtApellido, txtDni, txtContrasenia;
	private final EmpleadoControlador empleadoControlador = new EmpleadoControlador("Agencia.db");

	public EditarEmpleado() {
		setLayout(null);
		setBackground(Color.RED);

		JLabelConContorno lblEditarEmpleado = new JLabelConContorno("Editar Empleado");
		lblEditarEmpleado.setForeground(Color.WHITE);
		lblEditarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblEditarEmpleado.setBounds(263, 42, 400, 49);
		add(lblEditarEmpleado);

		JLabel lblEmpleado = new JLabel("Empleado:");
		lblEmpleado.setBounds(38, 123, 120, 25);
		lblEmpleado.setForeground(Color.WHITE);
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblEmpleado);

		cmbEmpleados = new JComboBox<>();
		cmbEmpleados.setBounds(38, 158, 241, 25);
		cmbEmpleados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(cmbEmpleados);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(307, 153, 99, 32);
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 22));
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(307, 194, 289, 32);
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(txtNombre);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(307, 236, 99, 32);
		lblApellido.setForeground(Color.WHITE);
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 22));
		add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(307, 266, 289, 32);
		txtApellido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(txtApellido);

		JLabel lblDni_1 = new JLabel("DNI");
		lblDni_1.setBounds(307, 311, 99, 32);
		lblDni_1.setForeground(Color.WHITE);
		lblDni_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		add(lblDni_1);

		txtDni = new JTextField();
		txtDni.setBounds(307, 342, 289, 32);
		txtDni.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(txtDni);

		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(307, 395, 155, 32);
		lblContrasenia.setForeground(Color.WHITE);
		lblContrasenia.setFont(new Font("Tahoma", Font.BOLD, 22));
		add(lblContrasenia);

		txtContrasenia = new JTextField();
		txtContrasenia.setBounds(307, 426, 289, 32);
		txtContrasenia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(txtContrasenia);

		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setIcon(new ImageIcon(EditarEmpleado.class.getResource("/imgenes/icono de guardar.png")));
		btnGuardar.setBounds(38, 374, 200, 35);
		btnGuardar.addActionListener(e -> {
		    Empleado sel = (Empleado) cmbEmpleados.getSelectedItem();
		    if (sel == null) {
		        JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
		        return;
		    }
		    String nombre = txtNombre.getText().trim();
		    String apellido = txtApellido.getText().trim();
		    String dni = txtDni.getText().trim();
		    String contrasenia = txtContrasenia.getText().trim();
		    if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || contrasenia.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
		        return;
		    }

		    Empleado mod = new Empleado(nombre, apellido, dni, contrasenia);
		    boolean ok = empleadoControlador.actualizarEmpleado(mod);

		    if (ok) {
		        JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
		        cargarEmpleadosEnCombo();
		        seleccionarEnComboPorDni(dni);
		    } else {
		        JOptionPane.showMessageDialog(this, "No se encontró un empleado con ese DNI.");
		    }
		});

		// 🎨 Estilo original
		btnGuardar.setForeground(Color.BLACK);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnGuardar.setFocusPainted(false);
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnGuardar.setBackground(new Color(240, 240, 240));
		btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		        btnGuardar.setBackground(new Color(60, 63, 65));
		        btnGuardar.setForeground(Color.WHITE);
		    }
		    public void mouseExited(java.awt.event.MouseEvent e) {
		        btnGuardar.setBackground(new Color(240, 240, 240));
		        btnGuardar.setForeground(Color.BLACK);
		    }
		});
		add(btnGuardar);


		JButton btnEliminar = new JButton("Eliminar Empleado");
		btnEliminar.setIcon(new ImageIcon(EditarEmpleado.class.getResource("/imgenes/icono eliminar.png")));
		btnEliminar.setBounds(38, 425, 200, 35);
		btnEliminar.addActionListener(e -> {
		    Empleado sel = (Empleado) cmbEmpleados.getSelectedItem();
		    if (sel == null) {
		        JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
		        return;
		    }
		    int confirm = JOptionPane.showConfirmDialog(this,
		            "¿Eliminar a " + sel.getNombre() + " " + sel.getApellido() + " (DNI: " + sel.getDni() + ")?",
		            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
		    if (confirm == JOptionPane.YES_OPTION) {
		        boolean eliminado = empleadoControlador.eliminarPorDni(sel.getDni());
		        if (eliminado) {
		            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
		            limpiarCampos();
		            cargarEmpleadosEnCombo();
		        } else {
		            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.");
		        }
		    }
		});

		// 🎨 Estilo original
		btnEliminar.setForeground(Color.BLACK);
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnEliminar.setFocusPainted(false);
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnEliminar.setBackground(new Color(240, 240, 240));
		btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		        btnEliminar.setBackground(new Color(60, 63, 65));
		        btnEliminar.setForeground(Color.WHITE);
		    }
		    public void mouseExited(java.awt.event.MouseEvent e) {
		        btnEliminar.setBackground(new Color(240, 240, 240));
		        btnEliminar.setForeground(Color.BLACK);
		    }
		});
		add(btnEliminar);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(EditarEmpleado.class.getResource("/imgenes/logo2.png")));
		lblLogo.setBounds(496, 24, 575, 594);
		add(lblLogo);

		// Cargar combo y listeners
		cargarEmpleadosEnCombo();
		cmbEmpleados.addActionListener(e -> {
			Empleado sel = (Empleado) cmbEmpleados.getSelectedItem();
			if (sel != null) {
				Empleado emp = empleadoControlador.buscarPorDni(sel.getDni());
				cargarCampos(emp != null ? emp : sel);
			} else {
				limpiarCampos();
			}
		});

		if (cmbEmpleados.getItemCount() > 0) {
			cmbEmpleados.setSelectedIndex(0);
			Empleado sel = (Empleado) cmbEmpleados.getSelectedItem();
			if (sel != null)
				cargarCampos(sel);
		}
	}

	// Helpers
	private void cargarCampos(Empleado emp) {
		txtNombre.setText(emp.getNombre());
		txtApellido.setText(emp.getApellido());
		txtDni.setText(emp.getDni());
		txtContrasenia.setText(emp.getContrasena());
	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtDni.setText("");
		txtContrasenia.setText("");
		txtNombre.requestFocus();
	}

	private void cargarEmpleadosEnCombo() {
		java.util.List<Empleado> empleados = empleadoControlador.obtenerTodosLosEmpleados();
		DefaultComboBoxModel<Empleado> modelo = new DefaultComboBoxModel<>();
		for (Empleado emp : empleados)
			modelo.addElement(emp);
		cmbEmpleados.setModel(modelo);

		// Renderer: "Nombre Apellido"
		cmbEmpleados.setRenderer(new javax.swing.ListCellRenderer<Empleado>() {
			private final javax.swing.DefaultListCellRenderer def = new javax.swing.DefaultListCellRenderer();

			@Override
			public java.awt.Component getListCellRendererComponent(javax.swing.JList<? extends Empleado> list,
					Empleado value, int index, boolean isSelected, boolean cellHasFocus) {
				javax.swing.JLabel lbl = (javax.swing.JLabel) def.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null)
					lbl.setText(value.getNombre() + " " + value.getApellido());
				return lbl;
			}
		});
	}

	private void seleccionarEnComboPorDni(String dni) {
		ComboBoxModel<Empleado> model = cmbEmpleados.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			Empleado e = model.getElementAt(i);
			if (e != null && dni.equals(e.getDni())) {
				cmbEmpleados.setSelectedIndex(i);
				return;
			}
		}
	}

	private void estilizarBoton(JButton b) {
	}
}