package com.Olik.Library_Management.CustomExeception;

public class AuthorNotFoundException extends Exception{
    public AuthorNotFoundException(String message)
    {
        super(message);
    }
}
