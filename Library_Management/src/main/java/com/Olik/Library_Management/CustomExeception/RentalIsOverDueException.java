package com.Olik.Library_Management.CustomExeception;

public class RentalIsOverDueException extends  Exception{

    public RentalIsOverDueException(String message)
    {
        super(message);
    }
}
