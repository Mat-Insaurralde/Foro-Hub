package com.lastByte.Foro.Hub.domain.topico;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository  extends JpaRepository<Topico,Long> {


    @Modifying
    @Transactional
     @Query(value = "INSERT INTO topico_cursos (topico_id,curso_id) VALUES (:topico_id,:curso_id);" , nativeQuery = true)
    void registrarCursoDelTopico(Long topico_id,Long curso_id);


    @Modifying
    @Transactional
    @Query(value = "DELETE  FROM topico_cursos t WHERE t.topico_id =:topico_id;" ,nativeQuery = true)
    void eliminarCursosDelTopico(Long topico_id);



    boolean existsByTituloAndMensajeIgnoreCase(String titulo, String mensaje);


}
