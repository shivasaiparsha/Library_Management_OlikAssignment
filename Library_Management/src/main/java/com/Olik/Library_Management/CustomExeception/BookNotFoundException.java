package com.Olik.Library_Management.CustomExeception;

import java.util.NoSuchElementException;

public class BookNotFoundException extends NoSuchElementException {
    public BookNotFoundException(String message)
    {
        super(message);
    }
}
