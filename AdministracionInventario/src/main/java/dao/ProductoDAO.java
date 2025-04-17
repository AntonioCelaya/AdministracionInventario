package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Producto;

public class ProductoDAO {

    private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    // Devuelve true si el producto fue registrado correctamente
    public boolean registrarProducto(String nombre, String descripcion) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return true; // Producto registrado correctamente
                    } else {
                        return false; // No se pudo obtener el ID del producto insertado
                    }
                }
            } else {
                return false; // No se registraron filas en la tabla
            }
        }   
    }
    
    // Obtener todos los productos activos
    public List<Producto> obtenerProductosActivos() throws SQLException {
        String sql = "SELECT * FROM productos WHERE estatus = 1";
        List<Producto> productos = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                productos.add(p);
            }
        }

        return productos;
    }
    
    public Producto obtenerProductoPorId(int idProducto) throws SQLException {
        String sql = "SELECT * FROM productos WHERE idProducto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setCantidad(rs.getInt("cantidad"));
                    return p;
                }
            }
        }
        throw new SQLException("Producto no encontrado");
    }

    public void actualizarCantidadProducto(int idProducto, int nuevaCantidad) throws SQLException {
        String sql = "UPDATE productos SET cantidad = ? WHERE idProducto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }
    
 // ProductoDAO.java
    public void actualizarEstatus(int idProducto, int nuevoEstatus) throws SQLException {
        String sql = "UPDATE productos SET estatus = ? WHERE idProducto = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstatus);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        }
    }
    
 // ProductoDAO.java
    public List<Producto> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstatus(rs.getInt("estatus"));
                productos.add(p);
            }
        }

        return productos;
    }



}