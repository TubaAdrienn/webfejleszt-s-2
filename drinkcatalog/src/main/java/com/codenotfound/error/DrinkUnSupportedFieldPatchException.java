package com.codenotfound.error;

import java.util.Set;

public class DrinkUnSupportedFieldPatchException extends RuntimeException {

    public DrinkUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}
