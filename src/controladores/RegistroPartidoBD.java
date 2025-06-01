package controladores;

import sql.MySQLConnection;

import java.sql.*;
import java.util.Random;

public class RegistroPartidoBD {

    public void insertarLuchaAleatoria() {
        Connection conn = new MySQLConnection().mySQLConnect();

        try {
            int[] equipos = obtenerDosEquiposAleatorios(conn);
            int id_retador = equipos[0];
            int id_oponente = equipos[1];

            if (id_retador == 0 || id_oponente == 0) {
                System.out.println("No se pudieron obtener equipos válidos.");
                return;
            }

            String nombre1 = obtenerNombreEquipoPorId(conn, id_retador);
            String nombre2 = obtenerNombreEquipoPorId(conn, id_oponente);

           
            int goles1 = new Random().nextInt(6);
            int goles2 = new Random().nextInt(6);

            
            int id_ganador = (goles1 > goles2) ? id_retador : (goles2 > goles1) ? id_oponente : null;

            
            int id_arena = obtenerArenaAleatoria(conn);
            if (id_arena == 0) {
                System.out.println("No se pudo seleccionar una arena.");
                return;
            }

            
            String sql = "INSERT INTO lucha (equipo_retador_id, equipo_oponente_id, arena_id, fecha, equipo_ganador_id) " +
                    "VALUES (?, ?, ?, CURDATE(), ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_retador);
            stmt.setInt(2, id_oponente);
            stmt.setInt(3, id_arena);

            if (id_ganador == 0) {
                stmt.setNull(4, Types.INTEGER); 
            } else {
                stmt.setInt(4, id_ganador);
            }

            stmt.executeUpdate();

            System.out.println("LUCHA INSERTADA:");
            System.out.println(nombre1 + " " + goles1 + " - " + goles2 + " " + nombre2);
            System.out.println("Arena ID: " + id_arena);
            System.out.println("Ganador: " + (id_ganador == 0 ? "Empate" : obtenerNombreEquipoPorId(conn, id_ganador)));

        } catch (SQLException e) {
            System.out.println("Error al insertar la lucha.");
            e.printStackTrace();
        }
    }

    private int[] obtenerDosEquiposAleatorios(Connection conn) throws SQLException {
        String sql = "SELECT id_equipo FROM equipo";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        int[] ids = new int[5];
        int index = 0;
        while (rs.next() && index < 5) {
            ids[index++] = rs.getInt("id_equipo");
        }

        if (index < 2) return new int[]{0, 0};

        Random rand = new Random();
        int idx1 = rand.nextInt(index);
        int idx2;
        do {
            idx2 = rand.nextInt(index);
        } while (idx1 == idx2);

        return new int[]{ids[idx1], ids[idx2]};
    }

    private int obtenerArenaAleatoria(Connection conn) throws SQLException {
        String sql = "SELECT id_arena FROM arena ORDER BY RAND() LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) return rs.getInt("id_arena");
        return 0;
    }

    private String obtenerNombreEquipoPorId(Connection conn, int id) throws SQLException {
        String sql = "SELECT nombre_equipo FROM equipo WHERE id_equipo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getString("nombre_equipo");
        return "Desconocido";
    }
    public String insertarLuchaAleatoriaYDevolverTexto() {
        Connection conn = new MySQLConnection().mySQLConnect();
        StringBuilder resumen = new StringBuilder();

        try {
            int[] equipos = obtenerDosEquiposAleatorios(conn);
            int id_retador = equipos[0];
            int id_oponente = equipos[1];

            if (id_retador == 0 || id_oponente == 0) {
                return "No se pudieron obtener equipos válidos.";
            }

            String nombre1 = obtenerNombreEquipoPorId(conn, id_retador);
            String nombre2 = obtenerNombreEquipoPorId(conn, id_oponente);

            int goles1 = new Random().nextInt(6);
            int goles2 = new Random().nextInt(6);

            int id_ganador = (goles1 > goles2) ? id_retador : (goles2 > goles1) ? id_oponente : 0;

            int id_arena = obtenerArenaAleatoria(conn);
            if (id_arena == 0) {
                return "No se pudo seleccionar una arena.";
            }

            String sql = "INSERT INTO lucha (equipo_retador_id, equipo_oponente_id, arena_id, fecha, equipo_ganador_id) " +
                    "VALUES (?, ?, ?, CURDATE(), ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_retador);
            stmt.setInt(2, id_oponente);
            stmt.setInt(3, id_arena);
            if (id_ganador == 0) stmt.setNull(4, Types.INTEGER);
            else stmt.setInt(4, id_ganador);
            stmt.executeUpdate();

            resumen.append("LUCHA FINALIZADA!!\n")
                    .append("-").append(nombre1).append(" ").append(goles1)
                    .append(" - ").append(goles2).append(" ").append(nombre2).append("\n")
                    .append("Arena ID: ").append(id_arena).append("\n")
                    .append("Ganador: ").append(id_ganador == 0 ? "Empate" : obtenerNombreEquipoPorId(conn, id_ganador));

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al insertar la lucha.";
        }

        return resumen.toString();
    }

}
