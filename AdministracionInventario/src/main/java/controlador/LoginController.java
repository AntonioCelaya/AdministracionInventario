package controlador;
import dao.UsuarioDAO;
import dao.Conexion;
import modelo.Usuario;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Si ya hay sesión activa, redirige directamente a home con el perfil de la sesión
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/vistas/home.jsp");
            return;
        }

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            Connection con = Conexion.getConexion();

            UsuarioDAO dao = new UsuarioDAO(con);
            Usuario usuario = dao.validar(correo, contrasena);
            
            if (usuario != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario", usuario);
                response.sendRedirect(request.getContextPath() + "/vistas/home.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/vistas/login.jsp?error=1");
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}