package com.bintang.usermanagement.exception;

import lombok.Data;

@Data
public class DuplicateResourceException extends RuntimeException{

    private final String field;

    public DuplicateResourceException(String field, String message){
        super(message);
        this.field = field;
    }

}
