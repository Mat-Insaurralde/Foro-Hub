

CREATE TABLE topico_cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,

    topico_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_topico_cursos_topico FOREIGN KEY (topico_id) REFERENCES topicos(id) ON DELETE CASCADE ,
    CONSTRAINT fk_topico_cursos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);
