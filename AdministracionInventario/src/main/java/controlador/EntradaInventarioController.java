package controlador;

import dao.MovimientoDAO;
import dao.ProductoDAO;
import dao.Conexion;
import modelo.Producto;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class EntradaInventarioController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Verificar que sea admin
        HttpSession session = request.getSession(false);
        Usuario usuario = session!=null ? (Usuario)session.getAttribute("usuario") : null;
        if (usuario == null || usuario.getIdRol() != 1) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=acceso_denegado");
            return;
        }

        // 2) Leer parámetros
        int idProducto    = Integer.parseInt(request.getParameter("idProducto"));
        int cantidadEntrada = Integer.parseInt(request.getParameter("cantidad"));

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO productoDAO   = new ProductoDAO(con);
            MovimientoDAO movDAO      = new MovimientoDAO(con);

            // 3) Obtener producto y actualizar cantidad
            Producto p = productoDAO.obtenerProductoPorId(idProducto);
            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/error.jsp?error=producto_no_encontrado");
                return;
            }
            int nuevaCantidad = p.getCantidad() + cantidadEntrada;
            productoDAO.actualizarCantidadProducto(idProducto, nuevaCantidad);

            // 4) Registrar movimiento
            movDAO.registrarMovimiento(idProducto, cantidadEntrada, "entrada", usuario.getIdUsuario());

            // 5) Volver al inventario
            response.sendRedirect(request.getContextPath() + "/inventario");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
