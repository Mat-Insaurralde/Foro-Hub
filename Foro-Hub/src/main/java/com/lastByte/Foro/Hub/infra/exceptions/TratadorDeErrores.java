package com.lastByte.Foro.Hub.infra.exceptions;


import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<ErrorDetailsDTO> errorHandlerValidacionesDeIntegridad(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorDetailsDTO(e.getMessage()));
    }




    ///RESPONSE DE COLLECTION VACIA EXCEPTION
    @ExceptionHandler(CollectionEmptyException.class)
    public ResponseEntity<ErrorDetailsDTO> collectionEmptyException(Exception e) {
        return ResponseEntity.ok().body(new ErrorDetailsDTO(e.getMessage()));
    }



    //RESPONSE VALIDATION EXCEPTION
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetailsDTO> errorHandlerValidacionesDeNegocio(Exception e) {

        return ResponseEntity.badRequest().body(new ErrorDetailsDTO(e.getMessage()));
    }


    //RESPONSE RECURSO NO ENCONTRADO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetailsDTO(e.getMessage()));
    }


    //RESPONSE USERNAME O CONTRASEÑA INVALIDA
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorDetailsDTO> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDetailsDTO(e.getMessage()));
    }

    //RESPONSE USERNAME NOT FOUND
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetailsDTO(e.getMessage()));
    }


    //RESPONSE ILEGALARGUMENT
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDetailsDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetailsDTO(e.getMessage()));
    }

}
