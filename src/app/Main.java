package app;

import interfaz.Login;
import interfaz.Menu;
import sql.MySQLConnection;

import javax.swing.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String email = "";
        String contrasena = "";
        new Login();




        while (!Login.isLoginIntentado()) {
            try {
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        email = Login.getEmail();
        contrasena = Login.getContrasena();





        Connection conexion = new MySQLConnection().mySQLConnect();
        if (conexion != null) {
            try {
                String sql = "SELECT * FROM usuario WHERE email = ? AND contrasena = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, contrasena);

             ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. Bienvenido " + rs.getString("nombre_usuario"));
                    new Menu().ventanaPrincipal();
                } else {
                    JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos, Saliendo del programa...");
                    System.exit(1);
                }

                rs.close();
                stmt.close();
                conexion.close();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos");
        }
    }
}
