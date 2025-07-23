
CREATE DATABASE IF NOT EXISTS trukea;

USE trukea;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100),
    edad INT,
    foto_perfil VARCHAR(255),
    descripcion TEXT
);

CREATE TABLE zonas_seguras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion TEXT
);

CREATE TABLE publicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(50),
    estado ENUM('Nuevo', 'Usado', 'Buen estado') NOT NULL,
    imagen_url VARCHAR(255),
    zona_segura_id INT,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (zona_segura_id) REFERENCES zonas_seguras(id)
);

CREATE TABLE solicitudes_trueque (
    id INT AUTO_INCREMENT PRIMARY KEY,
    publicacion_ofrecida_id INT NOT NULL,
    publicacion_deseada_id INT NOT NULL,
    usuario_solicitante_id INT NOT NULL,
    comentario TEXT,
    estado ENUM('Pendiente', 'Aceptado', 'Rechazado', 'Cancelado') DEFAULT 'Pendiente',
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (publicacion_ofrecida_id) REFERENCES publicaciones(id),
    FOREIGN KEY (publicacion_deseada_id) REFERENCES publicaciones(id),
    FOREIGN KEY (usuario_solicitante_id) REFERENCES usuarios(id)
);

CREATE TABLE historial_trueques (
    id INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_id INT NOT NULL,
    fecha_realizacion DATETIME,
    estado ENUM('Completado', 'Cancelado', 'Caducado') NOT NULL,
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes_trueque(id)
);

CREATE TABLE calificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    trueque_id INT NOT NULL,
    usuario_calificado_id INT NOT NULL,
    calificador_id INT NOT NULL,
    puntuacion INT CHECK (puntuacion BETWEEN 1 AND 5),
    comentario TEXT,
    fecha_calificacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trueque_id) REFERENCES historial_trueques(id),
    FOREIGN KEY (usuario_calificado_id) REFERENCES usuarios(id),
    FOREIGN KEY (calificador_id) REFERENCES usuarios(id)
);

CREATE TABLE intercambios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    historial_id INT NOT NULL,
    publicacion_1_id INT NOT NULL,
    publicacion_2_id INT NOT NULL,
    FOREIGN KEY (historial_id) REFERENCES historial_trueques(id),
    FOREIGN KEY (publicacion_1_id) REFERENCES publicaciones(id),
    FOREIGN KEY (publicacion_2_id) REFERENCES publicaciones(id)
);