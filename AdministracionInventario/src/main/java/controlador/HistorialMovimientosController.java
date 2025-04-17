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
public class HistorialMovimientosController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipo = request.getParameter("tipo"); // tipo de movimiento: "entrada", "salida", o null

        try {
            Connection con = Conexion.getConexion();
            MovimientoDAO movimientoDAO = new MovimientoDAO(con);
            List<Movimiento> movimientos;

            if (tipo != null && !tipo.isEmpty()) {
                movimientos = movimientoDAO.listarMovimientosFiltrados(tipo);
            } else {
                movimientos = movimientoDAO.listarMovimientos(); // todos
            }

            request.setAttribute("listaMovimientos", movimientos);
            request.getRequestDispatcher("/vistas/historialMovimientos.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
