package controlador;

import dao.Conexion;
import dao.MovimientoDAO;
import modelo.Movimiento;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("serial")
public class MovimientoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo"); // puede ser "entrada", "salida" o null (todos)

        try {
            Connection con = Conexion.getConexion();
            MovimientoDAO movimientoDAO = new MovimientoDAO(con);
            List<Movimiento> listaMovimientos;

            if (tipo != null && !tipo.isEmpty()) {
                listaMovimientos = movimientoDAO.listarMovimientosFiltrados(tipo);
            } else {
                listaMovimientos = movimientoDAO.listarMovimientos();
            }

            request.setAttribute("listaMovimientos", listaMovimientos);
            request.getRequestDispatcher("vistas/historialMovimientos.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
