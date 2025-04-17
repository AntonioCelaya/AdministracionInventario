// InventarioController.java
package controlador;

import dao.ProductoDAO;
import modelo.Producto;
import dao.Conexion;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@SuppressWarnings("serial")
public class InventarioController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection con = Conexion.getConexion()) {
            ProductoDAO dao = new ProductoDAO(con);
            List<Producto> lista = dao.obtenerTodos();
            request.setAttribute("listaProductos", lista);
            request.getRequestDispatcher("/vistas/inventario.jsp")
                   .forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
