<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="fw-bold">Bienvenido, <%= usuario.getNombre() %></h2>
                <p class="mb-0 text-muted">Rol: 
                    <strong class="<%= usuario.getIdRol() == 1 ? "text-primary" : "text-success" %>">
                        <%= usuario.getIdRol() == 1 ? "Administrador" : "Almacenista" %>
                    </strong>
                </p>
            </div>
            <form action="<%= request.getContextPath() %>/LogoutController" method="post">
                <button class="btn btn-outline-danger">Cerrar sesión</button>
            </form>
        </div>

        <%-- Alertas --%>
        <%
            String mensajeExito = (String) session.getAttribute("mensajeExito");
            String mensajeError = (String) session.getAttribute("mensajeError");
            if (mensajeExito != null) {
        %>
            <div class="alert alert-success"><%= mensajeExito %></div>
        <%
                session.removeAttribute("mensajeExito");
            } else if (mensajeError != null) {
        %>
            <div class="alert alert-danger"><%= mensajeError %></div>
        <%
                session.removeAttribute("mensajeError");
            }
        %>

        <%-- Panel administrador: Agregar producto --%>
        <% if (usuario.getIdRol() == 1) { %>
            <div class="card mb-4">
                <div class="card-header bg-primary text-white">Agregar nuevo producto</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/AgregarProductoController" method="post">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" name="nombre" class="form-control" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Descripción</label>
                                <input type="text" name="descripcion" class="form-control" required>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Agregar producto</button>
                    </form>
                </div>
            </div>
        <% } %>

        <%-- Panel gestión inventario --%>
        <div class="card mb-4">
            <div class="card-header bg-secondary text-white">Gestión de Inventario</div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <div class="card-body">
                    	<a href="${pageContext.request.contextPath}/inventario">Ver inventario</a>
                    </div>
                    <% if (usuario.getIdRol() == 2) { %>
                    <div class="card-body">
                    	<a href="${pageContext.request.contextPath}/SalidaProductoController">Salida Productos</a>
                    </div>
                    <% } %>
                </ul>
            </div>
        </div>

        <%-- Historial (solo Admin) --%>
        <% if (usuario.getIdRol() == 1) { %>
            <div class="card mb-4">
                <div class="card-header bg-dark text-white">Historial de movimientos</div>
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/MovimientoController" class="btn btn-outline-dark">Ver historial</a>
                </div>
            </div>
        <% } %>
    </div>
</body>
</html>
