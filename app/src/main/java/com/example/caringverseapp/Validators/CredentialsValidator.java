package com.example.caringverseapp.Validators;

public class CredentialsValidator {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";

    public CredentialsValidator() {}

    public boolean validateEmail(String s){ return s.matches(emailPattern) && !s.isEmpty();}
    public boolean validatePassword(String s){ return !s.isEmpty();}
}
