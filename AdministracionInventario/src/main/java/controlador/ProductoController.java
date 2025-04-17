package controlador;

import dao.ProductoDAO;
import modelo.Producto;
import dao.Conexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class ProductoController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO dao = new ProductoDAO(con);

            if ("darBaja".equals(accion)) {
                dao.actualizarEstatus(idProducto, 0);
            } else if ("activar".equals(accion)) {
                dao.actualizarEstatus(idProducto, 1);
            }

            response.sendRedirect("InventarioController"); // Para recargar la tabla

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("inventario.jsp?error=1");
        }
    }
}
