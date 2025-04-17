package controlador;

import dao.Conexion;
import dao.ProductoDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@SuppressWarnings("serial")
public class SalidaProductoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO dao = new ProductoDAO(con);
            List<Producto> listaProductos = dao.obtenerTodos();

            request.setAttribute("productosActivos", listaProductos);
            request.getRequestDispatcher("/vistas/salidaProductos.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        HttpSession session = request.getSession();
        modelo.Usuario usuario = (modelo.Usuario) session.getAttribute("usuario");

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO productoDAO = new ProductoDAO(con);
            dao.MovimientoDAO movimientoDAO = new dao.MovimientoDAO(con);

            Producto producto = productoDAO.obtenerProductoPorId(idProducto);

            if (producto.getCantidad() < cantidad) {
                session.setAttribute("mensajeError", "No hay suficiente cantidad disponible.");
            } else {
                productoDAO.actualizarCantidadProducto(idProducto, producto.getCantidad() - cantidad);
                movimientoDAO.registrarMovimiento(
                    idProducto,
                    cantidad,
                    "salida",
                    usuario.getIdUsuario()
                );

                session.setAttribute("mensajeExito", "Salida registrada correctamente.");
            }

            response.sendRedirect(request.getContextPath() + "/SalidaProductoController");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
