
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,

     nombre VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL UNIQUE,
     username VARCHAR(30) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL ,

     is_enabled BOOLEAN NULL DEFAULT NULL,
     account_No_Expired BOOLEAN NULL DEFAULT NULL,
     account_No_Locked BOOLEAN NULL DEFAULT NULL,
     credentials_No_Expired BOOLEAN NULL DEFAULT NULL,

    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_name ENUM('ADMIN', 'DEVELOPER', 'INVITED', 'USER') NULL DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UK_role_name (role_name)
);


CREATE TABLE IF NOT EXISTS permisos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    permiso_name ENUM('READ', 'UPDATE', 'DELETE','CREATE','WRITE' ) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX UK_permiso_name (permiso_name)
);

-- Crear tabla intermedia usuario_roles
CREATE TABLE IF NOT EXISTS usuario_roles (
    id BIGINT NOT NULL AUTO_INCREMENT,

    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
     PRIMARY KEY (id),

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (usuario_id, role_id)  -- Asegura que la combinación usuario_id y role_id sea única
);



CREATE TABLE IF NOT EXISTS role_permisos (
    id BIGINT NOT NULL AUTO_INCREMENT,

    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
     PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permisos(id),
     UNIQUE (role_id, permission_id)
);





-- Insertar roles
INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('DEVELOPER');
INSERT INTO roles (role_name) VALUES ('INVITED');
INSERT INTO roles (role_name) VALUES ('USER');






-- Inserción de permisos iniciales
INSERT INTO permisos (permiso_name) VALUES ('READ');
INSERT INTO permisos (permiso_name) VALUES ('WRITE');
INSERT INTO permisos (permiso_name) VALUES ('DELETE');
INSERT INTO permisos (permiso_name) VALUES ('UPDATE');
INSERT INTO permisos (permiso_name) VALUES ('CREATE');


-- Asignar permisos a roles (ejemplo)
INSERT INTO role_permisos (role_id, permission_id) VALUES (1, 1); -- ADMIN tiene READ
INSERT INTO role_permisos (role_id, permission_id) VALUES (1, 2); -- ADMIN tiene WRITE
INSERT INTO role_permisos (role_id, permission_id) VALUES (1, 3); -- ADMIN tiene DELETE