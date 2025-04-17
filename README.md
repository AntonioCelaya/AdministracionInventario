# Sistema de Administración de Inventario

Este proyecto es una aplicación web basada en Java que permite gestionar un inventario con control de accesos por roles (Administrador y Almacenista). Desarrollada utilizando el patrón de diseño MVC.

---

## Tecnologías utilizadas

- **IDE:** Eclipse 2025-03 (`eclipse-inst-jre-win64.exe`)
- **Servidor de Aplicaciones:** Apache Tomcat 9.0.104
- **Base de Datos:** MySQL 8, mediante XAMPP (`xampp-windows-x64-8.2.12-0-VS16`)
- **JDK:** JavaSE-21 (Eclipse Temurin JDK 2)
- **Modelo de arquitectura:** MVC (Modelo - Vista - Controlador)

---

## Pasos para ejecutar la aplicación

1. **Clonar o descargar el repositorio del proyecto**  
   Ubícalo en tu workspace de Eclipse, por ejemplo:  
   `C:\Users\TU_USUARIO\eclipse-workspace\AdministracionInventario`

2. **Abrir el proyecto en Eclipse**
   - File > Open Projects from File System...
   - Selecciona la carpeta raíz del proyecto.

3. **Configurar Apache Tomcat en Eclipse**  
   - Window > Preferences > Server > Runtime Environments  
   - Agrega Apache Tomcat 9 y vincúlalo con tu instalación local.  
   - Luego haz clic derecho sobre el proyecto > *Run As > Run on Server*.

4. **Configurar la base de datos en XAMPP**
   - Inicia Apache y MySQL desde el panel de XAMPP.
   - Abre phpMyAdmin y crea una base de datos llamada:  
     `administracion_inventario`
   - Ejecuta los scripts SQL ubicados en la carpeta `SCRIPTS` del repositorio para crear las tablas necesarias (`roles`, `usuarios`, `productos`, `movimientos`).

5. **Editar la cadena de conexión (si es necesario)**  
   Asegúrate de que el archivo que establece la conexión (`UsuarioDAO.java`, u otro archivo DAO) tenga la cadena:
   ```java jdbc:mysql://localhost:3306/administracion_inventario ```

6. **Iniciar la aplicación**
    - Ejecuta el proyecto con clic derecho > Run As > Run on Server.
    - Se abrirá en el navegador (ej: http://localhost:8080/AdministracionInventario)
    - Inicia sesión con un usuario existente o agrégalo manualmente en la base.
