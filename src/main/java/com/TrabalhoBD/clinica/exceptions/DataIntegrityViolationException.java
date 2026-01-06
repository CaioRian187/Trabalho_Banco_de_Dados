package com.TrabalhoBD.clinica.exceptions;

public class DataIntegrityViolationException extends RuntimeException{

    public DataIntegrityViolationException( ){
        super("Não é possível deletar, pois há vinculações em outras tabelas");
    }

    public DataIntegrityViolationException(String msg){
        super(msg);
    }
}
