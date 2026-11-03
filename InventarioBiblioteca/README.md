# Proyecto 2: Control de Inventario y Reservas de una Biblioteca

Proyecto Maven (NetBeans) con Java Swing + MySQL.

## Pasos
1. Ejecutar `database/inventario_biblioteca.sql` en MySQL (crea la base `inventario_biblioteca`, las tablas y datos de prueba).
2. Revisar usuario/contraseña de MySQL en `src/main/java/Clases/clsConexion.java` (root / wilmer).
3. Abrir la carpeta `InventarioBiblioteca` en NetBeans y ejecutar (clase principal: `com.mycompany.inventariobiblioteca.InventarioBiblioteca`).
4. Iniciar sesión con **admin / admin123**.

## Estructura
| Paquete | Archivo | Descripción |
|---|---|---|
| Clases | `clsConexion` | Conexión a MySQL |
| Clases | `clsRecurso` | Clase base abstracta (atributos privados: codigo, titulo, autor, existencias) |
| Clases | `clsLibro` | Hereda de clsRecurso + numPaginas, isbn |
| Clases | `clsRevista` | Hereda de clsRecurso + numEdicion, mesPublicacion |
| Clases | `clsRecursoDAO` | CRUD: registrar, modificar, eliminar, buscar |
| Clases | `clsUsuarioDAO` | Validación del login |
| Formularios | `frmLogin` | Formulario de inicio de sesión |
| Formularios | `frmInventario` | JTable con todos los recursos; Editar / Eliminar el seleccionado |
| Formularios | `frmRegistro` | Formulario secundario para nuevos ingresos (y edición) |

## Tablas
- `tblusuario` (idUsuario, Usuario, Clave)
- `tblrecurso` (idRecurso, Titulo, Autor, Existencias, Tipo) → clase base
- `tbllibro` (idRecurso FK, NumPaginas, ISBN) → hija
- `tblrevista` (idRecurso FK, NumEdicion, MesPublicacion) → hija

Las tablas hijas usan `ON DELETE CASCADE`, así que al eliminar un recurso se borra también su detalle.
