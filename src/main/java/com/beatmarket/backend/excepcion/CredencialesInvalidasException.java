// CredencialesInvalidasException.java
package com.beatmarket.backend.excepcion;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String mensaje) { super(mensaje); }
}