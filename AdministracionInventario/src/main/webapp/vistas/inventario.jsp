<%@ page import="java.util.List, modelo.Producto, modelo.Usuario" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Validar sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
    List<Producto> productos = (List<Producto>) request.getAttribute("listaProductos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Inventario</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container mt-5">
      	  <div class="mb-3">
		  <a href="${pageContext.request.contextPath}/vistas/home.jsp" class="btn btn-secondary">
		    ← Volver a Home
		  </a>
	  </div>
    <h2>Inventario de Productos</h2>
    <% if (usuario.getIdRol() == 1) { %>
  <div class="card mb-4">
    <div class="card-header bg-primary text-white">Registrar Entrada de Inventario</div>
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/entradaInventario" method="post" class="row g-3 align-items-end">
        <div class="col-md-6">
          <label for="idProducto" class="form-label">Producto</label>
          <select name="idProducto" id="idProducto" class="form-select" required>
            <% for (Producto p : productos) { %>
              <option value="<%= p.getIdProducto() %>">
                <%= p.getNombre() %> (Disponibles: <%= p.getCantidad() %>)
              </option>
            <% } %>
          </select>
        </div>
        <div class="col-md-4">
          <label for="cantidad" class="form-label">Cantidad a ingresar</label>
          <input type="number" name="cantidad" id="cantidad" class="form-control" min="1" required>
        </div>
        <div class="col-md-2">
          <button type="submit" class="btn btn-success w-100">Registrar</button>
        </div>
      </form>
    </div>
  </div>
<% } %>
    
    <table class="table table-striped mt-4">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Cantidad</th>
          <th>Estatus</th>
        </tr>
      </thead>
      <tbody>
        <% if (productos != null) {
             for (Producto p : productos) { %>
        <tr>
          <td><%= p.getIdProducto() %></td>
          <td><%= p.getNombre() %></td>
          <td><%= p.getDescripcion() %></td>
          <td><%= p.getCantidad() %></td>
          <td>
            <span class="badge <%= p.getEstatus()==1 ? "bg-success" : "bg-secondary" %>">
              <%= p.getEstatus()==1 ? "Activo" : "Inactivo" %>
            </span>
          </td>
           <% if (usuario.getIdRol()==1) { %>
	      <td>
	        <form action="${pageContext.request.contextPath}/estatusProducto" method="post" style="display:inline">
	          <input type="hidden" name="idProducto" value="<%= p.getIdProducto() %>"/>
	          <button
	            name="accion"
	            value="<%= p.getEstatus()==1 ? "darBaja" : "activar" %>"
	            class="btn btn-sm <%= p.getEstatus()==1 ? "btn-danger" : "btn-success" %>">
	            <%= p.getEstatus()==1 ? "Dar de baja" : "Reactivar" %>
	          </button>
	        </form>
	      </td>
	      <% } %>
        </tr>
        <%   }
           } else { %>
        <tr>
          <td colspan="5" class="text-center">No hay productos.</td>
        </tr>
        <% } %>
      </tbody>
    </table>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>