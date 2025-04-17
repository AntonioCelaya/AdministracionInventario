package controlador;

import dao.Conexion;
import dao.ProductoDAO;
import modelo.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class AgregarProductoController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        try {
            Connection con = Conexion.getConexion();
            ProductoDAO dao = new ProductoDAO(con);
            boolean productoRegistrado = dao.registrarProducto(nombre, descripcion);

            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (productoRegistrado) {
                session.setAttribute("mensajeExito", "Producto registrado con éxito.");
            } else {
                session.setAttribute("mensajeError", "Error al registrar el producto.");
            }

            response.sendRedirect(request.getContextPath() + "/vistas/home.jsp");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
