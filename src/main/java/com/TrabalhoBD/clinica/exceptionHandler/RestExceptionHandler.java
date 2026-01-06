package com.TrabalhoBD.clinica.exceptionHandler;

//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.TrabalhoBD.clinica.exceptions.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler {//extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> MedicoNotFoundHandler(NotFoundException exception){
        ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(com.TrabalhoBD.clinica.exceptions.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> DataIntegrityViolationExceptionHandler(com.TrabalhoBD.clinica.exceptions.DataIntegrityViolationException exception){
        ErrorMessage response = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        ErrorMessage response = new ErrorMessage(HttpStatus.BAD_REQUEST, "Erro da validação, você deixou algum campo obrigatório vázio");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
