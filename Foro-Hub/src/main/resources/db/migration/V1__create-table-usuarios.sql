
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,

     nombre VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL UNIQUE,
     username VARCHAR(30) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL ,

     status tinyint not null,

    PRIMARY KEY (id)
);