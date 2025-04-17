<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Movimiento" %>
<%@ page import="modelo.Usuario" %>
<%
	// Si no hay ninguna sesión activa, se redirige a login
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
    // Si no se tiene rol de admin entonces redirige a home
    if (usuario.getIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/vistas/home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Movimientos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-4">
        <h2 class="mb-4">Historial de Movimientos</h2>
        <form method="get" action="<%= request.getContextPath() %>/HistorialMovimientosController" class="row g-3 mb-4">
  <div class="col-md-4">
    <label for="tipoMovimiento" class="form-label">Filtrar por tipo</label>
    <select name="tipo" id="tipoMovimiento" class="form-select">
      <option value="">Todos</option>
      <option value="entrada" <%= "entrada".equals(request.getParameter("tipo")) ? "selected" : "" %>>Entrada</option>
      <option value="salida" <%= "salida".equals(request.getParameter("tipo")) ? "selected" : "" %>>Salida</option>
    </select>
  </div>
  <div class="col-md-2 align-self-end">
    <button type="submit" class="btn btn-primary">Filtrar</button>
  </div>
</form>
        
        <table class="table table-striped">
			<thead>
			    <tr>
			        <th>Producto</th>
			        <th>Usuario</th>
			        <th>Tipo de movimiento</th>
			        <th>Cantidad</th>
			        <th>Fecha y hora</th>
			    </tr>
			</thead>
			<tbody>
			    <%
			        List<Movimiento> listaMovimientos = (List<Movimiento>) request.getAttribute("listaMovimientos");
			        if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
			            for (Movimiento m : listaMovimientos) {
			    %>
			    <tr>
			        <td><%= m.getNombreProducto() %></td>
			        <td><%= m.getNombreUsuario() %></td>
			        <td><%= m.getTipoMovimiento() %></td>
			        <td><%= m.getCantidad() %></td>
			        <td><%= m.getFechaHora() %></td>
			    </tr>
			    <%
			            }
			        } else {
			    %>
			    <tr>
			        <td colspan="5" class="text-center">No hay movimientos registrados.</td>
			    </tr>
			    <%
			        }
			    %>
			</tbody>
        </table>
        <a href="<%= request.getContextPath() %>/vistas/home.jsp" class="btn btn-secondary">Volver al Home</a>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>