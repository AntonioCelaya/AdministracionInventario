package controlador;

import dao.ProductoDAO;
import dao.Conexion;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class EstatusProductoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar sesión y rol admin
        HttpSession session = request.getSession(false);
        Usuario usuario = (session!=null) ? (Usuario)session.getAttribute("usuario") : null;
        if (usuario==null || usuario.getIdRol()!=1) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=acceso_denegado");
            return;
        }

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String accion   = request.getParameter("accion"); // "darBaja" o "activar"
        int nuevoEstatus = "darBaja".equals(accion) ? 0 : 1;

        try (Connection con = Conexion.getConexion()) {
            ProductoDAO dao = new ProductoDAO(con);
            dao.actualizarEstatus(idProducto, nuevoEstatus);
            // Volver al inventario para ver el cambio
            response.sendRedirect(request.getContextPath() + "/inventario");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
