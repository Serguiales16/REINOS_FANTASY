package interfaz;

import javax.swing.*;
import java.awt.*;

public class Fondo extends JPanel {
    private Image fondo;

    public Fondo(String rutaImagen) {
        // Cargar la imagen como recurso desde el classpath (funciona en el .jar)
        try {
            fondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
        } catch (NullPointerException e) {
            System.err.println("No se pudo cargar la imagen: " + rutaImagen);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(80, 150, 80, 150));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            // Esto adapta la imagen al tamaño del panel (que será 800x800)
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
