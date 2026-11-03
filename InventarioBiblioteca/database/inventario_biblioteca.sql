-- =====================================================
--  Base de datos: inventario_biblioteca
--  Ejecutar completo en MySQL Workbench (o consola).
--  OJO: borra la base si ya existe y la crea de nuevo.
-- =====================================================
SET NAMES utf8mb4;
DROP DATABASE IF EXISTS inventario_biblioteca;
CREATE DATABASE inventario_biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE inventario_biblioteca;

-- Usuarios que pueden iniciar sesión (formulario de Login)
CREATE TABLE tblusuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    Usuario   VARCHAR(50) NOT NULL UNIQUE,
    Clave     VARCHAR(50) NOT NULL
);

-- Clase base Recurso (datos comunes de libros y revistas)
CREATE TABLE tblrecurso (
    idRecurso   VARCHAR(20)  PRIMARY KEY,
    Titulo      VARCHAR(150) NOT NULL,
    Autor       VARCHAR(100) NOT NULL,
    Existencias INT          NOT NULL DEFAULT 0 CHECK (Existencias >= 0),
    Tipo        ENUM('Libro','Revista') NOT NULL
);

-- Clase hija Libro (hereda de tblrecurso por la llave idRecurso)
CREATE TABLE tbllibro (
    idRecurso  VARCHAR(20) PRIMARY KEY,
    NumPaginas INT         NOT NULL CHECK (NumPaginas > 0),
    ISBN       VARCHAR(20) NOT NULL UNIQUE,
    FOREIGN KEY (idRecurso) REFERENCES tblrecurso(idRecurso)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Clase hija Revista (hereda de tblrecurso por la llave idRecurso)
CREATE TABLE tblrevista (
    idRecurso      VARCHAR(20) PRIMARY KEY,
    NumEdicion     INT         NOT NULL CHECK (NumEdicion > 0),
    MesPublicacion VARCHAR(15) NOT NULL,
    FOREIGN KEY (idRecurso) REFERENCES tblrecurso(idRecurso)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ---------------- Datos de prueba ----------------
INSERT INTO tblusuario (Usuario, Clave) VALUES ('admin', 'admin123');

INSERT INTO tblrecurso (idRecurso, Titulo, Autor, Existencias, Tipo) VALUES
('L001', 'Cien años de soledad', 'Gabriel García Márquez', 5, 'Libro'),
('L002', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 3, 'Libro'),
('L003', 'Java: Cómo programar', 'Paul Deitel', 4, 'Libro'),
('R001', 'National Geographic', 'National Geographic Society', 6, 'Revista'),
('R002', 'Muy Interesante', 'Editorial Televisa', 2, 'Revista');

INSERT INTO tbllibro (idRecurso, NumPaginas, ISBN) VALUES
('L001', 471, '978-0307474728'),
('L002', 1056, '978-8420412146'),
('L003', 1200, '978-6073227384');

INSERT INTO tblrevista (idRecurso, NumEdicion, MesPublicacion) VALUES
('R001', 245, 'Marzo'),
('R002', 512, 'Junio');

-- Consulta para comprobar el inventario completo
SELECT r.idRecurso, r.Tipo, r.Titulo, r.Autor, r.Existencias,
       l.NumPaginas, l.ISBN, v.NumEdicion, v.MesPublicacion
FROM tblrecurso r
LEFT JOIN tbllibro   l ON r.idRecurso = l.idRecurso
LEFT JOIN tblrevista v ON r.idRecurso = v.idRecurso
ORDER BY r.idRecurso;
