package com.pascoal.service;

public class Handler {

    public String handleString(String input) {
        System.out.println("Copying text: " + input);
        return input.toUpperCase();
    }

}
