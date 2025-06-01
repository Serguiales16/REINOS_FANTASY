
package interfaz;
/**
 * @authors Jorge & Sergio
 */
import sql.MySQLConnection;

import javax.swing.*;
import java.sql.*;

/**
 * Clase que gestiona la visualización de la información del equipo del usuario.
 * Permite consultar y mostrar los detalles del equipo almacenados en la base de datos,
 * incluyendo su identificador, nombre, reino de origen y puntuación actual.
 */
public class MiEquipo {

    /**
     * Muestra una ventana de diálogo con la información del equipo del usuario.
     * Realiza una consulta a la base de datos para obtener los detalles del equipo con ID 1
     * y los presenta en una ventana emergente formateada.
     * Si ocurre algún error durante la consulta, muestra un mensaje de error apropiado.
     */
    public void mostrar() {
        Connection conn = new MySQLConnection().mySQLConnect();

        try {
            String sql = "SELECT * FROM equipo WHERE id_equipo = 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int id = rs.getInt("id_equipo");
                String nombre = rs.getString("nombre_equipo");
                String reino = rs.getString("reino_origen");
                int puntos = rs.getInt("puntos");


                String mensaje = "⚔️ MI EQUIPO\n\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Reino: " + reino + "\n" +
                        "Puntos: " + puntos + "\n";

                JOptionPane.showMessageDialog(null, mensaje, "Mi Equipo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún equipo con ID 1.", "Mi Equipo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}