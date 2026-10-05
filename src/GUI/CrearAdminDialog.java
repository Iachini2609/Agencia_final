package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controlador.AdministradorControlador;

import java.awt.*;
import java.awt.event.*;

public class CrearAdminDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JPasswordField txtRepetirContrasenia;
    private AdministradorControlador ControladorAdmin = new AdministradorControlador(); 

    public CrearAdminDialog(Frame owner) {
        super(owner, "Crear Administrador", true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PantallaEmpleado.class.getResource("/imgenes/logo2.png")));
        setSize(500, 350);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(new Color(200, 0, 0));
        setLayout(new BorderLayout());

        // Panel central con GridBagLayout
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(new Color(200, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.LINE_END;

        // Label Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsuario = crearLabel("DNI:");
        panelCampos.add(lblUsuario, gbc);

        // Campo Usuario
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = crearTextField();
        panelCampos.add(txtUsuario, gbc);

        // Label Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblContrasenia = crearLabel("Contraseña:");
        panelCampos.add(lblContrasenia, gbc);

        // Campo Contraseña
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtContrasenia = crearPasswordField();
        panelCampos.add(txtContrasenia, gbc);

        // Label Repetir Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblRepetir = crearLabel("Repetir Contraseña:");
        panelCampos.add(lblRepetir, gbc);

        // Campo Repetir Contraseña
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtRepetirContrasenia = crearPasswordField();
        panelCampos.add(txtRepetirContrasenia, gbc);

        add(panelCampos, BorderLayout.CENTER);

        // Panel de botones abajo
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(200, 0, 0));
        JButton btnCrear = crearBoton("Crear");
        JButton btnCancelar = crearBoton("Cancelar");

        btnCancelar.addActionListener(e -> dispose());

        btnCrear.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String pass1 = new String(txtContrasenia.getPassword());
            String pass2 = new String(txtRepetirContrasenia.getPassword());

            if (usuario.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamamos al método que guarda en la BD
            boolean exito = ControladorAdmin.guardarAdminEnBD(usuario, pass1);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Administrador creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cierra la ventana de registro
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un error al guardar el administrador en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
        return lbl;
    }

    private JTextField crearTextField() {
        JTextField txt = new JTextField(15);
        txt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txt.setBorder(new EmptyBorder(6, 6, 6, 6));
        return txt;
    }

    private JPasswordField crearPasswordField() {
        JPasswordField txt = new JPasswordField(15);
        txt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txt.setBorder(new EmptyBorder(6, 6, 6, 6));
        return txt;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Tahoma", Font.BOLD, 18));
        btn.setPreferredSize(new Dimension(100, 30)); // Aumenta el tamaño del botón
        btn.setBackground(new Color(240, 240, 240));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(60, 63, 65));
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 240));
                btn.setForeground(Color.BLACK);
            }
        });
        return btn;
    }
}