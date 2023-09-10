package ru.neoflex.dossier.exceptions;

public class DossierException extends RuntimeException {
    public DossierException() {
        super("Unexpected error");
    }
}