package controladores;

import sql.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDatos {

    private Connection conectar() {
        Connection conn = new MySQLConnection().mySQLConnect();
        if (conn == null) {
            System.out.println("❌ No se pudo conectar a la base de datos");
        }
        return conn;
    }

    private int obtenerIdEquipoPorNombre(Connection conn, String nombreEquipo) throws SQLException {
        String sql = "SELECT id_equipo FROM equipo WHERE LOWER(TRIM(nombre_equipo)) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreEquipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_equipo");
                System.out.println("Equipo encontrado: " + nombreEquipo + " (ID: " + id + ")");
                return id;
            } else {
                System.out.println("Equipo no encontrado: " + nombreEquipo);
                return 0;
            }
        }
    }

    public void insertar(Jugador jugador) {
        String sql = "INSERT INTO jugador (nombre, raza, salud, fuerza, mana, poder_total, precio, equipo_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.out.println("Conexión nula, no se puede insertar el jugador");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, jugador.getNombre());
                stmt.setString(2, jugador.getRaza());
                stmt.setInt(3, jugador.getSalud());
                stmt.setInt(4, jugador.getFuerza());
                stmt.setInt(5, jugador.getMana());
                stmt.setFloat(6, jugador.getPoderTotal());
                stmt.setDouble(7, jugador.getPrecio());

                if (jugador.getEquipoId() > 0) {
                    stmt.setInt(8, jugador.getEquipoId());
                } else {
                    stmt.setNull(8, Types.INTEGER); // ← NO TIENE EQUIPO
                }

                int rows = stmt.executeUpdate();
                System.out.println("Jugador insertado correctamente (" + rows + " filas afectadas)");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el jugador: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void actualizar(Jugador jugador) {
        String sql = "UPDATE jugador SET nombre = ?, raza = ?, salud = ?, fuerza = ?, mana = ?, poder_total = ?, precio = ?, equipo_id = ? WHERE id_jugador = ?";
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.out.println("Conexión nula, no se puede actualizar el jugador");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, jugador.getNombre());
                stmt.setString(2, jugador.getRaza());
                stmt.setInt(3, jugador.getSalud());
                stmt.setInt(4, jugador.getFuerza());
                stmt.setInt(5, jugador.getMana());
                stmt.setFloat(6, jugador.getPoderTotal());
                stmt.setDouble(7, jugador.getPrecio());


                if (jugador.getEquipoId() > 0) {
                    stmt.setInt(8, jugador.getEquipoId());
                } else {
                    stmt.setNull(8, Types.INTEGER);
                }

                stmt.setInt(9, jugador.getId());

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Jugador actualizado (" + rows + " filas afectadas)");
                } else {
                    System.out.println("Jugador con ID " + jugador.getId() + " no encontrado");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el jugador: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void eliminar(int id) {
        String sql = "DELETE FROM jugador WHERE id_jugador = ?";
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.out.println("Conexión nula, no se puede eliminar el jugador");
                return;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Jugador eliminado (" + rows + " filas afectadas)");
                } else {
                    System.out.println("Jugador con ID " + id + " no encontrado");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el jugador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Jugador> listarTodos() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT j.*, e.nombre_equipo FROM jugador j LEFT JOIN equipo e ON j.equipo_id = e.id_equipo";
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.out.println("Conexión nula, no se puede listar jugadores");
                return lista;
            }
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Jugador j = new Jugador(
                            rs.getInt("id_jugador"),
                            rs.getString("nombre"),
                            rs.getString("raza"),
                            rs.getInt("salud"),
                            rs.getInt("fuerza"),
                            rs.getInt("mana"),
                            rs.getFloat("poder_total"),
                            rs.getDouble("precio"),
                            rs.getInt("equipo_id"),
                            rs.getString("nombre_equipo")
                    );
                    lista.add(j);
                }
                System.out.println("Listado de jugadores recuperado (" + lista.size() + " jugadores)");
            }
        } catch (SQLException e) {
            System.out.println("Error al listar jugadores: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public Jugador buscarMayorPuntuacion() {
        String sql = "SELECT j.*, e.nombre_equipo FROM jugador j LEFT JOIN equipo e ON j.equipo_id = e.id_equipo ORDER BY poder_total DESC LIMIT 1";
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.out.println("Conexión nula, no se puede buscar el jugador");
                return null;
            }
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    Jugador j = new Jugador(
                            rs.getInt("id_jugador"),
                            rs.getString("nombre"),
                            rs.getString("raza"),
                            rs.getInt("salud"),
                            rs.getInt("fuerza"),
                            rs.getInt("mana"),
                            rs.getFloat("poder_total"),
                            rs.getDouble("precio"),
                            rs.getInt("equipo_id"),
                            rs.getString("nombre_equipo")
                    );
                    System.out.println("Jugador con mayor puntuación encontrado: " + j);
                    return j;
                } else {
                    System.out.println("No se encontraron jugadores");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar jugador con mayor puntuación: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}