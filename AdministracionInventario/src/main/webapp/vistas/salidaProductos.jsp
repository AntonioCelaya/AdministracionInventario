<%@ page import="java.util.List, modelo.Producto, modelo.Usuario" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // 1) Validación de sesión
	Usuario usuario = (Usuario) session.getAttribute("usuario");
	if (usuario == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
    // 2) Solo almacenistas pueden acceder a esta página
    if (usuario.getIdRol() != 2) {
        response.sendRedirect(request.getContextPath() + "/vistas/home.jsp");
        return;
    }
    // Ya validado, obtenemos la lista de productos activos
    List<Producto> productos = (List<Producto>) request.getAttribute("productosActivos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Salida de Productos</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container mt-5">
    <div class="mb-3">
      <a href="<%= request.getContextPath() %>/vistas/home.jsp" class="btn btn-secondary">← Volver a Home</a>
    </div>

    <h2>Registrar Salida de Producto</h2>

    <%-- Mensajes de error o éxito --%>
    <%
      String msgE = (String) session.getAttribute("mensajeError");
      if (msgE != null) {
    %>
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= msgE %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    <%
        session.removeAttribute("mensajeError");
      }
      String msgS = (String) session.getAttribute("mensajeExito");
      if (msgS != null) {
    %>
      <div class="alert alert-success alert-dismissible fade show">
        <%= msgS %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    <%
        session.removeAttribute("mensajeExito");
      }
    %>

    <form action="<%= request.getContextPath() %>/SalidaProductoController" method="post" class="row g-3">
      <div class="col-md-6">
        <label for="producto" class="form-label">Producto</label>
        <select name="idProducto" id="producto" class="form-select" required>
          <% for (Producto p : productos) { %>
            <option value="<%= p.getIdProducto() %>">
              <%= p.getNombre() %> (Disponibles: <%= p.getCantidad() %>)
            </option>
          <% } %>
        </select>
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="form-label">Cantidad a retirar</label>
        <input type="number" name="cantidad" id="cantidad" class="form-control" min="1" required>
      </div>
      <div class="col-12">
        <button type="submit" class="btn btn-danger">Registrar salida</button>
      </div>
    </form>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
