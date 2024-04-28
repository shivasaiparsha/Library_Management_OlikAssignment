package com.Olik.Library_Management.CustomExeception;

public class MissingFieldException extends Exception{
    public MissingFieldException(String message){
        super(message);
    }
}
