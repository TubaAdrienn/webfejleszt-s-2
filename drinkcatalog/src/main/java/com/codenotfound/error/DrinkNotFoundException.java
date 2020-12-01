package com.codenotfound.error;

public class DrinkNotFoundException extends RuntimeException {

    public DrinkNotFoundException(Long id) {
        super("Drink id not found : " + id);
    }

}
