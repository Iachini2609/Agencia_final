package GUI;

import javax.swing.*;
import java.awt.*;

public class JLabelConContorno extends JLabel {

    private static final long serialVersionUID = 1L;

    public JLabelConContorno(String texto) {
        super(texto);
        setFont(new Font("Segoe UI", Font.BOLD, 20));
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibuja contorno negro
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawString(getText(), 1, getBaseline(getWidth(), getHeight()));
        
        // Dibuja texto blanco encima
        g2.setColor(getForeground());
        g2.drawString(getText(), 0, getBaseline(getWidth(), getHeight()));
        
        g2.dispose();
    }
}
