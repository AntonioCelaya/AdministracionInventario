package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Movimiento;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {
    private Connection con;

    public MovimientoDAO(Connection con) {
        this.con = con;
    }

    public boolean registrarMovimiento(int idProducto, int cantidad, String tipoMovimiento, int idUsuario) throws SQLException {
        String sql = "INSERT INTO movimientos (idProducto, cantidad, tipoMovimiento, idUsuario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);       
            stmt.setInt(2, cantidad);          
            stmt.setString(3, tipoMovimiento);
            stmt.setInt(4, idUsuario);         
            return stmt.executeUpdate() > 0;
        }
    }

    
    public List<Movimiento> listarMovimientos() throws SQLException {
        List<Movimiento> lista = new ArrayList<>();

        String sql = 
            "SELECT m.tipoMovimiento, m.fechaHora, m.cantidad, " +
            "       u.nombre AS nombreUsuario, " +
            "       p.nombre AS nombreProducto " +
            "FROM movimientos m " +
            "JOIN usuarios u ON m.idUsuario = u.idUsuario " +
            "JOIN productos p ON m.idProducto = p.idProducto " +
            "ORDER BY m.fechaHora DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setTipoMovimiento(rs.getString("tipoMovimiento"));
                movimiento.setFechaHora(rs.getTimestamp("fechaHora"));
                movimiento.setCantidad(rs.getInt("cantidad"));             // ahora sí existe
                movimiento.setNombreUsuario(rs.getString("nombreUsuario"));
                movimiento.setNombreProducto(rs.getString("nombreProducto"));
                lista.add(movimiento);
            }
        }

        return lista;
    }

    
    public List<Movimiento> listarMovimientosFiltrados(String tipo) throws SQLException {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.tipoMovimiento, m.fechaHora, m.cantidad, " +
                     "u.nombre AS nombreUsuario, p.nombre AS nombreProducto " +
                     "FROM movimientos m " +
                     "JOIN usuarios u ON m.idUsuario = u.idUsuario " +
                     "JOIN productos p ON m.idProducto = p.idProducto ";

        if (tipo != null && !tipo.isEmpty()) {
            sql += "WHERE m.tipoMovimiento = ? ";
        }

        sql += "ORDER BY m.fechaHora DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            if (tipo != null && !tipo.isEmpty()) {
                stmt.setString(1, tipo);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setTipoMovimiento(rs.getString("tipoMovimiento"));
                    movimiento.setFechaHora(rs.getTimestamp("fechaHora"));
                    movimiento.setNombreUsuario(rs.getString("nombreUsuario"));
                    movimiento.setNombreProducto(rs.getString("nombreProducto"));
                    movimiento.setCantidad(rs.getInt("cantidad"));
                    lista.add(movimiento);
                }
            }
        }

        return lista;
    }


}