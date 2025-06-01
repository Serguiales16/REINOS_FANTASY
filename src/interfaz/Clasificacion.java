package interfaz;
/**
 * @authors Jorge & Sergio
 */
import sql.MySQLConnection;

import javax.swing.*;
import java.sql.*;

public class Clasificacion {

    public void mostrar() {
        Connection conn = new MySQLConnection().mySQLConnect();
        StringBuilder texto = new StringBuilder();

        try {
            String sql = "SELECT * FROM equipo ORDER BY puntos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            texto.append("📊 CLASIFICACIÓN DE EQUIPOS\n\n");

            while (rs.next()) {
                int id = rs.getInt("id_equipo");
                String nombre = rs.getString("nombre_equipo");
                String reino = rs.getString("reino_origen");
                int puntos = rs.getInt("puntos");
               ;

                texto.append("ID: ").append(id)
                        .append(" | Nombre: ").append(nombre)
                        .append(" | Reino: ").append(reino)
                        .append(" | Puntos: ").append(puntos)

                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, texto.toString(), "Clasificación", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la clasificación.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
