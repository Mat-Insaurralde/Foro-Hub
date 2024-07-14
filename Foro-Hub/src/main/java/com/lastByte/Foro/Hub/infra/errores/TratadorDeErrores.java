package com.lastByte.Foro.Hub.infra.errores;


import com.lastByte.Foro.Hub.infra.excepciones.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.excepciones.ResourceNotFoundException;
import com.lastByte.Foro.Hub.infra.excepciones.TokenVerificationException;
import com.lastByte.Foro.Hub.infra.excepciones.ValidacionDeIntegridad;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //Intercepta las llamadas en caso ocurra una excepcion
public class TratadorDeErrores {
   // ValidacionDeIntegridad: Es más específica y se puede usar para situaciones donde hay una
    // violación de las reglas de integridad, como duplicados, relaciones entre entidades, etc.

    // ValidationException: Es más general y se puede usar para cualquier tipo de error de validación
    // de datos, como entradas vacías, formatos incorrectos, valores fuera de rango, etc.


    ///RESPONSE VALIDACIONES DE INTEGRIDAD
    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    ///RESPONSE DE COLLECTION VACIA EXCEPTION
   @ExceptionHandler(CollectionEmptyException.class)
    public ResponseEntity collectionEmptyException(Exception e) {
        return ResponseEntity.ok().body(e.getMessage());
    }

    //RESPONSE VALIDATION EXCEPTION
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    ///RESPONSE VERIFICACION DE TOKEN EXCEPTION
    @ExceptionHandler(TokenVerificationException.class)
    public ResponseEntity<String> handleTokenVerificationException(TokenVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    //RESPONSE RECURSO NO ENCONTRADO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
