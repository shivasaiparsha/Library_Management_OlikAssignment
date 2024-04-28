package com.Olik.Library_Management.CustomExeception;

public class BookNotAvailableException extends  Exception{

    public BookNotAvailableException(String message){
        super(message);
    }
}
