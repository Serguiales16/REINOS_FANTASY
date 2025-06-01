
package interfaz;

/**
 * @authors Jorge & Sergio
 */

import controladores.RegistroPartidoBD;

import javax.swing.*;
import java.awt.*;

public class SimuladorPartidoVentana {

    private JFrame ventana;
    private JLabel resultado;

    public void mostrar() {
        ventana = new JFrame("Simulación de Lucha");
        ventana.setSize(400, 200);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        
        JLabel animacion = new JLabel("Simulando la lucha", SwingConstants.CENTER);
        animacion.setFont(new Font("Serif", Font.BOLD, 18));
        ventana.add(animacion, BorderLayout.CENTER);
        ventana.setVisible(true);

        
        Timer timer = new Timer(500, null);
        final String[] pasos = {
                "Simulando la lucha.",
                "Simulando la lucha..",
                "Simulando la lucha...",
                "Simulando la lucha....",
                "Simulando la lucha....."
        };
        final int[] index = {0};

        timer.addActionListener(e -> {
            animacion.setText(pasos[index[0]]);
            index[0]++;
            if (index[0] == pasos.length) {
                timer.stop();

                
                RegistroPartidoBD simulador = new RegistroPartidoBD();
                String textoResultado = simulador.insertarLuchaAleatoriaYDevolverTexto();
                animacion.setText("<html><center>" + textoResultado.replace("\n", "<br>") + "</center></html>");
            }
        });

        timer.start();
    }

}