package controlador;

import dao.ProductoDAO;
import dao.MovimientoDAO;
import modelo.Producto;
import modelo.Usuario;
import dao.Conexion;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@SuppressWarnings("serial")
public class SalidaInventarioController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar que el usuario sea almacenista (idRol == 2)
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null || usuario.getIdRol() != 2) {
            response.sendRedirect("error.jsp?error=acceso_denegado");
            return;
        }

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidadSalida = Integer.parseInt(request.getParameter("cantidad"));

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO productoDAO = new ProductoDAO(con);
            MovimientoDAO movimientoDAO = new MovimientoDAO(con);

            Producto producto = productoDAO.obtenerProductoPorId(idProducto);
            if (producto != null && producto.getCantidad() >= cantidadSalida) {
                int nuevaCantidad = producto.getCantidad() - cantidadSalida;
                productoDAO.actualizarCantidadProducto(idProducto, nuevaCantidad);

                // Registrar el movimiento de salida
                movimientoDAO.registrarMovimiento(idProducto, cantidadSalida, "Salida", usuario.getIdUsuario());

                response.sendRedirect("inventario"); // Redirigir a la página de inventario
            } else {
                response.sendRedirect("error.jsp?error=producto_no_disponible");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=salida_error");
        }
    }
}
