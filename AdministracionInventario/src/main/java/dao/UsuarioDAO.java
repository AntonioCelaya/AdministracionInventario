package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Usuario;

public class UsuarioDAO {

    private Connection con;

    public UsuarioDAO(Connection con) {
        this.con = con;
    }

    public Usuario validar(String correo, String contrasena) {
    	// Se crea el objeto usuario que almacenará los datos obtenidos de la consulta
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ? AND estatus = 1";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // Si ResultSet.next() devuelve True
                usuario = new Usuario();
                /*
                 el objeto usuario recibe los datos obtenidos y se agregan al constructor vacío para
                 ser enviados de vuelta como objeto con datos al modelo
                 */
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setIdRol(rs.getInt("idRol"));
                usuario.setEstatus(rs.getInt("estatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}