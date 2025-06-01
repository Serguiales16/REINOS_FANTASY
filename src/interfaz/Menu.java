/**
 * @authors Jorge & Sergio
 */
package interfaz;

import controladores.Jugador;
import controladores.JugadorDatos;
import interfaz.SimuladorPartidoVentana;
import interfaz.Fondo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Menu extends JFrame {


    private final JugadorDatos dao = new JugadorDatos();

    public void menuDesarrollador() {
        int opcion = 0;

        do {
            String input = JOptionPane.showInputDialog(null, """
                    1. Insertar jugador
                    2. Actualizar jugador
                    3. Eliminar jugador
                    4. Mostrar todos los jugadores
                    5. Jugador con mayor puntuación
                    0. Salir
                    
                    Introduce una opción:
                    """, "Menú Desarrollador", JOptionPane.PLAIN_MESSAGE);

            try {
                opcion = (input != null) ? Integer.parseInt(input) : -1;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Debes introducir un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }

            switch (opcion) {
                case 1: {
                    try {
                        String nombre = JOptionPane.showInputDialog("Nombre:");
                        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre no puede estar vacío");
                        String raza = JOptionPane.showInputDialog("Raza:");
                        if (raza == null || raza.trim().isEmpty()) raza = "Unknown";
                        String saludStr = JOptionPane.showInputDialog("Salud (número):");
                        String fuerzaStr = JOptionPane.showInputDialog("Fuerza (número):");
                        String manaStr = JOptionPane.showInputDialog("Mana (número):");
                        String poderTotalStr = JOptionPane.showInputDialog("Poder Total (número):");
                        String precioStr = JOptionPane.showInputDialog("Precio (número):");
                        String equipo = JOptionPane.showInputDialog("Equipo:");
                        if (equipo == null || equipo.trim().isEmpty()) throw new IllegalArgumentException("Equipo no puede estar vacío");

                        int salud = Integer.parseInt(saludStr);
                        int fuerza = Integer.parseInt(fuerzaStr);
                        int mana = Integer.parseInt(manaStr);
                        float poderTotal = Float.parseFloat(poderTotalStr);
                        double precio = Double.parseDouble(precioStr);

                        Jugador jugador = new Jugador(nombre, raza, salud, fuerza, mana, poderTotal, precio, equipo);
                        dao.insertar(jugador);
                        JOptionPane.showMessageDialog(null, "Jugador insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Los valores numéricos no son válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al insertar jugador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 2: {
                    try {
                        String idStr = JOptionPane.showInputDialog("ID del jugador a actualizar:");
                        int id = Integer.parseInt(idStr);
                        String nombre = JOptionPane.showInputDialog("Nuevo nombre:");
                        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre no puede estar vacío");
                        String raza = JOptionPane.showInputDialog("Nueva raza:");
                        if (raza == null || raza.trim().isEmpty()) raza = "Unknown";
                        String saludStr = JOptionPane.showInputDialog("Nueva salud (número):");
                        String fuerzaStr = JOptionPane.showInputDialog("Nueva fuerza (número):");
                        String manaStr = JOptionPane.showInputDialog("Nuevo mana (número):");
                        String poderTotalStr = JOptionPane.showInputDialog("Nuevo poder total (número):");
                        String precioStr = JOptionPane.showInputDialog("Nuevo precio (número):");
                        String equipo = JOptionPane.showInputDialog("Nuevo equipo:");
                        if (equipo == null || equipo.trim().isEmpty()) throw new IllegalArgumentException("Equipo no puede estar vacío");

                        int salud = Integer.parseInt(saludStr);
                        int fuerza = Integer.parseInt(fuerzaStr);
                        int mana = Integer.parseInt(manaStr);
                        float poderTotal = Float.parseFloat(poderTotalStr);
                        double precio = Double.parseDouble(precioStr);

                        Jugador jugador = new Jugador(id, nombre, raza, salud, fuerza, mana, poderTotal, precio, 0, equipo);
                        dao.actualizar(jugador);
                        JOptionPane.showMessageDialog(null, "Jugador actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Los valores numéricos no son válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al actualizar jugador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 3: {
                    try {
                        String idStr = JOptionPane.showInputDialog("ID del jugador a eliminar:");
                        int id = Integer.parseInt(idStr);
                        dao.eliminar(id);
                        JOptionPane.showMessageDialog(null, "Jugador eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar jugador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 4: {
                    List<Jugador> jugadores = dao.listarTodos();
                    if (jugadores.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay jugadores.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (Jugador j : jugadores) {
                            sb.append(j).append("\n");
                        }
                        JTextArea area = new JTextArea(sb.toString());
                        area.setEditable(false);
                        JOptionPane.showMessageDialog(null, new JScrollPane(area), "Lista de jugadores", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                case 5: {
                    Jugador top = dao.buscarMayorPuntuacion();
                    if (top != null) {
                        JOptionPane.showMessageDialog(null, "Jugador con mayor poder:\n" + top, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay jugadores.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
                case 0: {
                    JOptionPane.showMessageDialog(null, "Saliendo...", "Información", JOptionPane.INFORMATION_MESSAGE);

                    break;

                }
                default: {
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);

                    break;
                }
            }
        } while (opcion != 0);
    }
    
        public void ventanaPrincipal() {

            setTitle("REINOS FANTASY");
            setSize(800, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);



            JPanel panelCentral = new JPanel();
            panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
            panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));


            JButton jugar = new JButton("JUGAR");
            jugar.setPreferredSize(new Dimension(200, 60));
            jugar.setMaximumSize(new Dimension(300, 60));
            jugar.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(jugar);
            panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

            jugar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SimuladorPartidoVentana().mostrar();
                }
            });


            JButton equipo = new JButton("EQUIPO");
            equipo.setPreferredSize(new Dimension(200, 60));
            equipo.setMaximumSize(new Dimension(300, 60));
            equipo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(equipo);
            panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

            equipo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new MiEquipo().mostrar();
                }
            });


            JButton clasificacion = new JButton("CLASIFICACION");
            clasificacion.setPreferredSize(new Dimension(200, 60));
            clasificacion.setMaximumSize(new Dimension(300, 60));
            clasificacion.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(clasificacion);
            panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

            clasificacion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Clasificacion().mostrar();
                }
            });

            JButton salir = new JButton("SALIR");
            salir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Gracias, Ten un buen dia!!", "Adios", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            });
            salir.setPreferredSize(new Dimension(200, 60));
            salir.setMaximumSize(new Dimension(300, 60));
            salir.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(salir);
            panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));


            Fondo fondo = new Fondo("/recursos/fondo.png");
            fondo.setLayout(new GridBagLayout()); 

            panelCentral.setOpaque(false);

            fondo.add(panelCentral); 
            setContentPane(fondo);   

            setVisible(true);
        
        }
}    
